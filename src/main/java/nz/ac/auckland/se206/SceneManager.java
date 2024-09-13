package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum AppUi {
    MENU,
    GAME,
    CRIME_SCENE,
    SUSPECT1,
    SUSPECT2,
    SUSPECT3,
    GUESS,
    RESULT,
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<>();

  /**
   * Stores root to the given UI in the scene map hashmap, and associate them to the given enum
   * value.
   *
   * @param appUi enum value to associate to the UI root.
   * @param uiRoot UI root that the enum value associates to.
   */
  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  /**
   * Returns the root of the UI associated with the given AppUi enum value.
   *
   * @param appUi enum value to search for.
   * @return root of the UI that the enum value associates with.
   */
  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }
}
