package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import nz.ac.auckland.se206.*;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.components.Sprite;
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
  private final int[] finalPosition = {365, 135};


  @FXML
  private Sprite hueySprite;
  @FXML
  private Sprite duewySprite;
  @FXML
  private Sprite louieSprite;
  @FXML
  private TextArea reportArea;

  @FXML
  private Label timerLabel;

  private MugshotTransition hueyTransition;
  private MugshotTransition dewyTransition;
  private MugshotTransition louieTransition;

  /**
   * Initializes the guess scene.
   */
  @FXML
  public void initialize() throws URISyntaxException {
    TextToSpeech.playVoiceline("MakeAGuess");

    // Set the starting label to the default text
    System.out.println("Initialising guess scene...");


    // Set the starting label to the default text
    appTimer = new AppTimer(this, timerLabel, AppTimer.GUESSTIME);
    appTimer.beginCountdown();

    hueyTransition = new MugshotTransition(hueySprite, finalPosition[0], finalPosition[1]);
    dewyTransition = new MugshotTransition(duewySprite, finalPosition[0], finalPosition[1]);
    louieTransition = new MugshotTransition(louieSprite, finalPosition[0], finalPosition[1]);
  }

  @FXML
  public void sendDataToGlobalVariables(KeyEvent event) {
    GlobalVariables.setChosenSuspect(chosenSuspect);
  }


  @FXML
  private void onHandleUpdateReport() {
    GlobalVariables.setReport(reportArea.getText());
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect1ButtonClick() {
    swapImages(Suspect.HUEY);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect2ButtonClick() {
    swapImages(Suspect.DEWEY);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  private void onHandleSuspect3ButtonClick() {
    swapImages(Suspect.LOUIE);
  }

  private void swapImages(Suspect target) {
    if (chosenSuspect.equals(target)) {
      return;
    }

    switch (target) {
      case HUEY -> hueyTransition.playForwards();
      case DEWEY -> dewyTransition.playForwards();
      case LOUIE -> louieTransition.playForwards();
    }

    switch (chosenSuspect) {
      case HUEY -> hueyTransition.playBackwards();
      case DEWEY -> dewyTransition.playBackwards();
      case LOUIE -> louieTransition.playBackwards();
    }

    GlobalVariables.setChosenSuspect(target);
    chosenSuspect = target;
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

  @FXML
  public void onHandleSubmitReport() throws IOException {
    switchScene();
  }
}
