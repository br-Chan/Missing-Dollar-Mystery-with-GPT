package nz.ac.auckland.se206.controllers;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>NOTE: This scene is the proposed 'global' scene that is hopefully able to contain the timer,
 * buttons and views of the crime scene or suspects.
 *
 * <p>This is a controller class for a fxml scene.
 */
public class GameController {

  public static final int GAMETIME = 7;

  @FXML private Pane gameView;

  @FXML private Button suspect1Button;
  @FXML private Button suspect2Button;
  @FXML private Button suspect3Button;
  @FXML private Button crimeSceneButton;

  @FXML private Label timerLabel;

  private Timer timer;
  private int timeLeft;

  /** Initializes the game scene and sets the initial game view. */
  @FXML
  public void initialize() {
    System.out.println("Initialising game scene...");
    setGameView(AppUi.SUSPECT1);
    beginCountdown(GAMETIME);
  }

  public void beginCountdown(int startingTime) {
    timeLeft = startingTime;
    timer = new Timer();

    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  // Decrement the time left and update the label.
                  timeLeft--;
                  int minutesLeft = timeLeft / 60;
                  int secondsLeft = timeLeft - 60 * minutesLeft;
                  String digitalTimeLeft = null;
                  if (secondsLeft < 10) {
                    digitalTimeLeft = "0" + minutesLeft + ":0" + secondsLeft;
                  } else {
                    digitalTimeLeft = "0" + minutesLeft + ":" + secondsLeft;
                  }
                  timerLabel.setText(digitalTimeLeft);
                  System.out.println(digitalTimeLeft);

                  // Stop the timer if 0 is reached and handle it.
                  if (timeLeft <= 0) {
                    timer.cancel();
                    handleTimeUp(startingTime);
                  }
                });
          }
        };

    timer.scheduleAtFixedRate(task, 1000, 1000);
  }

  private void handleTimeUp(int startingTime) {
    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS));
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " released");
  }

  private void setGameView(AppUi appUi) {
    gameView.getChildren().setAll(SceneManager.getUiRoot(appUi));
  }

  @FXML
  public void handleSuspect1ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT1);
  }

  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT2);
  }

  @FXML
  public void handleSuspect3ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT3);
  }

  @FXML
  public void handleCrimeSceneButtonClick(ActionEvent event) {
    setGameView(AppUi.CRIME_SCENE);
  }
}
