package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.controllers.SceneManager.AppUi;

/**
 * Controller class for the suspect1 view. Handles user interactions within the suspect1 scene where the user can
 * chat with suspect 1 and guess their profession.
 */
public class Suspect1Controller extends AbstractSuspectController {

  public Suspect1Controller() {
    suspectId = "rectPerson1";
  }

  @Override
  @FXML
  public void switchSuspectScene() {
    try {
      App.setRoot(AppUi.SUSPECT2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}