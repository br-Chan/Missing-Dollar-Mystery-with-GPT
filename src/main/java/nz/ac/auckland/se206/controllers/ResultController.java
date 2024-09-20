package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for a fxml scene.
 */
public class ResultController {
  @FXML
  private Pane correctGuessPane;
  @FXML
  private Pane incorrectGuessPane;
  @FXML
  private Label marking;
  @FXML
  private Label responseMarked;
  @FXML
  private Label markingResult;

  private ChatCompletionRequest completionRequest;

  public ResultController() {
    try {
      var systemPromptFile = new String(Objects.requireNonNull(ResultController.class.getResourceAsStream("/prompts/validateGuess.txt")).readAllBytes());
      completionRequest = new ChatCompletionRequest(ApiProxyConfig.readConfig()).addMessage("system", systemPromptFile);
    } catch (NullPointerException e) {
      System.err.println("Could not load prompt");
    } catch (IOException e) {
      System.err.println("Could not load system prompt");
      throw new RuntimeException(e);
    } catch (ApiProxyException e) {
      System.err.println("Could not read api proxy config");
      throw new RuntimeException(e);
    }
  }

  public void setResult(boolean isGuessCorrect, String reasoning) {
    if (!isGuessCorrect) {
      incorrectGuessPane.setVisible(true);
      correctGuessPane.setVisible(false);

      return;
    }

    incorrectGuessPane.setVisible(false);
    correctGuessPane.setVisible(true);

    var task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        ChatCompletionResult result;
        try {
          result = completionRequest.addMessage("user", reasoning).execute();
        } catch (ApiProxyException e) {
          System.err.println("Could not get chatgpt response " + e.getMessage());
          return null;
        }


        Platform.runLater(() -> {
          marking.setVisible(false);

          var message = result.getChoice(0).getChatMessage().getContent();

          if (message.contains("--yes")) {
            message = message.replace("--yes", "");

            responseMarked.setText("You were spot on, here is the feedback on your response");
          } else {
            responseMarked.setText("Not quite, here is the feedback on your response");
          }

          markingResult.setText(message);

          responseMarked.setVisible(true);
          markingResult.setVisible(true);
        });


        return null;
      }
    };

    new Thread(task).start();
  }

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialising...");
  }
}
