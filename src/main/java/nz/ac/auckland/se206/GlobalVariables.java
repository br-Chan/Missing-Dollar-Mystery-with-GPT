package nz.ac.auckland.se206;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Class for managing and performing operations on global variables of the game. */
public class GlobalVariables {

  private static Map<String, Boolean> interactablesMap = new HashMap<>();
  private static Map<String, Boolean> cluesMap = new HashMap<>();

  private static boolean cardProfilePicClean = false; // When napkin has cleaned dirt
  private static boolean cardDetailsBottomClean = false; // When brush has cleaned debris
  private static boolean cardDetailsTopClean = false; // When rubber has cleaned pencil

  private static Suspect chosenSuspect = Suspect.NONE;
  private static String report;

  private static String  gameOverReason = null;

  // TODO: Set this boolean to false before final release, and turn on for client presentation.
  public static final boolean ENABLE_CHEATS = true;

  private static boolean muteTts = false;
  private static boolean interactablesOverriddenTrue = false;
  private static boolean presetExplanationIsCorrect = false;

  private static String correctPresetExplanation =
      "Louie was last to leave, meaning he could have stolen the dollar after the other 2 had left."
          + " His company card was found at the crime scene, which he could have left behind"
          + " accidentally when he stole the dollar. The drinks display case is missing 1 Berry"
          + " Burst can, and Berry Burst is Louie's favourite drink, so he could have stolen it to"
          + " replace the one that he had spilled.";
  private static String incorrectPresetExplanation =
      "Louie is the thief because he seemed like it. I found his card on the ground, not sure what"
          + " that means.";

  public static void setCardProfilePicClean(boolean cardProfilePicClean) {
    GlobalVariables.cardProfilePicClean = cardProfilePicClean;
  }

  public static void setCardDetailsBottomClean(boolean cardDetailsBottomClean) {
    GlobalVariables.cardDetailsBottomClean = cardDetailsBottomClean;
  }

  public static void setCardDetailsTopClean(boolean cardDetailsTopClean) {
    GlobalVariables.cardDetailsTopClean = cardDetailsTopClean;
  }

  public static void setReport(String report) {
    GlobalVariables.report = report;
  }

  public static void setChosenSuspect(Suspect suspect) {
    GlobalVariables.chosenSuspect = suspect;
  }

  public static void setGameOverReason(String gameOverReason) {
    GlobalVariables.gameOverReason = gameOverReason;
  }

  public static boolean isMuteTts() {
    return muteTts;
  }

  public static Map<String, Boolean> getInteractablesMap() {
    return interactablesMap;
  }

  public static Map<String, Boolean> getCluesMap() {
    return cluesMap;
  }

  public static boolean isCardProfilePicClean() {
    return cardProfilePicClean;
  }

  public static boolean isCardDetailsBottomClean() {
    return cardDetailsBottomClean;
  }

  public static boolean isCardDetailsTopClean() {
    return cardDetailsTopClean;
  }

  public static String getReport() {
    return GlobalVariables.report;
  }

  public static Suspect getChosenSuspect() {
    return GlobalVariables.chosenSuspect;
  }

  public static String getGameOverReason() {
    return GlobalVariables.gameOverReason;
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

  /** Adds all clues to the cluesMap and sets their values to false. */
  public static void initialiseCluesMap() {
    cluesMap.put("cardClue", false);
    cluesMap.put("computerClue", false);
    cluesMap.put("displayCaseClue", false);
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
   * Changes the value of the input key to true regarding clues selection.
   *
   * @param mapKey the id of the clue that is the key in the interactables map
   */
  public static void clueClicked(String mapKey) {
    cluesMap.put(mapKey, true);
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
    // Outputs cheat message in console
    alertUserOfCheat(
        "adding "
            + (presetExplanationIsCorrect ? "incorrect" : "correct")
            + " explanation to text area.");
    // Toggle cheat explanations
    presetExplanationIsCorrect = !presetExplanationIsCorrect;
    // Update explanation text box
    textArea.setText(
        presetExplanationIsCorrect ? correctPresetExplanation : incorrectPresetExplanation);
  }

  /** Cheat that sets all values in the interactables map to the input parameter. */
  private static void overrideInteractablesMapCheat() {
    // Outputs cheat message in console
    alertUserOfCheat(
        "overriding interactables map to "
            + (interactablesOverriddenTrue ? "false" : "true")
            + ".");
    interactablesOverriddenTrue = !interactablesOverriddenTrue; // Toggle conditions for guessing

    // Sets the interactables interacted true/false
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
