package nz.ac.auckland.se206.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
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
  @FXML private Pane computerPane;
  @FXML private ImageView dirtImage;
  @FXML private ImageView debrisImage;
  @FXML private ImageView pencilImage;
  @FXML private ImageView cleaningImage;
  @FXML private Label cardDirtyLabel;
  @FXML private Button searchStockButton;
  @FXML private Button searchLogsButton;
  @FXML private TextField searchStockField;
  @FXML private TextField searchLogsStart;
  @FXML private TextField searchLogsEnd;
  @FXML private TextArea logsArea;

  @FXML private Pane suspensePane;
  @FXML private Pane redDrinkPane;
  @FXML private Pane blueDrinkPane;
  @FXML private Pane greenDrinkPane;
  @FXML private Pane pinkDrinkPane;
  @FXML private Pane yellowDrinkPane;
  @FXML private Pane logPane;
  @FXML private Pane stockPane;
  @FXML private Pane errorStockPane;
  @FXML private Pane redStockPane;
  @FXML private Pane blueStockPane;
  @FXML private Pane greenStockPane;
  @FXML private Pane pinkStockPane;
  @FXML private Pane yellowStockPane;

  private boolean napkinOn;
  private boolean brushOn;
  private boolean rubberOn;
  private boolean cardCleaned = false;
  private boolean stockAppOpen = false;
  private boolean logsAppOpen = false;

  private ArrayList<Pane> itemPaneList = new ArrayList<>();
  private ArrayList<Pane> stockPaneList = new ArrayList<>();
  private ArrayList<String> logsList = new ArrayList<>();

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
    computerPane.setVisible(false);
    cleaningImage.setVisible(false);
    addAllItemPanes();
    addAllStockPanes();
    addAllLogs();
  }

  /**
   * Handles the key pressed event, sending a message if the user presses ENTER.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException {
    System.out.println("Key " + event.getCode() + " pressed");
    if (event.getCode().toString().equals("ENTER") && stockAppOpen) {
      searchStock();
    } else if (event.getCode().toString().equals("ENTER") && logsAppOpen) {
      searchLogs();
    }
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
    cardPane.setOnMouseDragged(
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

  @FXML
  public void showComputerClue() {
    System.out.println("Showing computer clue");
    computerPane.setVisible(true);
  }

  @FXML
  public void hideComputerClue() {
    System.out.println("Hiding computer clue");
    computerPane.setVisible(false);
  }

  @FXML
  public void openLogs() {
    logPane.setVisible(true);
    stockPane.setVisible(false);
    stockAppOpen = false;
    logsAppOpen = true;
  }

  @FXML
  public void openStock() {
    logPane.setVisible(false);
    stockPane.setVisible(true);
    stockAppOpen = true;
    logsAppOpen = false;
  }

  @FXML
  public void searchStock() {
    String query = searchStockField.getText().trim().toLowerCase();
    searchStockField.clear();
    switch (query) {
      case "cola crush":
        showStock(redStockPane);
        break;
      case "coffee craze":
        showStock(blueStockPane);
        break;
      case "elite energy":
        showStock(greenStockPane);
        break;
      case "berry burst":
        showStock(pinkStockPane);
        break;
      case "lemon lift":
        showStock(yellowStockPane);
        break;
      default:
        showStock(errorStockPane);
        break;
    }
  }

  @FXML
  public void searchLogs() {
    String start = searchLogsStart.getText().trim();
    String end = searchLogsEnd.getText().trim();
    if(start.isEmpty() || end.isEmpty()){
      return;
    }
    searchLogsStart.clear();
    searchLogsEnd.clear();

    if (!checkLogsInteger(start, end)) {
      logsArea.setText("Invalid input!\nPlease enter an integer.");
    } else if (!checkValidInput(start, end)) {
      logsArea.setText("Invalid input!\nPlease enter a number from 0 to 24.");
    } else if (Integer.parseInt(start) > Integer.parseInt(end)) {
      logsArea.setText("Invalid input!\nStart time must be earlier than end time.");
    } else {
      updateLogs(Integer.parseInt(start), Integer.parseInt(end));
    }
  }

  private boolean checkValidInput(String start, String end) {
    if (Integer.parseInt(start) > 24 || Integer.parseInt(end) > 24) {
      return false;
    } else if (Integer.parseInt(start) < 0 || Integer.parseInt(end) < 0) {
      return false;
    }
    return true;
  }

  private boolean checkLogsInteger(String start, String end) {
    try {
      Integer.parseInt(start);
      Integer.parseInt(end);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
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

  private void addAllStockPanes() {
    stockPaneList.add(errorStockPane);
    stockPaneList.add(redStockPane);
    stockPaneList.add(blueStockPane);
    stockPaneList.add(greenStockPane);
    stockPaneList.add(pinkStockPane);
    stockPaneList.add(yellowStockPane);
  }

  private void showPane(Pane paneToShow) {
    for (Pane pane : itemPaneList) {
      if (pane != paneToShow) {
        pane.setVisible(false);
      }
    }
    paneToShow.setVisible(true);
  }

  private void showStock(Pane paneToShow) {
    for (Pane pane : stockPaneList) {
      if (pane != paneToShow) {
        pane.setVisible(false);
      }
    }
    paneToShow.setVisible(true);
  }

  private void updateLogs(Integer start, Integer end) {
    String logsString = "";
    if (start == end) {
      logsString += "Showing logs during " + start + ":00,";
      logsString = logsList.get(start);
    } else {
      logsString += "Showing logs from " + start + ":00 to " + end + ":00,";
      for (int i = start; i < end; i++) {
        logsString += logsList.get(i);
      }
    }

    logsArea.setText(logsString.replaceAll(",", "\n"));
  }

  private void addAllLogs() {
    InputStream inputStream = App.class.getResourceAsStream("/data/logs.txt");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        logsList.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
