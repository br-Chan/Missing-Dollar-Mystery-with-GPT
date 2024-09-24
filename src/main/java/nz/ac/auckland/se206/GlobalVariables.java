package nz.ac.auckland.se206;

/** Context class for managing global variables of the game. */
public class GlobalVariables {

  private static Suspect chosenSuspect = Suspect.NONE;
  private static String report;

  public static void setReport(String report) {
    GlobalVariables.report = report;
  }

  public static void setChosenSuspect(Suspect suspect) {
    GlobalVariables.chosenSuspect = suspect;
  }

  public static String getReport() {
    return GlobalVariables.report;
  }

  public static Suspect getChosenSuspect() {
    return GlobalVariables.chosenSuspect;
  }

}
