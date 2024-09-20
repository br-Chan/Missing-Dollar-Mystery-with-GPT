package nz.ac.auckland.se206.controllers;

import java.io.IOException;
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
  @FXML private Button sendReportButton;

  @FXML private Rectangle suspect1Bg;
  @FXML private Rectangle suspect2Bg;
  @FXML private Rectangle suspect3Bg;

  @FXML private Label startingLabel;
  @FXML private TextArea reportTextArea;
  @FXML private Label timerLabel;

  private Suspect chosenSuspect = Suspect.NONE;

  // GameStateContext gameStateContext = new GameStateContext(null);

  AppTimer appTimer;

  private final Color selectedBg = Color.rgb(255, 255, 255);
  private final Color notSelectedBug = Color.rgb(150, 150, 150);

  /** Initializes the guess scene. */
  @FXML
  public void initialize() {
    // Set the starting label to the default text
    System.out.println("Initialising guess scene...");

    // Set the starting label to the default text
    appTimer = new AppTimer(timerLabel, AppTimer.GUESSTIME);
    appTimer.beginCountdown();
    setupGuessButton();
  }

  @FXML
  public void sendDataToContext(KeyEvent event) {
    GameStateContext.setChosenSuspect(chosenSuspect);
    System.out.println(GameStateContext.getChosenSuspect());

    GameStateContext.setReport(reportTextArea.getText());
    System.out.println(GameStateContext.getReport());
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void handleReportIssueKeyEvent(KeyEvent event) {
    // If the report text area is empty, disable the send report button
    setupGuessButton();
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   *
   * @param event the mouse event triggered by clicking the button
   */
  @FXML
  public void handleSuspect1ButtonClick(ActionEvent event) {
    chosenSuspect = Suspect.LOUIE;
    sendDataToContext(null);
    suspect1Bg.setFill(selectedBg);

    suspect2Bg.setFill(notSelectedBug);
    suspect3Bg.setFill(notSelectedBug);

    setupGuessButton();
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    // Set the chosen suspect to Huey
    chosenSuspect = Suspect.HUEY;
    sendDataToContext(null);
    suspect2Bg.setFill(selectedBg);

    // Set the other suspects to not selected
    suspect1Bg.setFill(notSelectedBug);
    suspect3Bg.setFill(notSelectedBug);

    setupGuessButton();
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  public void handleSuspect3ButtonClick() {
    // Set the chosen suspect to Dewey
    chosenSuspect = Suspect.DEWEY;
    sendDataToContext(null);
    suspect3Bg.setFill(selectedBg);

    // Set the other suspects to not selected
    suspect2Bg.setFill(notSelectedBug);
    suspect1Bg.setFill(notSelectedBug);

    setupGuessButton();
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
    if (chosenSuspect.equals(Suspect.NONE) || reportTextArea.getText().isEmpty()) {
      // Don't go to results scene because you need to pick a suspect & write a report.
      System.out.println("Can't send report, pick suspect & write report!");
      return;
    }

    // Stop the timer
    appTimer.cancelTimer();
    var loader = new FXMLLoader(GuessController.class.getResource("/fxml/result.fxml"));
    Parent root = loader.load();
    ResultController controller = loader.getController();
    // Set the result of the guess
    // controller.setResult(chosenSuspect.equals(Suspect.LOUIE), reportTextArea.getText());
    SceneManager.addUi(AppUi.RESULT, root);

    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
  }

  /**
   * Sets up the guess button, disabling it if a suspect has not been chosen or if the report text
   */
  private void setupGuessButton() {
    // If no suspect has been chosen or the report text area is empty, disable the send report
    if (chosenSuspect.equals(Suspect.NONE) || reportTextArea.getText().isBlank()) {
      sendReportButton.setDisable(true);
      sendReportButton.setOpacity(0.5);
      return;
    }

    // Otherwise, enable the send report button
    sendReportButton.setDisable(false);
    sendReportButton.setOpacity(1);
  }
}
