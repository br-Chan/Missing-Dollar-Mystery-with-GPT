package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** This class handles the story scene. */
public class StoryController {

  @FXML private Button startButton;

  @FXML
  private void initialize() throws URISyntaxException {
    TextToSpeech.playVoiceline("Menu");
  }

  @FXML
  private void onKeyPressed(KeyEvent event) {
    GlobalVariables.checkForCheatCode(event.getCode().toString());
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {}
}
