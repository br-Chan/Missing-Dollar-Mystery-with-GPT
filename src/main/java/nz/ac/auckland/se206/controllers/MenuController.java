package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class MenuController {

  @FXML
  private void initialize() {}

  @FXML private Button startButton;

  /**
   * Switches the displayed scene when the user clicks the start button.
   *
   * @param event the mouse event triggered by clicking the button
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleStartButtonClick(ActionEvent event) throws IOException {
    // Initialise the scene only when the start button is clicked so that the app timer starts
    // counting down at the right time.
    SceneManager.addUi(AppUi.GAME, App.loadFxml("game"));
    App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GAME));
  }
}
