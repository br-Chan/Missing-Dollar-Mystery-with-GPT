package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.controllers.SceneManager.AppUi;

/**
 * Controller class for the suspect2 view. Handles user interactions within the suspect2 scene where the user can
 * chat with suspect 2 and guess their profession.
 */
public class Suspect2Controller extends AbstractSuspectController {
  public Suspect2Controller() {
    suspectId = "rectPerson2";
  }

  @Override
  @FXML
  public void switchSuspectScene() {
    try {
      App.setRoot(AppUi.SUSPECT1);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}