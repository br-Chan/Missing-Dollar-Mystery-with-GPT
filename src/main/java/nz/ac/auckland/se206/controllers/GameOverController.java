package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for the gameOver scene, in which the user has lost the game by
 * default due to not meeting some condition(s) to be able to guess or view the results of said
 * guess.
 */
public class GameOverController {

  @FXML private Button restartButton;
  @FXML private Label gameOverLabel;

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   */
  @FXML
  public void initialize() {
    gameOverLabel.setText(
        "You ran out of time! "
            + GlobalVariables.getGameOverReason()
            + " Better luck next time, Investigator!");
    try {
      if (GlobalVariables.getGameOverReason()
          .equals(
              "You need to talk to all suspects and interact with something in the crime scene.")) {
        TextToSpeech.playVoiceline("GameOverInteractions");
      } else if (GlobalVariables.getGameOverReason()
          .equals("You need to accuse a suspect in your report.")) {
        TextToSpeech.playVoiceline("GameOverAccuse");
      } else {
        TextToSpeech.playVoiceline("GameOverReport");
      }
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @FXML
  private void onKeyPressed(KeyEvent event) {
    GlobalVariables.checkForCheatCode(event.getCode().toString());
  }

  /**
   * Handles clicking restart button. Changes to a restarting screen, and calls a restart method.
   *
   * @throws IOException throws exception regarding loading FXML
   */
  @FXML
  private void handleRestartButtonClick() throws IOException {
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
