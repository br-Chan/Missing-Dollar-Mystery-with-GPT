package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AppTimer;
import nz.ac.auckland.se206.AppTimerUser;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.Suspect;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for the guess fxml scene.
 */
public class GuessController extends AppTimerUser {
  private Suspect chosenSuspect = Suspect.NONE;

  /**
   * Initializes the guess scene.
   */
  @FXML
  public void initialize() throws URISyntaxException {
    TextToSpeech.playVoiceline("MakeAGuess");

    // Set the starting label to the default text
    System.out.println("Initialising guess scene...");


    // Set the starting label to the default text
//    appTimer = new AppTimer(this, timerLabel, AppTimer.GUESSTIME);
//    appTimer.beginCountdown();
//    setupGuessButton();
  }

  @FXML
  public void sendDataToGlobalVariables(KeyEvent event) {
//    GlobalVariables.setChosenSuspect(chosenSuspect);
//    GlobalVariables.setReport(reportTextArea.getText());
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  private void onHandleSuspect1ButtonClick(ActionEvent event) {
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect2ButtonClick(ActionEvent event) {
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect3ButtonClick() {
  }

  /**
   * Handles a mouse click on the send report button. Switches to the results scene only if a
   * suspect has been chosen and there is text in the report text area.
   *
   * @param event the mouse event triggered by clicking the button
   * @throws IOException
   */
  @FXML
  private void onHandleSendReportClick(ActionEvent event) throws IOException {
  }

  /**
   * Sets up the guess button, disabling it if a suspect has not been chosen or if the report text
   */
  private void setupGuessButton() {
  }

  /**
   * Called when app timer runs out of time (see AppTimerUser.java). Switches the scene to the
   * result scene.
   *
   * <p>TODO: add logic to switch to gameOver scene if haven't met conditions
   */
  @Override
  public void switchScene() throws IOException {
    SceneManager.addUi(AppUi.RESULT, App.loadFxml("result"));
    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
  }
}
