package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
  @FXML private Pane aiCardCluePane;
  @FXML private Pane cardPane;
  @FXML private Pane casePane;
  @FXML private ImageView dirtImage;
  @FXML private ImageView debrisImage;
  @FXML private ImageView pencilImage;
  @FXML private ImageView cleaningImage;
  @FXML private Label cardDirtyLabel;

  @FXML private Pane suspensePane;
  @FXML private Pane redDrinkPane;
  @FXML private Pane blueDrinkPane;
  @FXML private Pane greenDrinkPane;
  @FXML private Pane pinkDrinkPane;
  @FXML private Pane yellowDrinkPane;

  private boolean napkinOn;
  private boolean brushOn;
  private boolean rubberOn;
  private boolean cardCleaned = false;

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
    brushOn = false;
    rubberOn = false;
  }

  @FXML
  public void hideCardClue() {
    System.out.println("Hiding card clue");
    cardPane.setVisible(false);
    cleaningImage.setVisible(false);
    napkinOn = false;
    brushOn = false;
    rubberOn = false;
  }

  @FXML
  public void decreaseDirtOpacity() {
    if (brushOn) {
      decreaseOpacity(dirtImage);
    }
  }

  @FXML
  public void decreaseDebrisOpacity() {
    if (napkinOn) {
      decreaseOpacity(debrisImage);
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
      cleaningImage.setImage(
          new Image((App.class.getResource("/images/crimeScene/cardClue/napkin.png")).toString()));
      napkinOn = true;
      brushOn = false;
      rubberOn = false;
    }
  }

  @FXML
  void brushSelected() {
    if (brushOn) {
      cleaningImage.setVisible(false);
      brushOn = false;
    } else {
      cleaningImage.setVisible(true);
      cleaningImage.setImage(
          new Image((App.class.getResource("/images/crimeScene/cardClue/brush.png")).toString()));
      napkinOn = false;
      brushOn = true;
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
      cleaningImage.setImage(
          new Image((App.class.getResource("/images/crimeScene/cardClue/rubber.png")).toString()));
      napkinOn = false;
      brushOn = false;
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
  private void displayRedDrink() {
    showPane(redDrinkPane);
  }

  @FXML
  private void displayBlueDrink() {
    showPane(blueDrinkPane);
  }

  @FXML
  private void displayGreenDrink() {
    showPane(greenDrinkPane);
  }

  @FXML
  private void displayPinkDrink() {
    showPane(pinkDrinkPane);
  }

  @FXML
  private void displayYellowDrink() {
    showPane(yellowDrinkPane);
  }

  public void checkCardCleaned() {
    if (dirtImage.getOpacity() < 0.1
        && debrisImage.getOpacity() < 0.1
        && pencilImage.getOpacity() < 0.1) {
      cardCleaned = true;
      cardDirtyLabel.setVisible(false);
      aiCardCluePane.setVisible(true);
    }
  }

  public void decreaseOpacity(ImageView image) {
    image.setOpacity(image.getOpacity() - 0.005);
    checkCardCleaned();
  }

  private void addAllItemPanes() {
    itemPaneList.add(suspensePane);
    itemPaneList.add(redDrinkPane);
    itemPaneList.add(blueDrinkPane);
    itemPaneList.add(greenDrinkPane);
    itemPaneList.add(pinkDrinkPane);
    itemPaneList.add(yellowDrinkPane);
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
