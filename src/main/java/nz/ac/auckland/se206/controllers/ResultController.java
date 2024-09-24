package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.Suspect;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for a fxml scene.
 */
public class ResultController {
  @FXML private Label guessStatus;
  @FXML private Label marking;
  @FXML private TextArea markingResult;

  private ChatCompletionRequest completionRequest;

  public ResultController() {
    // tries this thing
    try {
      var systemPromptFile =
          new String(
              Objects.requireNonNull(
                      ResultController.class.getResourceAsStream("/prompts/validateGuess.txt"))
                  .readAllBytes());
      completionRequest =
          new ChatCompletionRequest(ApiProxyConfig.readConfig())
              .addMessage("system", systemPromptFile);
    } catch (NullPointerException e) {
      // If doesnt load prompt
      System.err.println("Could not load prompt");
    } catch (IOException e) {
      System.err.println("Could not load system prompt");
      throw new RuntimeException(e);
    } catch (ApiProxyException e) {
      System.err.println("Could not read api proxy config");
      throw new RuntimeException(e);
    }
  }

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   */
  @FXML
  public void initialize() {
    setResult(
        GlobalVariables.getChosenSuspect().equals(Suspect.LOUIE), GlobalVariables.getReport());
  }

  public void setResult(boolean isGuessCorrect, String reasoning) {
    // if guess is wrong does stuff
    if (!isGuessCorrect) {
      guessStatus.setText("You guessed wrong!");
      marking.setVisible(false);
      markingResult.setVisible(false);
      return;
    }

    guessStatus.setText("You guessed correctly!");
    // spawn background task to do stuffff
    var task =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            ChatCompletionResult result;
            try {
              result = completionRequest.addMessage("user", reasoning).execute();
            } catch (ApiProxyException e) {
              System.err.println("Could not get chatgpt response " + e.getMessage());
              return null;
            }
            // update the ui
            Platform.runLater(
                () -> {
                  var message = result.getChoice(0).getChatMessage().getContent();

                  if (message.contains("--yes")) {
                    message = message.replace("--yes", "");

                    marking.setText("You were spot on, here is the feedback on your response");
                  } else {
                    marking.setText("Not quite, here is the feedback on your response");
                  }

                  markingResult.setText(message);
                });

            return null;
          }
        };
    // startes thread
    new Thread(task).start();
  }

  @FXML
  private void onHandleRestart() throws IOException {
    SceneManager.addUi(AppUi.RESTART, App.loadFxml("restart"));
    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESTART));
    // Set what to do after the pause
    PauseTransition pause = new PauseTransition(Duration.millis(500));
    pause.setOnFinished(
        event -> {
          App.restart();
        });
    // Start the pause
    pause.play();
  }
}
