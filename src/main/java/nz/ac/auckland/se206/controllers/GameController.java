package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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

  @FXML private ImageView suspectOneSelected;
  @FXML private ImageView suspectTwoSelected;
  @FXML private ImageView suspectThreeSelected;
  @FXML private ImageView crimeSceneSelected;

  @FXML private ImageView suspectOneAlert;
  @FXML private ImageView suspectTwoAlert;
  @FXML private ImageView suspectThreeAlert;

  @FXML private static Pane displayCaseCluePane;
  @FXML private static Pane computerCluePane;
  @FXML private static Pane idCardCluePane;
  @FXML private Pane hueyPane;
  @FXML private Pane louiePane;
  @FXML private Pane deweyPane;
  @FXML private Pane leftPane;
  @FXML private Pane rightPane;

  @FXML private Label timerLabel;

  AppTimer appTimer;
  ArrayList<ImageView> selectedList = new ArrayList<>();

  private Boolean hueyVisited = false;
  private Boolean louieVisited = false;
  private Boolean deweyVisited = false;

  /** Initializes the game scene and sets the initial game view. */
  @FXML
  public void initialize() {
    System.out.println("Initialising game scene...");
    setGameView(AppUi.CRIME_SCENE);
    showSelected(crimeSceneSelected);

    appTimer = new AppTimer(timerLabel, AppTimer.GAMETIME);
    appTimer.beginCountdown();
    addAllSelected();
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
    showSelected(suspectOneSelected);
    suspectOneAlert.setImage(
        new Image(App.class.getResource("/images/suspects/Louie Avatar.png").toString()));
    louieVisited = true;
    louiePane.setVisible(false);
  }

  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT2);
    showSelected(suspectTwoSelected);
    suspectTwoAlert.setImage(
        new Image(App.class.getResource("/images/suspects/Huey Avatar.png").toString()));
    hueyVisited = true;
    hueyPane.setVisible(false);
  }

  @FXML
  public void handleSuspect3ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT3);
    showSelected(suspectThreeSelected);
    suspectThreeAlert.setImage(
        new Image(App.class.getResource("/images/suspects/Dewey Avatar.png").toString()));
    deweyVisited = true;
    deweyPane.setVisible(false);
  }

  @FXML
  public void handleCrimeSceneButtonClick(ActionEvent event) {
    setGameView(AppUi.CRIME_SCENE);
    showSelected(crimeSceneSelected);
  }

  public void showSelected(ImageView image) {
    for (ImageView imageView : selectedList) {
      imageView.setVisible(false);
    }
    image.setVisible(true);
  }

  public void addAllSelected() {
    selectedList.add(crimeSceneSelected);
    selectedList.add(suspectOneSelected);
    selectedList.add(suspectTwoSelected);
    selectedList.add(suspectThreeSelected);
  }

  public static void showCardCluePane() {
    idCardCluePane.setVisible(false);
  }

  public static void showDisplayCaseCluePane() {
    displayCaseCluePane.setVisible(false);
  }

  public static void showComputerCluePane() {
    computerCluePane.setVisible(false);
  }
}
