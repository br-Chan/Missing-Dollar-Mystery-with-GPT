package nz.ac.auckland.se206;

import java.util.HashMap;
import java.util.Map;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Class for managing global variables of the game. */
public class GlobalVariables {

  private static Map<String, Boolean> interactablesMap = new HashMap<>();

  private static Suspect chosenSuspect = Suspect.NONE;
  private static String report;

  private static boolean muteTts = false;
  private static boolean interactablesOverriddenTrue = false;

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

  public static void checkForCheatCode(String pressedKey) {
    if (pressedKey.equals("F1")) {
      GlobalVariables.muteTtsCheat();
    } else if (pressedKey.equals("F2")) {
      GlobalVariables.overrideInteractablesMapCheat();
    }
  }

  private static void alertUserOfCheat(String message) {
    System.out.println("CHEAT ACTIVATED: " + message);
  }

  /**
   * Cheat that sets all values in the interactables map to the input parameter.
   *
   * @param override true or false
   */
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
}
