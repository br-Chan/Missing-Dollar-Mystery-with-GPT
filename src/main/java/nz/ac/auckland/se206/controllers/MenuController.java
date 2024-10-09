package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class MenuController {

  @FXML private Button startButton;

  @FXML
  private void initialize() throws URISyntaxException {
    TextToSpeech.loopMusic("HawkingWalking");
  }

  @FXML
  private void onKeyPressed(KeyEvent event) {
    GlobalVariables.checkForCheatCode(event.getCode().toString());
  }

  /**
   * Switches the displayed scene when the user clicks the start button.
   *
   * @param event the mouse event triggered by clicking the button
   * @throws IOException if there is an I/O error
   * @throws URISyntaxException 
   */
  @FXML
  private void onHandleStartButtonClick(ActionEvent event) throws IOException, URISyntaxException {
    // Initialise the scene only when the start button is clicked so that the app timer starts
    // counting down at the right time.
    TextToSpeech.playVoiceline("Game");
    SceneManager.addUi(AppUi.GAME, App.loadFxml("game"));
    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GAME));
  }
}
