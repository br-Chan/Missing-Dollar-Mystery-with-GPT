package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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

  @FXML private Pane gameView;

  @FXML private Button suspect1Button;
  @FXML private Button suspect2Button;
  @FXML private Button suspect3Button;
  @FXML private Button crimeSceneButton;

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialising...");
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

  @FXML
  public void handleSuspect1ButtonClick(ActionEvent event) {
    gameView.getChildren().setAll(SceneManager.getUiRoot(AppUi.SUSPECT1));
  }

  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    gameView.getChildren().setAll(SceneManager.getUiRoot(AppUi.SUSPECT2));
  }

  @FXML
  public void handleSuspect3ButtonClick(ActionEvent event) {
    gameView.getChildren().setAll(SceneManager.getUiRoot(AppUi.SUSPECT3));
  }

  @FXML
  public void handleCrimeSceneButtonClick(ActionEvent event) {
    gameView.getChildren().setAll(SceneManager.getUiRoot(AppUi.CRIME_SCENE));
  }
}
