package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
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

  @FXML private Pane hueyPane;
  @FXML private Pane louiePane;
  @FXML private Pane deweyPane;
  @FXML private Pane leftPane;
  @FXML private Pane rightPane;
  @FXML private BorderPane borderPane;
  @FXML private Pane hueyFadePane;
  @FXML private Pane louieFadePane;
  @FXML private Pane deweyFadePane;

  @FXML private Label timerLabel;

  private FadeTransition currentFadeTransition;
  AppTimer appTimer;
  private ArrayList<ImageView> selectedList = new ArrayList<>();

  private Boolean hueyVisited = false;
  private Boolean louieVisited = false;
  private Boolean deweyVisited = false;

  /** Initializes the game scene and sets the initial game view. */
  @FXML
  public void initialize() {
    System.out.println("Initialising game scene...");
    setGameView(AppUi.CRIME_SCENE);
    showSelected(crimeSceneSelected); // Shows the selection box for the crimescene in minimap

    appTimer = new AppTimer(timerLabel, AppTimer.GAMETIME);
    appTimer.beginCountdown();
    addAllSelected(); // Adds selection images to an arraylist
    setBackgroundImage();
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
        new Image(
            App.class
                .getResource("/images/suspects/Louie Avatar.png")
                .toString())); // Updates alert image to Louies avatar
    louieVisited = true;
    louiePane.setVisible(false);
  }

  @FXML
  public void handleSuspect2ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT2);
    showSelected(suspectTwoSelected);
    suspectTwoAlert.setImage(
        new Image(
            App.class
                .getResource("/images/suspects/Huey Avatar.png")
                .toString())); // Updates alert image to Hueys avatar
    hueyVisited = true;
    hueyPane.setVisible(false);
  }

  @FXML
  public void handleSuspect3ButtonClick(ActionEvent event) {
    setGameView(AppUi.SUSPECT3);
    showSelected(suspectThreeSelected);
    suspectThreeAlert.setImage(
        new Image(
            App.class
                .getResource("/images/suspects/Dewey Avatar.png")
                .toString())); // Updates alert image to Deweys avatar
    deweyVisited = true;
    deweyPane.setVisible(false);
  }

  @FXML
  public void handleCrimeSceneButtonClick(ActionEvent event) {
    setGameView(AppUi.CRIME_SCENE);
    showSelected(crimeSceneSelected);
  }

  @FXML
  public void handleGuessClick() throws IOException {
    if (!hueyVisited || !louieVisited || !deweyVisited) {
      flashUnvisitedPanes(); // Flash all unvisited corresponding panes
    } else {
      appTimer.cancelTimer();
      SceneManager.addUi(AppUi.GUESS, App.loadFxml("guess"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS)); // Switches to guessing scene
    }
  }

  /** Flashes the panes of the suspects which have not been visited. */
  private void flashUnvisitedPanes() {
    if (!hueyVisited) {
      showAndFade(hueyFadePane); // Shows hueys fade pane if he hasnt been visited
    }
    if (!louieVisited) {
      showAndFade(louieFadePane); // Shows louies fade pane if he hasnt been visited
    }
    if (!deweyVisited) {
      showAndFade(deweyFadePane); // Shows deweys fade pane if he hasnt been visited
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
            BackgroundSize.DEFAULT);
    borderPane.setBackground(new Background(backgroundImage));
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
}
