package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

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
  @FXML private Rectangle caseRectangle;
  @FXML private Pane cardPane;
  @FXML private Pane casePane;
  @FXML private ImageView dirtImage;
  @FXML private ImageView scratchImage;
  @FXML private ImageView pencilImage;
  @FXML private ImageView cleaningImage;

  @FXML private Pane suspensePane;
  @FXML private Pane tigerCanPane;
  @FXML private Pane cokeZeroPane;
  @FXML private Pane hopCanPane;
  @FXML private Pane tigerBottlePane;

  private boolean napkinOn;
  private boolean clothOn;
  private boolean rubberOn;

  private ArrayList<Pane> itemPaneList = new ArrayList<>();

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialising...");
    cardPane.setVisible(false);
    casePane.setVisible(false);
    cleaningImage.setVisible(false);
    addAllItemPanes();
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
    System.out.println("Showing card clue");
    cardPane.setVisible(true);
    cardPane.setOnMouseMoved(
        event -> {
          cleaningImage.setX(event.getX() - 160);
          cleaningImage.setY(event.getY() - 215);
        });
    napkinOn = false;
    clothOn = false;
    rubberOn = false;
  }

  @FXML
  public void hideCardClue() {
    System.out.println("Hiding card clue");
    cardPane.setVisible(false);
    cleaningImage.setVisible(false);
    napkinOn = false;
    clothOn = false;
    rubberOn = false;
  }

  @FXML
  public void decreaseDirtOpacity() {
    if (napkinOn) {
      decreaseOpacity(dirtImage);
    }
  }

  @FXML
  public void decreaseScratchOpacity() {
    if (clothOn) {
      decreaseOpacity(scratchImage);
    }
  }

  @FXML
  public void decreasePencilOpacity() {
    if (rubberOn) {
      decreaseOpacity(pencilImage);
    }
  }

  @FXML
  void napkinSelected() {
    if (napkinOn) {
      cleaningImage.setVisible(false);
      napkinOn = false;
    } else {
      cleaningImage.setVisible(true);
      cleaningImage.setImage(new Image((App.class.getResource("/images/napkin.png")).toString()));
      napkinOn = true;
      clothOn = false;
      rubberOn = false;
    }
  }

  @FXML
  void clothSelected() {
    if (clothOn) {
      cleaningImage.setVisible(false);
      clothOn = false;
    } else {
      cleaningImage.setVisible(true);
      cleaningImage.setImage(new Image((App.class.getResource("/images/cloth.png")).toString()));
      napkinOn = false;
      clothOn = true;
      rubberOn = false;
    }
  }

  @FXML
  void rubberSelected() {
    if (rubberOn) {
      cleaningImage.setVisible(false);
      rubberOn = false;
    } else {
      cleaningImage.setVisible(true);
      cleaningImage.setImage(new Image((App.class.getResource("/images/rubber.png")).toString()));
      napkinOn = false;
      clothOn = false;
      rubberOn = true;
    }
  }

  @FXML
  public void showCaseClue() {
    System.out.println("Showing case clue");
    casePane.setVisible(true);
  }

  @FXML
  public void hideCaseClue() {
    System.out.println("Hiding case clue");
    casePane.setVisible(false);
  }

  @FXML
  private void displayTigerCan() {
    showPane(tigerCanPane);
  }

  @FXML
  private void displayCokeZeroCan() {
    showPane(cokeZeroPane);
  }

  @FXML
  private void displayHopCan() {
    showPane(hopCanPane);
  }

  @FXML
  private void displayTigerBottle() {
    showPane(tigerBottlePane);
  }

  public void decreaseOpacity(ImageView image) {
    image.setOpacity(image.getOpacity() - 0.005);
  }

  private void addAllItemPanes() {
    itemPaneList.add(suspensePane);
    itemPaneList.add(tigerCanPane);
    itemPaneList.add(cokeZeroPane);
    itemPaneList.add(hopCanPane);
    itemPaneList.add(tigerBottlePane);
  }

  private void showPane(Pane paneToShow) {
    for (Pane pane : itemPaneList) {
      if (pane != paneToShow) {
        pane.setVisible(false);
      }
    }
    paneToShow.setVisible(true);
  }
}
