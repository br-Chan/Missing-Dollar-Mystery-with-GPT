package nz.ac.auckland.se206.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GlobalVariables;
import nz.ac.auckland.se206.components.Sprite;
import nz.ac.auckland.se206.speech.TextToSpeech;

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
  @FXML private Pane cardChatPane;
  @FXML private Pane cardPane;
  @FXML private Pane casePane;
  @FXML private Pane computerPane;
  @FXML private Sprite dirtImage;
  @FXML private Sprite debrisImage;
  @FXML private Sprite pencilImage;
  @FXML private Sprite cleaningImage;
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
  private boolean stockAppOpen = false;
  private boolean logsAppOpen = false;

  private boolean firstTimeCardClue = true;
  private boolean firstTimeDisplayCaseClue = true;
  private boolean firstTimeComputerClue = true;

  private ArrayList<Pane> itemPaneList = new ArrayList<>();
  private ArrayList<Pane> stockPaneList = new ArrayList<>();
  private ArrayList<String> logsList = new ArrayList<>();

  /**
   * TODO: Fill in this JavaDoc comment.
   *
   * <p>Initializes the scene view.
   *
   * @throws URISyntaxException
   */
  @FXML
  public void initialize() throws URISyntaxException {
    System.out.println("Initialising crime scene scene...");
    // Hides all panes relating to clues
    cardPane.setVisible(false);
    casePane.setVisible(false);
    computerPane.setVisible(false);
    cleaningImage.setVisible(false);
    // Adds all panes and logs lines to their respective arraylists
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
    // Searches either stock or logs, depending on which app is open
    if (event.getCode().toString().equals("ENTER") && stockAppOpen) {
      onHandleSearchStock();
    } else if (event.getCode().toString().equals("ENTER") && logsAppOpen) {
      onHandleSearchLogs();
    }
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {}

  /**
   * Sets the card clue to be visible, allows for mouse tracking with the cleaning tool.
   *
   * @throws URISyntaxException
   */
  @FXML
  public void showCardClue() throws URISyntaxException {
    GlobalVariables.clueClicked("cardClue");
    cardPane.setVisible(true);

    // This is code that was in checkCardCleaned but is now here to automatically reveal the chat
    cardDirtyLabel.setVisible(false);
    cardChatPane.setVisible(true);

    // Tracks the cleaning tool on mouse move or drag
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
    // Play voiceline if first time
    if (firstTimeCardClue) {
      TextToSpeech.playVoiceline("IDCard");
      firstTimeCardClue = false;
    }
  }

  @FXML
  public void hideCardClue() {
    // Sets the card clue pane invisible
    cardPane.setVisible(false);
    cleaningImage.setVisible(false);
    // Turns off all cleaning images
    napkinOn = false;
    brushOn = false;
    rubberOn = false;
  }

  @FXML
  public void decreaseDebrisOpacity() {
    if (napkinOn) {
      decreaseOpacity(debrisImage);
      if (debrisImage.getOpacity() <= 0) {
        System.out.println("debris are all gone, vanishing napkin");
        napkinSelected(); // to remove napkin cursor image
      } else if (debrisImage.getOpacity() < 0.1) {
        GlobalVariables.setCardProfilePicClean(true);
      }
    }
  }

  @FXML
  public void decreaseDirtOpacity() {
    if (brushOn) {
      decreaseOpacity(dirtImage);
      if (dirtImage.getOpacity() <= 0) {
        System.out.println("dirt is all gone, vanishing brush");
        brushSelected(); // to remove brush cursor image
      } else if (dirtImage.getOpacity() < 0.1) {
        GlobalVariables.setCardDetailsBottomClean(true);
      }
    }
  }

  @FXML
  public void decreasePencilOpacity() {
    if (rubberOn) {
      decreaseOpacity(pencilImage);
      if (pencilImage.getOpacity() <= 0) {
        System.out.println("pencil is all gone, vanishing rubber");
        rubberSelected(); // to remove rubber cursor image
      } else if (pencilImage.getOpacity() < 0.01) {
        GlobalVariables.setCardDetailsTopClean(true);
      }
    }
  }

  public void checkCardCleaned() {
    // Check if thing on the card has been cleaning
    if (GlobalVariables.isCardProfilePicClean()
        && GlobalVariables.isCardDetailsBottomClean()
        && GlobalVariables.isCardDetailsTopClean()) {
      cleaningImage.setVisible(false);
      brushOn = false;
      napkinOn = false;
      rubberOn = false;
    }
  }

  @FXML
  void napkinSelected() {
    // Toggles napkin on and off
    if (napkinOn) {
      cleaningImage.setVisible(false); // Sets the napkin as invisible
      napkinOn = false;
    } else {
      // Changes current cleaning image to the napkin
      cleaningImage.setVisible(true);
      cleaningImage
          .spriteUrlProperty()
          .set(
              CrimeSceneController.class
                  .getResource("/images/crimeScene/cardClue/napkin.png")
                  .toString());
      napkinOn = true;
      brushOn = false;
      rubberOn = false;
    }
  }

  @FXML
  void brushSelected() {
    // Toggles brush on and off
    if (brushOn) {
      cleaningImage.setVisible(false); // Sets the brush invisible
      brushOn = false;
    } else {
      // Changes current cleaning image to the brush
      cleaningImage.setVisible(true);
      cleaningImage
          .spriteUrlProperty()
          .set(
              CrimeSceneController.class
                  .getResource("/images/crimeScene/cardClue/brush.png")
                  .toString());
      napkinOn = false;
      brushOn = true;
      rubberOn = false;
    }
  }

  @FXML
  void rubberSelected() {
    // Toggles rubber on and off
    if (rubberOn) {
      cleaningImage.setVisible(false); // Sets the rubber invisible
      rubberOn = false;
    } else {
      // Changes current cleaning images to the rubber
      cleaningImage.setVisible(true);
      cleaningImage
          .spriteUrlProperty()
          .set(
              CrimeSceneController.class
                  .getResource("/images/crimeScene/cardClue/rubber.png")
                  .toString());
      napkinOn = false;
      brushOn = false;
      rubberOn = true;
    }
  }

  @FXML
  public void showCaseClue() throws URISyntaxException {
    GlobalVariables.clueClicked("displayCaseClue");
    casePane.setVisible(true);
    // Play voiceline if first time
    if (firstTimeDisplayCaseClue) {
      TextToSpeech.playVoiceline("DisplayCase");
      firstTimeDisplayCaseClue = false;
    }
  }

  @FXML
  public void hideCaseClue() {
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
  public void showComputerClue() throws URISyntaxException {
    GlobalVariables.clueClicked("computerClue");
    computerPane.setVisible(true);
    // Play voiceline if first time
    if (firstTimeComputerClue) {
      TextToSpeech.playVoiceline("Computer");
      firstTimeComputerClue = false;
    }
  }

  @FXML
  public void hideComputerClue() {
    computerPane.setVisible(false);
  }

  @FXML
  public void openLogs() {
    // Makes the logs app visible
    logPane.setVisible(true);
    stockPane.setVisible(false);
    // Turns on the logs app for search feature
    stockAppOpen = false;
    logsAppOpen = true;
  }

  @FXML
  public void openStock() {
    // Makes the stock app visible
    logPane.setVisible(false);
    stockPane.setVisible(true);
    // Turns on the stock app for search feature
    stockAppOpen = true;
    logsAppOpen = false;
  }

  @FXML
  private void onHandleSearchStock() {
    // Recieves the input in the search bar
    String query = searchStockField.getText().trim().toLowerCase();
    searchStockField.clear();
    // Shows corresponding item depending on the text input
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

    // Mark the computer clue as having been interacted with.
    GlobalVariables.handleInteraction("computerClue");
  }

  @FXML
  private void onHandleSearchLogs() {
    // Recieves input from the search bar
    String start = searchLogsStart.getText().trim();
    String end = searchLogsEnd.getText().trim();
    // Returns nothing if either is empty
    if (start.isEmpty() || end.isEmpty()) {
      return;
    }
    searchLogsStart.clear();
    searchLogsEnd.clear();

    // Runs checks for different errors
    if (!checkLogsInteger(start, end)) {
      logsArea.setText("Invalid input!\nPlease enter an integer."); // If the input isnt an integer
    } else if (!checkValidInput(start, end)) {
      logsArea.setText(
          "Invalid input!\nPlease enter a number from 0 to 24."); // If the input is out of bounds
    } else if (Integer.parseInt(start) > Integer.parseInt(end)) {
      logsArea.setText(
          "Invalid input!\nStart time must be earlier than end time."); // If the input order is
      // wrong
    } else {
      updateLogs(Integer.parseInt(start), Integer.parseInt(end));
    }

    // Mark the computer clue as having been interacted with.
    GlobalVariables.handleInteraction("computerClue");
  }

  private boolean checkValidInput(String start, String end) {
    // Checks the integer value of the start and end input
    if (Integer.parseInt(start) > 24 || Integer.parseInt(end) > 24) {
      return false;
    } else if (Integer.parseInt(start) < 0 || Integer.parseInt(end) < 0) {
      return false;
    }
    return true;
  }

  private boolean checkLogsInteger(String start, String end) {
    // Checks the validity of the start and end input
    try {
      Integer.parseInt(start);
      Integer.parseInt(end);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public void decreaseOpacity(Node image) {
    // Decrease the opacity of an image by a set amount
    image.setOpacity(image.getOpacity() - 0.005);
    // checkCardCleaned();
  }

  private void addAllItemPanes() {
    // Adds all item panes to an arraylist
    itemPaneList.add(suspensePane);
    itemPaneList.add(redDrinkPane);
    itemPaneList.add(blueDrinkPane);
    itemPaneList.add(greenDrinkPane);
    itemPaneList.add(pinkDrinkPane);
    itemPaneList.add(yellowDrinkPane);
  }

  private void addAllStockPanes() {
    // Adds all stock app panes to an arraylist
    stockPaneList.add(errorStockPane);
    stockPaneList.add(redStockPane);
    stockPaneList.add(blueStockPane);
    stockPaneList.add(greenStockPane);
    stockPaneList.add(pinkStockPane);
    stockPaneList.add(yellowStockPane);
  }

  private void showPane(Pane paneToShow) {
    // Hides all panes in an arraylist
    for (Pane pane : itemPaneList) {
      if (pane != paneToShow) {
        pane.setVisible(false);
      }
    }
    // Sets the pane to show to be visible
    paneToShow.setVisible(true);

    // Mark the display case clue as having been interacted with.
    GlobalVariables.handleInteraction("displayCaseClue");
  }

  private void showStock(Pane paneToShow) {
    // Hides all stock panes in the stock pane list
    for (Pane pane : stockPaneList) {
      if (pane != paneToShow) {
        pane.setVisible(false);
      }
    }
    // Shows the given stock pane
    paneToShow.setVisible(true);
  }

  private void updateLogs(Integer start, Integer end) {
    // Initialises an empty log
    StringBuilder logsString = new StringBuilder();

    // Different cases for different start and end inputs
    if (start == end) {
      logsString
          .append("Showing logs during ")
          .append(start)
          .append(":00,"); // Shows the start time if both start and end are the same

      logsString.append(logsList.get(start));
    } else {
      // Shows a time period if different
      logsString
          .append("Showing logs from ")
          .append(start)
          .append(":00 to ")
          .append(end)
          .append(":00,");
      for (int i = start; i < end; i++) {
        logsString.append(logsList.get(i));
      }
    }
    // Replaces commas with a newlien character for formatting
    logsArea.setText(logsString.toString().replaceAll(",", "\n"));
  }

  private void addAllLogs() {
    // Inports all logs from a txt file
    InputStream inputStream = App.class.getResourceAsStream("/data/logs.txt");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      // Reads each line and adds to an arraylist
      String line;
      while ((line = reader.readLine()) != null) {
        logsList.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
