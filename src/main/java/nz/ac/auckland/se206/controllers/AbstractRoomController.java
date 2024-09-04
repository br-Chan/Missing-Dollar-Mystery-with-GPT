package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.speech.TextToSpeech;

public abstract class AbstractRoomController {

  @FXML protected Button btnToSwitch;
  @FXML private Label lblProfession;

  protected String suspectId;

  protected static boolean isFirstTimeInit = true;
  protected static GameStateContext context;

  public AbstractRoomController() {
    context = new GameStateContext(this);
  }

  @FXML
  public abstract void switchRoom();

  public abstract void setProfession(String profession);

    /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialise is being run from the abstract class!");
    if (isFirstTimeInit) {
      TextToSpeech.speak(
          "Chat with the three customers, and guess who is the "
              + context.getProfessionToGuess());
      isFirstTimeInit = false;
    }
    lblProfession.setText(context.getProfessionToGuess());
    setProfession(context.getProfession(suspectId));
  }
  
}
