package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * TODO: Fill in this JavaDoc comment.
 *
 * <p>TODO: Edit the respective fxml file and add to this empty class to implement features. Remove
 * default methods as needed.
 *
 * <p>This is a controller class for a fxml scene.
 */
public class CrimeSceneController {

  @FXML private Rectangle cardRectangle;
  @FXML private Pane cardPane;
  @FXML private ImageView dirtImage;
  @FXML private ImageView scratchImage;
  @FXML private ImageView pencilImage;
  @FXML private ImageView cleaningImage;

  private boolean napkinOn;
  private boolean clothOn;
  private boolean rubberOn;

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialising...");
    cardPane.setVisible(false);
    cleaningImage.setVisible(false);
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
  public void showCardClue() {
    cardPane.setVisible(true);
    cardPane.setOnMouseMoved(
        event -> {
          cleaningImage.setX(event.getX() - 160);
          cleaningImage.setY(event.getY() - 225);
        });
    napkinOn = false;
    clothOn = false;
    rubberOn = false;
  }

  @FXML
  public void hideCardClue() {
    cardPane.setVisible(false);
    cleaningImage.setVisible(false);
    napkinOn = false;
    clothOn = false;
    rubberOn = false;
  }

  @FXML
  public void decreaseDirtOpacity() {
    decreaseOpacity(dirtImage);
  }

  @FXML
  public void decreaseScratchOpacity() {
    decreaseOpacity(scratchImage);
  }

  @FXML
  public void decreasePencilOpacity() {
    decreaseOpacity(pencilImage);
  }

  @FXML
  void napkinSelected() {}

  @FXML
  void clothSelected() {}

  @FXML
  void rubberSelected() {}

  public void decreaseOpacity(ImageView image) {
    image.setOpacity(image.getOpacity() - 0.005);
  }
}
