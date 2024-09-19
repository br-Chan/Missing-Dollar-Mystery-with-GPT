package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AppTimer;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.Suspect;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for the guess fxml scene.
 */
public class GuessController {

  private GameStateContext gameStateContext;

  @FXML private Button suspect1Button;
  @FXML private Button suspect2Button;
  @FXML private Button suspect3Button;
  @FXML private Button sendReportButton;

  @FXML private Rectangle suspect1Bg;
  @FXML private Rectangle suspect2Bg;
  @FXML private Rectangle suspect3Bg;


  @FXML private Label startingLabel;
  @FXML private TextArea reportTextArea;

  private Suspect chosenSuspect;

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
  public void handleSuspect1ButtonClick(ActionEvent event) {
    gameStateContext.setChosenSuspect(Suspect.LOUIE);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    gameStateContext.setChosenSuspect(Suspect.HUEY);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  public void handleSuspect3ButtonClick(ActionEvent event) {
    gameStateContext.setChosenSuspect(Suspect.DEWEY);
  }

  /**
   * Handles a mouse click on the send report button. Switches to the results scene only if a
   * suspect has been chosen and there is text in the report text area.
   *
   * @param event the mouse event triggered by clicking the button
   * @throws IOException
   */
  @FXML
  public void handleSendReportClick(ActionEvent event) throws IOException {
    if (gameStateContext.getChosenSuspect().equals(Suspect.NONE)
        || reportTextArea.getText().equals("")) {
          // Don't go to results scene because you need to pick a suspect & write a report.
          System.out.println("Can't send report, pick suspect & write report!");
    } else {
      appTimer.cancelTimer();
      SceneManager.addUi(AppUi.RESULT, App.loadFxml("result"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
    }
  }

  private void onSuspect1Click() {

  }

  private void onSuspect2Click() {

  }

  private void onSuspect3Click() {

  }
}
