package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.Suspect;
import nz.ac.auckland.se206.prompts.PromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for the results scene.
 */
public class ResultController extends GptChatter {
  @FXML private Label guessStatus;
  @FXML private Label marking;

  public ResultController() {
    promptFilename = "validateGuess.txt";

    temperature = 0.1;
    topP = 0.1;
    maxTokens = 300;
  }

  /**
   * Initialises the results scene, analysing the user's accusation and report to create feedback.
   */
  @FXML
  public void initialize() {
    Task<Void> backgroundTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            // Check if the user was right, and potentially write feedback.
            setResult(
                GlobalVariables.getChosenSuspect().equals(Suspect.LOUIE),
                GlobalVariables.getReport());

            /**
             * Check if ChatGPT gave user's report a passing score (3 or more) and set visual text
             * accordingly.
             */
            Platform.runLater(
                () -> {
                  try {
                    // Create chat message containing the user's report, then get ChatGPT's response
                    // (Inspector Ros' feedback).
                    ChatMessage generatedFeedback =
                        runGpt(
                            new ChatMessage(
                                "user", "Mentee's report: " + GlobalVariables.getReport()),
                            true);
                    String message = generatedFeedback.getContent();
                    System.out.println(message);

                    // Handle acceptance logic. If the GPT's score for the report is at least 3 out
                    // of 6, then Louie must confess and the feedback must end with --yes.
                    if (message.contains("--yes")) {
                      message = message.replace("--yes", "");

                      marking.setText("You were spot on, here is the feedback on your response");
                      try {
                        playResultTTS("rightAll");
                      } catch (URISyntaxException e) {
                        e.printStackTrace();
                      }
                    } else {
                      marking.setText("Not quite, here is the feedback on your response");
                      try {
                        playResultTTS("rightGuess");
                      } catch (URISyntaxException e) {
                        e.printStackTrace();
                      }
                    }

                    // Update the text area with Inspector Ros' feedback.
                    txtaChat.setText(message);
                  } catch (ApiProxyException e) {
                    e.printStackTrace();
                  }
                });

            return null;
          }
        };
    Thread backgroundThread = new Thread(backgroundTask);
    backgroundThread.start();
  }

  @Override
  protected String getSystemPrompt() {
    Map<String, String> dataMap = new HashMap<>();
    return PromptEngineering.getPrompt(promptFilename, dataMap);
  }

  /**
   * Determine if the user accused the right suspect, and if they are wrong update the UI
   * accordingly.
   *
   * @param isGuessCorrect whether the user's accusation was correct
   * @param reasoning the user's report
   */
  public void setResult(boolean isGuessCorrect, String reasoning) {
    // Handle the guess being wrong
    if (!isGuessCorrect) {
      guessStatus.setText("You guessed wrong!");
      try {
        playResultTTS("wrongGuess");
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      marking.setVisible(false);
      txtaChat.setVisible(false);
      return;
    }

    // Get ChatGPT to start writing the feedback to the report
    guessStatus.setText("You guessed correctly!");
    initialiseChatCompletionRequest(false);
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    GlobalVariables.checkForCheatCode(event.getCode().toString());
  }

  /**
   * Handles clicking restart button. Changes to a restarting screen, and calls a restart method.
   *
   * @throws IOException throws exception regarding loading FXML
   */
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

  /**
   * Plays different TTS based on the users guess results.
   *
   * @param result the result of the guess
   * @throws URISyntaxException exception regarding playing TTS
   */
  private void playResultTTS(String result) throws URISyntaxException {
    switch (result) {
      case "wrongGuess":
        TextToSpeech.playVoiceline("IncorrectGuessIncorrectReasoning");
        break;
      case "rightGuess":
        TextToSpeech.playVoiceline("CorrectGuessIncorrectReasoning");
        break;
      case "rightAll":
        TextToSpeech.playVoiceline("CorrectGuessCorrectReasoning");
        break;
    }
  }
}
