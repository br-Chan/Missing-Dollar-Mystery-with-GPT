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
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.*;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.components.Sprite;
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
  @FXML private Sprite resultsSheet;
  @FXML private TextArea resultsArea;
  @FXML private Label markingLabel;

  /** Initialises the result controller instance with temperature and topP for the results. */
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
    // Check if the user was right, and potentially write feedback.
    var isGuessCorrect = GlobalVariables.getChosenSuspect().equals(Suspect.LOUIE);

    // Handle the guess being wrong
    if (!isGuessCorrect) {
      try {
        playResultTTS("wrongGuess");
        return;
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }

    // Get ChatGPT to start writing the feedback to the report
    initialiseChatCompletionRequest(false);
    MugshotTransition mt = hideGuessSheet();
    markingLabel.setOpacity(1);

    Task<Void> backgroundTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            //Check if ChatGPT gave user's report a passing score (3 or more) and set visual text
            //accordingly.
            // Create chat message containing the user's report, then get ChatGPT's response
            // (Inspector Ros' feedback).
            String message;
            try {
              ChatMessage generatedFeedback =
                  runGpt(
                      new ChatMessage("user", "Mentee's report: " + GlobalVariables.getReport()),
                      true);
              message = generatedFeedback.getContent();
              System.out.println(message);
            } catch (ApiProxyException e) {
              e.printStackTrace();
              return null;
            }

            Platform.runLater(
                () -> {
                  // Handle acceptance logic. If the GPT's score for the report is at least 3 out
                  // of 6, then Louie must confess and the feedback must end with --yes.
                  if (message.contains("--yes")) {
                    var messageReplaced = message.replace("--yes", "");

                    try {
                      playResultTTS("rightAll");
                      resultsArea.setVisible(true);
                      mt.playBackwards();
                      markingLabel.setOpacity(0);
                      resultsSheet
                          .spriteUrlProperty()
                          .set(
                              getClass()
                                  .getResource("/images/resultsScreen/CorrectGuessAndResponse.png")
                                  .toString());
                      resultsArea.setText(messageReplaced);
                    } catch (URISyntaxException e) {
                      e.printStackTrace();
                    }
                  } else {
                    try {
                      playResultTTS("rightGuess");
                      mt.playBackwards();
                      resultsArea.setVisible(true);
                      markingLabel.setOpacity(0);
                      resultsSheet
                          .spriteUrlProperty()
                          .set(
                              getClass()
                                  .getResource("/images/resultsScreen/CorrectGuess.png")
                                  .toString());
                      resultsArea.setText(message);
                    } catch (URISyntaxException e) {
                      e.printStackTrace();
                    }
                  }

                  //             Update the text area with Inspector Ros' feedback.
                  //            txtaChat.setText(message);
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

  private MugshotTransition hideGuessSheet() {
    MugshotTransition mt =
        new MugshotTransition(resultsSheet, (int) resultsSheet.getLayoutX(), -700);
    resultsSheet.setTranslateY(-700);
    return mt;
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
