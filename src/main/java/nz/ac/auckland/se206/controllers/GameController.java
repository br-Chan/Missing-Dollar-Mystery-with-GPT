package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.AppTimer;
import nz.ac.auckland.se206.AppTimerUser;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

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
public class GameController extends AppTimerUser {

  @FXML private BorderPane borderPane;
  @FXML private Button suspect1Button;
  @FXML private Button suspect2Button;
  @FXML private Button suspect3Button;
  @FXML private Button crimeSceneButton;
  @FXML private Button startButton;

  @FXML private ImageView suspectOneSelected;
  @FXML private ImageView suspectTwoSelected;
  @FXML private ImageView suspectThreeSelected;
  @FXML private ImageView crimeSceneSelected;

  @FXML private ImageView suspectOneAlert;
  @FXML private ImageView suspectTwoAlert;
  @FXML private ImageView suspectThreeAlert;

  @FXML private Label timerLabel;

  @FXML private Pane hueyPane;
  @FXML private Pane louiePane;
  @FXML private Pane deweyPane;
  @FXML private Pane cluePane;
  @FXML private Pane leftPane;
  @FXML private Pane rightPane;
  @FXML private Pane gameView;
  @FXML private Pane hueyFadePane;
  @FXML private Pane louieFadePane;
  @FXML private Pane deweyFadePane;
  @FXML private Pane clueFadePane;
  @FXML private Rectangle transparentRectangle;

  private FadeTransition currentFadeTransition;

  private ArrayList<ImageView> selectedList = new ArrayList<>();

  private int hueyCount = 0;
  private int louieCount = 0;
  private int deweyCount = 0;

  /** Initializes the game scene and sets the initial game view. */
  @FXML
  public void initialize() {
    System.out.println("Initialising game scene...");
    setGameView(AppUi.STORY);
    showSelected(crimeSceneSelected); // Shows the selection box for the crimescene in minimap

    appTimer = new AppTimer(this, timerLabel, AppTimer.GAMETIME);
    appTimer.beginCountdown();
    addAllSelected(); // Adds selection images to an arraylist
    setBackgroundImage();
    borderPane.setOnMouseClicked(this::handleMouseClick);
  }

  /**
   * Updates clue pane whenever the mouse is clicked.
   *
   * @param event unused event parameter
   */
  @FXML
  private void handleMouseClick(MouseEvent event) {
    updateCluePane();
  }

  private void updateCluePane() {
    if (GlobalVariables.getCluesMap().get("computerClue")
        || GlobalVariables.getCluesMap().get("cardClue")
        || GlobalVariables.getCluesMap().get("displayCaseClue")) {
      cluePane.setVisible(false);
    }
  }

  /**
   * Switches the displayed scene when the user clicks the start button.
   *
   * @param event the mouse event triggered by clicking the button
   * @throws IOException if there is an I/O error
   * @throws URISyntaxException
   */
  @FXML
  private void onHandleStartButtonClick(ActionEvent event) throws IOException, URISyntaxException {
    onHandleCrimeSceneButtonClick(event);
  }

  /** Adds all selection box images to an arraylist. */
  public void addAllSelected() {
    selectedList.add(crimeSceneSelected);
    selectedList.add(suspectOneSelected);
    selectedList.add(suspectTwoSelected);
    selectedList.add(suspectThreeSelected);
  }

  /** Sets the background of the border pane to be an image. */
  public void setBackgroundImage() {
    Image image = new Image(App.class.getResource("/images/Background Border.png").toString());

    // Handles settings for the background image
    BackgroundImage backgroundImage =
        new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                946.8, // Width of the background image
                718.8, // Height of the background image
                false, // Whether to use a percentage value for width
                false, // Whether to use a percentage value for height
                false, // Whether the width should be set to the image's intrinsic size
                false // Whether the height should be set to the image's intrinsic size
                ));
    borderPane.setBackground(new Background(backgroundImage));
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    GlobalVariables.checkForCheatCode(event.getCode().toString());
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {}

  private void setGameView(AppUi appUi) {
    gameView.getChildren().setAll(SceneManager.getUiRoot(appUi));
  }

  /**
   * Updates all UI and variables surrounding clicking Louie on the game map. Updates map UI,
   * profile card, visited booleans, and plays TTS.
   *
   * @param event unused event parameter
   * @throws URISyntaxException throws exception regarding TTS
   */
  @FXML
  private void doStuff() {
    System.out.println("clicked");
  }

  @FXML
  private void onHandleSuspectOneButtonClick(ActionEvent event) throws URISyntaxException {
    startButton.setVisible(false);
    setGameView(AppUi.SUSPECT1);
    showSelected(suspectOneSelected);
    suspectOneAlert.setImage(
        new Image(
            App.class
                .getResource("/images/suspects/Louie Avatar.png")
                .toString())); // Updates alert image to Louies avatar
    louiePane.setVisible(false);
    louieCount++;
    // Play voiceline if first time
    if (louieCount == 1) {
      TextToSpeech.playVoiceline("Louie");
    }
  }

  /**
   * Updates all UI and variables surrounding clicking Huey on the game map. Updates map UI, profile
   * card, visited booleans, and plays TTS.
   *
   * @param event unused event parameter
   * @throws URISyntaxException throws exception regarding TTS
   */
  @FXML
  private void onHandleSuspectTwoButtonClick(ActionEvent event) throws URISyntaxException {
    startButton.setVisible(false);
    setGameView(AppUi.SUSPECT2);
    showSelected(suspectTwoSelected);
    suspectTwoAlert.setImage(
        new Image(
            App.class
                .getResource("/images/suspects/Huey Avatar.png")
                .toString())); // Updates alert image to Hueys avatar
    hueyPane.setVisible(false);
    hueyCount++;
    // Play voiceline if first time
    if (hueyCount == 1) {
      TextToSpeech.playVoiceline("Huey");
    }
  }

  /**
   * Updates all UI and variables surrounding clicking Dewey on the game map. Updates map UI,
   * profile card, visited booleans, and plays TTS.
   *
   * @param event unused event parameter
   * @throws URISyntaxException throws exception regarding TTS
   */
  @FXML
  private void onHandleSuspectThreeButtonClick(ActionEvent event) throws URISyntaxException {
    startButton.setVisible(false);
    setGameView(AppUi.SUSPECT3);
    showSelected(suspectThreeSelected);
    suspectThreeAlert.setImage(
        new Image(
            App.class
                .getResource("/images/suspects/Dewey Avatar.png")
                .toString())); // Updates alert image to Deweys avatar
    deweyPane.setVisible(false);
    deweyCount++;
    // Play voiceline if first time
    if (deweyCount == 1) {
      TextToSpeech.playVoiceline("Dewey");
    }
  }

  /**
   * Updates game window and map UI when clicking on the crime scene.
   *
   * @param event unusued event parameter
   */
  @FXML
  void onHandleCrimeSceneButtonClick(ActionEvent event) {
    startButton.setVisible(false);
    setGameView(AppUi.CRIME_SCENE);
    showSelected(crimeSceneSelected);
  }

  /**
   * Handles logic for the guess button. Checks global variable to see if all conditions have been
   * met.
   *
   * @throws IOException throws exception regarding loading FXML
   */
  @FXML
  private void onHandleGuessClick() throws IOException {
    if (GlobalVariables.canGuessThief()) {
      appTimer.cancelTimer();
      SceneManager.addUi(AppUi.GUESS, App.loadFxml("guess"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS)); // Switches to guessing scene

    } else {
      System.out.println("Cannot guess");
      flashUnvisitedPanes(); // Flash all unvisited corresponding panes
    }
  }

  /**
   * Shows one image out of a list of images, and hides the rest.
   *
   * @param image the image to be shown
   */
  public void showSelected(ImageView image) {
    for (ImageView imageView : selectedList) {
      imageView.setVisible(false); // Sets all images in the arraylist invisible
    }
    image.setVisible(true); // Sets the selected image visible
  }

  /** Flashes the panes of the suspects which have not been visited. */
  private void flashUnvisitedPanes() {
    if (!GlobalVariables.getInteractablesMap().get("suspect2")) {
      showAndFade(hueyFadePane); // Shows hueys fade pane if he hasnt been visited
    }
    if (!GlobalVariables.getInteractablesMap().get("suspect1")) {
      showAndFade(louieFadePane); // Shows louies fade pane if he hasnt been visited
    }
    if (!GlobalVariables.getInteractablesMap().get("suspect3")) {
      showAndFade(deweyFadePane); // Shows deweys fade pane if he hasnt been visited
    }
    if (!GlobalVariables.getInteractablesMap().get("computerClue")
        && !GlobalVariables.getInteractablesMap().get("cardClue")
        && !GlobalVariables.getInteractablesMap().get("displayCaseClue")) {
      showAndFade(clueFadePane); // Shows clue fade pane if he hasnt been visited
    }
  }

  /**
   * Briefly shows, then fades out a given pane.
   *
   * @param myPane the pane to be shown then faded
   */
  public void showAndFade(Pane myPane) {
    myPane.setVisible(true);

    // If there is an ongoing transition, stop it
    if (currentFadeTransition != null) {
      currentFadeTransition.stop();
    }

    // Start a new thread for the delay
    new Thread(
            () -> {
              try {
                Thread.sleep(1);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

              // Run the fade transition on the JavaFX Application Thread
              Platform.runLater(
                  () -> {
                    // Create a new FadeTransition
                    currentFadeTransition = new FadeTransition(Duration.millis(500), myPane);
                    currentFadeTransition.setFromValue(1.0); // Start fully visible
                    currentFadeTransition.setToValue(0.0); // Fade out to fully transparent

                    // Hide the pane after the fade-out completes
                    currentFadeTransition.setOnFinished(
                        event -> {
                          myPane.setVisible(false);
                          currentFadeTransition = null; // Clear the reference when done
                        });

                    // Start the fade transition
                    currentFadeTransition.play();
                  });
            })
        .start(); // Start the thread
  }

  /**
   * Called when app timer runs out of time (see AppTimerUser.java). Switches the scene to the guess
   * scene if the user has met all conditions to be able to guess the thief, otherwise switches to
   * the gameOver scene.
   */
  @Override
  public void switchScene() throws IOException {
    if (GlobalVariables.canGuessThief()) {
      SceneManager.addUi(AppUi.GUESS, App.loadFxml("guess"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS));
    } else {
      SceneManager.addUi(AppUi.GAME_OVER, App.loadFxml("gameOver"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GAME_OVER));
    }
  }
}
