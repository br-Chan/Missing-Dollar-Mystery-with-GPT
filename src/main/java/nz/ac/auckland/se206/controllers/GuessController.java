package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.*;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.components.Sprite;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * Controller for the guess scene.
 *
 * <p>This class is responsible for handling user input and updating the guess scene.
 */
public class GuessController extends AppTimerUser {
  /**
   * The chosen suspect. This is set to NONE by default, and is updated when the user clicks on a
   * suspect button.
   */
  private Suspect chosenSuspect = Suspect.NONE;

  /** The final position of the items. */
  private final int[] finalPosition = {450, 150};

  /** The sprite objects for the suspects. */
  @FXML private Sprite hueySprite;

  /**
   * The sprite objects for the suspects. The sprite objects for the suspects. The sprite objects
   * for the suspects.
   */
  @FXML private Sprite duewySprite;

  /**
   * The sprite objects for the suspects. The sprite objects for the suspects. The sprite objects
   * for the suspects.
   */
  @FXML private Sprite louieSprite;

  /**
   * The text area where the user can input their report. This is used to store the user's report
   * when they click the send report button.
   */
  @FXML private TextArea reportArea;

  /**
   * The label that displays the time remaining in the guess scene. This is updated by the app timer
   */
  @FXML private Label timerLabel;

  @FXML private Button guessButton;

  /**
   * The app timer object that keeps track of the time remaining in the guess scene. This is used to
   */
  private MugshotTransition hueyTransition;

  /** The app timer object that keeps track of the time remaining in the guess scene. */
  private MugshotTransition dewyTransition;

  /** The app timer object that keeps track of the time remaining in the guess scene. */
  private MugshotTransition louieTransition;

  /** Initializes the guess scene fxml. */
  @FXML
  public void initialize() throws URISyntaxException {
    // Play the voiceline for the guess scene
    TextToSpeech.playVoiceline("MakeAGuess");

    // Set the starting label to the default text
    appTimer = new AppTimer(this, timerLabel, AppTimer.GUESSTIME);
    appTimer.beginCountdown();

    // Set up the guess button
    hueyTransition = new MugshotTransition(hueySprite, finalPosition[0], finalPosition[1]);
    // Set up the guess button
    dewyTransition = new MugshotTransition(duewySprite, finalPosition[0], finalPosition[1]);
    louieTransition = new MugshotTransition(louieSprite, finalPosition[0], finalPosition[1]);

    updateButton();
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    String pressedKey = event.getCode().toString();
    GlobalVariables.checkForCheatCode(pressedKey);

    // Activates scene-specific cheat to toggle preset explanation and select the correct suspect.
    if (pressedKey.equals("F3") && GlobalVariables.ENABLE_CHEATS) {

      GlobalVariables.togglePresetExplanationCheat(reportArea);
      onHandleSuspect3ButtonClick(); // This method also sends the data to GlobalVariables
    }

    // If the report text area is empty, disable the send report button
    updateButton();
  }

  /**
   * Sends the chosen suspect to the global variables when the user presses the enter key.
   *
   * @param event the key event triggered by pressing the enter key
   */
  @FXML
  public void sendDataToGlobalVariables(KeyEvent event) {
    GlobalVariables.setChosenSuspect(chosenSuspect);
  }

  /** Updates the report in the global variables when the user types in the report text area. */
  @FXML
  private void onHandleUpdateReport() {
    updateButton();
    GlobalVariables.setReport(reportArea.getText());
  }

  /** Update the opacity of the guess button when it isn't clickable */
  private void updateButton() {
    guessButton.setVisible(!chosenSuspect.equals(Suspect.NONE) && !reportArea.getText().isBlank());
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect1ButtonClick() {
    updateButton();
    swapImages(Suspect.HUEY);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect2ButtonClick() {
    updateButton();
    swapImages(Suspect.DEWEY);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect3ButtonClick() {
    updateButton();
    swapImages(Suspect.LOUIE);
  }

  /**
   * Swaps the images of the suspects when the user clicks on a suspect button.
   *
   * @param target the suspect that the user has chosen
   */
  private void swapImages(Suspect target) {
    // If the target is the same as the chosen suspect, do nothing
    if (chosenSuspect.equals(target)) {
      return;
    }

    // Play the transition for the target suspect and reverse the transition for the chosen suspect
    switch (target) {
      case HUEY -> hueyTransition.playForwards();
      case DEWEY -> dewyTransition.playForwards();
      case LOUIE -> louieTransition.playForwards();
    }

    // Reverse the transition for the chosen suspect
    switch (chosenSuspect) {
      case HUEY -> hueyTransition.playBackwards();
      case DEWEY -> dewyTransition.playBackwards();
      case LOUIE -> louieTransition.playBackwards();
    }

    // Set the chosen suspect to the target suspect
    GlobalVariables.setChosenSuspect(target);
    chosenSuspect = target;
  }

  /**
   * Called when app timer runs out of time (see AppTimerUser.java). Switches the scene to the
   * result scene.
   *
   * <p>TODO: add logic to switch to gameOver scene if haven't met conditions
   */
  @Override
  public void switchScene() throws IOException {
    GlobalVariables.setChosenSuspect(chosenSuspect);
    GlobalVariables.setReport(reportArea.getText());

    if (GlobalVariables.getChosenSuspect().equals(Suspect.NONE)) {
      // Conditions to proceed to results scene have not been met, so go to game over scene.
      GlobalVariables.setGameOverReason("You need to accuse a suspect in your report.");
      SceneManager.addUi(AppUi.GAME_OVER, App.loadFxml("gameOver"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GAME_OVER));

    } else if (GlobalVariables.getReport().length() == 0) {
      GlobalVariables.setGameOverReason("You need to write up your evidence in your report.");
      SceneManager.addUi(AppUi.GAME_OVER, App.loadFxml("gameOver"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GAME_OVER));

    } else {
      // Proceed to results scene to see if choice is correct/mark report.
      SceneManager.addUi(AppUi.RESULT, App.loadFxml("result"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
    }
  }

  /**
   * Called when the timer runs out of time. Switches the scene to the results scene.
   *
   * @throws IOException if the fxml file for the results scene cannot be found
   */
  @FXML
  private void onHandleSubmitReport() throws IOException {
    // Stop the timer
    appTimer.cancelTimer();
    TextToSpeech.stopPlayer();
    switchScene();
  }
}
