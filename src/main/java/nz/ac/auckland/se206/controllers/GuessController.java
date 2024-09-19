package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AppTimer;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for the guess fxml scene.
 */
public class GuessController {

  @FXML private Button suspect1Button;
  @FXML private Button suspect2Button;
  @FXML private Button suspect3Button;
  @FXML private Button sendReportButton;

  @FXML private Label startingLabel;
  @FXML private TextArea reportTextArea;

  @FXML private Label timerLabel;

  AppTimer appTimer;

  /** Initializes the guess scene. */
  @FXML
  public void initialize() {
    System.out.println("Initialising guess scene...");

    appTimer = new AppTimer(timerLabel, AppTimer.GUESSTIME);
    appTimer.beginCountdown();
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

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  public void handleSuspect1ButtonClick(ActionEvent event) {}

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {}

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  public void handleSuspect3ButtonClick(ActionEvent event) {}

  /**
   * Handles a mouse click on the send report button. Switches to the results scene only if a
   * suspect has been chosen and there is text in the report text area.
   *
   * @param event the mouse event triggered by clicking the button
   * @throws IOException 
   */
  @FXML
  public void handleSendReportClick(ActionEvent event) throws IOException {
      appTimer.cancelTimer();
      SceneManager.addUi(AppUi.GUESS, App.loadFxml("result"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS)); // Switches to guessing scene
  }
}
