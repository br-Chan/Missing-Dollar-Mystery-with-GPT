package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for a fxml scene.
 */
public class GuessController {

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   *
   * @throws URISyntaxException
   */
  @FXML
  public void initialize() throws URISyntaxException {
    System.out.println("Initialising...");
    TextToSpeech.playVoiceline("MakeAGuess");
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {}

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {}
}
