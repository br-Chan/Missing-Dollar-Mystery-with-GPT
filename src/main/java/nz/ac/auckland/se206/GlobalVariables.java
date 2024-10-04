package nz.ac.auckland.se206;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Class for managing and performing operations on global variables of the game. */
public class GlobalVariables {

  private static Map<String, Boolean> interactablesMap = new HashMap<>();

  private static Suspect chosenSuspect = Suspect.NONE;
  private static String report;

  // TODO: Set this boolean to false before final release, and turn on for client presentation.
  public static final boolean ENABLE_CHEATS = true;

  private static boolean muteTts = false;
  private static boolean interactablesOverriddenTrue = false;
  private static boolean presetExplanationIsCorrect = false;

  private static String correctPresetExplanation =
      "Louie was the last to leave at 11 PM, and that is when he must have stolen the dollar.\r\n"
          + "Louie's ID card was found at the crime scene, which Louie must have accidentally left"
          + " behind when he stole the dollar.\r\n"
          + "The vending machine has 1 less Berry Burst can than the stock list on the computer"
          + " does, because Louie spilled his Berry Burst and thought he deserved a new one, and so"
          + " stole it.";
  private static String incorrectPresetExplanation =
      "Dewey’s ID card was found at the crime scene, which Louie must have stolen from Dewey and"
          + " accidentally left behind when he stole the dollar.\r\n"
          + "The vending machine has 1 less Elite Energy bottle than the stock list on the computer"
          + " does, because Louie spilled Elite Energy and thought he deserved a new one.";

  public static void setReport(String report) {
    GlobalVariables.report = report;
  }

  public static void setChosenSuspect(Suspect suspect) {
    GlobalVariables.chosenSuspect = suspect;
  }

  public static boolean isMuteTts() {
    return muteTts;
  }

  public static Map<String, Boolean> getInteractablesMap() {
    return interactablesMap;
  }

  public static String getReport() {
    return GlobalVariables.report;
  }

  public static Suspect getChosenSuspect() {
    return GlobalVariables.chosenSuspect;
  }

  /** Adds all interactables to the interactablesMap and sets their values to false. */
  public static void initialiseInteractablesMap() {
    interactablesMap.put("suspect1", false);
    interactablesMap.put("suspect2", false);
    interactablesMap.put("suspect3", false);
    interactablesMap.put("cardClue", false);
    interactablesMap.put("computerClue", false);
    interactablesMap.put("displayCaseClue", false);
  }

  /**
   * Changes the value of the input key to true, marking that the affiliated interactable has been
   * interacted with.
   *
   * @param mapKey the id of the interactable that is the key in the interactables map
   */
  public static void handleInteraction(String mapKey) {
    interactablesMap.put(mapKey, true);
  }

  /**
   * Determines if the player can switch from the game scene to the guess scene to make a guess.
   *
   * @return true if the player has met all conditions to go to the guess scene
   */
  public static boolean canGuessThief() {
    // Cannot guess if all suspects have not been interacted (player has chatted) with.
    if (!interactablesMap.get("suspect1")
        || !interactablesMap.get("suspect2")
        || !interactablesMap.get("suspect3")) {
      System.out.println(interactablesMap);
      return false;
    }

    // Can guess if at least one clue has been interacted with.
    if (interactablesMap.get("cardClue")
        || interactablesMap.get("computerClue")
        || interactablesMap.get("displayCaseClue")) {
      return true;
    }

    return false;
  }

  /**
   * Checks a pressed key to see if it activates any cheat. This method is for checking cheats that
   * can be activated in all/most main scenes.
   */
  public static void checkForCheatCode(String pressedKey) {
    if (!ENABLE_CHEATS) {
      return;
    }

    if (pressedKey.equals("F1")) {
      GlobalVariables.muteTtsCheat();
    } else if (pressedKey.equals("F2")) {
      GlobalVariables.overrideInteractablesMapCheat();
    }
  }

  /**
   * Cheat that selects adds a preset explanation to the text area in the guess scene, and toggles
   * between the 2 presets (correct and incorrect).
   *
   * @param textArea text area to add the preset explanation to
   */
  public static void togglePresetExplanationCheat(TextArea textArea) {
    alertUserOfCheat(
        "adding "
            + (presetExplanationIsCorrect ? "incorrect" : "correct")
            + " explanation to text area.");

    presetExplanationIsCorrect = !presetExplanationIsCorrect;
    textArea.setText(
        presetExplanationIsCorrect ? correctPresetExplanation : incorrectPresetExplanation);
  }

  /** Cheat that sets all values in the interactables map to the input parameter. */
  private static void overrideInteractablesMapCheat() {
    alertUserOfCheat(
        "overriding interactables map to "
            + (interactablesOverriddenTrue ? "false" : "true")
            + ".");
    interactablesOverriddenTrue = !interactablesOverriddenTrue;

    for (String interactable : interactablesMap.keySet()) {
      interactablesMap.put(interactable, interactablesOverriddenTrue);
    }
  }

  /** Cheat that toggles whether TTS is muted or not. */
  private static void muteTtsCheat() {
    alertUserOfCheat((muteTts ? "unmuting" : "muting") + " TTS.");

    muteTts = !muteTts;
    TextToSpeech.setMuteStatusOfPlayer();
  }

  /**
   * Print alert message in terminal to inform user that a cheat has been activated and what it did.
   */
  private static void alertUserOfCheat(String message) {
    System.out.println("CHEAT ACTIVATED: " + message);
  }
}