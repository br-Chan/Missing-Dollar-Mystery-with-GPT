package nz.ac.auckland.se206.controllers;

import java.util.HashMap;

import javafx.scene.Parent;

public class SceneManager {

  public enum AppUi {
    SUSPECT1,
    SUSPECT2,
    SUSPECT3,
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<>();

  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }
  
}
