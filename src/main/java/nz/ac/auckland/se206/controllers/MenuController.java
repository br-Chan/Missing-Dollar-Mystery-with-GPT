package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;

public class MenuController {

  @FXML
  private void initialize() {}

  @FXML private Button btnStart;

  /**
   * Switches the displayed scene from the start menu to room.fxml.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void switchToRoom(ActionEvent event) throws IOException { // change in scene builder
    App.getScene().setRoot(App.getRoomController());
  }
}
