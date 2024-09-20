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
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AppTimer;
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
  @FXML
  private Button sendReportButton;

  @FXML
  private Rectangle suspect1Bg;
  @FXML
  private Rectangle suspect2Bg;
  @FXML
  private Rectangle suspect3Bg;


  @FXML
  private Label startingLabel;
  @FXML
  private TextArea reportTextArea;
  @FXML
  private Label timerLabel;

  private Suspect chosenSuspect = Suspect.NONE;

  AppTimer appTimer;

  private final Color selectedBg = Color.rgb(255, 255, 255);
  private final Color notSelectedBug = Color.rgb(150, 150, 150);

  /**
   * Initializes the guess scene.
   */
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
  public void onKeyPressed(KeyEvent event) {
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
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
    suspect1Bg.setFill(Color.rgb(255, 255, 255));

    suspect2Bg.setFill(Color.rgb(191, 191, 191));
    suspect3Bg.setFill(Color.rgb(191, 191, 191));
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    chosenSuspect = Suspect.HUEY;
    suspect2Bg.setFill(selectedBg);

    suspect1Bg.setFill(notSelectedBug);
    suspect3Bg.setFill(notSelectedBug);
  }

  /**
   * Handles mouse clicks on the suspect 1 button, setting which suspect the user has chosen and
   * updating the starting label.
   */
  @FXML
  public void handleSuspect3ButtonClick() {
    chosenSuspect = Suspect.DEWEY;
    suspect3Bg.setFill(selectedBg);

    suspect2Bg.setFill(notSelectedBug);
    suspect1Bg.setFill(notSelectedBug);
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
    if (chosenSuspect.equals(Suspect.NONE)
        || reportTextArea.getText().isEmpty()) {
      // Don't go to results scene because you need to pick a suspect & write a report.
      System.out.println("Can't send report, pick suspect & write report!");
      return;
    }

    appTimer.cancelTimer();
    var loader = new FXMLLoader(GuessController.class.getResource("/fxml/result.fxml"));
    Parent root = loader.load();
    ResultController controller = loader.getController();
    controller.setResult(chosenSuspect.equals(Suspect.LOUIE), reportTextArea.getText());
    SceneManager.addUi(AppUi.RESULT, root);

    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
  }
}
