package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

/**
 * This is the entry point of the JavaFX application. This class initializes and runs the JavaFX
 * application.
 */
public class App extends Application {

  private static Scene scene;
  private static Stage currentStage;
  private static Stage oldStage;

  /**
   * NOTE TO DEVELOPERS: Edit this variable to change which scene is shown first when loading the
   * game during testing. firstAppUi should be Appui.MENU upon merging with main (ideally) and upon
   * release (must).
   */
  private static final AppUi firstAppUi = AppUi.MENU;

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    launch();
  }

  public static Scene getScene() {
    return scene;
  }

  /**
   * Sets the root of the scene to the specified FXML file.
   *
   * @param fxml the name of the FXML file (without extension)
   * @throws IOException if the FXML file is not found
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * Sets the root of the scene to the value of the specified AppUi in the scene manager.
   *
   * @param appUi the enum key for the hash map in the scene manager.
   * @throws IOException if the key is not found.
   */
  public static void setRoot(AppUi appUi) throws IOException {
    scene.setRoot(SceneManager.getUiRoot(appUi));
  }

  /**
   * Loads the FXML file and returns the associated node. The method expects that the file is
   * located in "src/main/resources/fxml".
   *
   * @param fxml the name of the FXML file (without extension)
   * @return the root node of the FXML file
   * @throws IOException if the FXML file is not found
   */
  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /** Restarts the app by creating a new app, then closing the current one. */
  public static void restart() {
    try {
      oldStage = currentStage; // Initialises the old stage
      new App().start(new Stage());
      oldStage.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Pre-adds FXML UI roots to the scene manager. Note that not all scenes are added in this method.
   * For example, the game scene is loaded and added to the scene manager in the MenuController when
   * the start button is clicked.
   *
   * <p>Some scenes here can be loaded elsewhere if needed.
   *
   * @throws IOException if a FXML file is not found
   */
  private void preAddUi() throws IOException {
    SceneManager.addUi(AppUi.CRIME_SCENE, loadFxml("crimeScene"));
    SceneManager.addUi(AppUi.SUSPECT1, loadFxml("suspect1"));
    SceneManager.addUi(AppUi.SUSPECT2, loadFxml("suspect2"));
    SceneManager.addUi(AppUi.SUSPECT3, loadFxml("suspect3"));
    SceneManager.addUi(AppUi.STORY, loadFxml("story"));
    SceneManager.addUi(AppUi.MENU, loadFxml("menu"));
  }

  /**
   * Sets the scene that is shown first to the UI root associated with the input AppUi value, then
   * displays the stage to the user.
   */
  private void showFirstScene(final Stage stage, AppUi firstAppUi) {
    Parent root = SceneManager.getUiRoot(firstAppUi);
    scene = new Scene(root);
    currentStage = stage;
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> handleWindowClose(event));
  }

  /**
   * This method is invoked when the application starts. It loads and shows the first scene.
   *
   * @param stage the primary stage of the application
   * @throws IOException if the FXML file is not found
   */
  @Override
  public void start(final Stage stage) {
    try {
      System.out.println(
          Font.loadFont(App.class.getResourceAsStream("/fonts/Caveat-Bold.ttf"), 13));
      System.out.println(
          Font.loadFont(
              App.class.getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 12));

      // Adds all ui to scene manager
      preAddUi();
      // Initialise all conditions interacted to be false
      GlobalVariables.initialiseInteractablesMap();
      GlobalVariables.initialiseCluesMap();
    } catch (IOException e) {
      System.err.println("Could not find FXML file in scene manager.");
      e.printStackTrace();
    }
    // Shows the first scene that we set (start)
    showFirstScene(stage, firstAppUi);
    // Sets the stage
    stage
        .widthProperty()
        .addListener(
            (a) -> {
              System.out.println("Width: " + stage.getWidth());
            });
    stage
        .heightProperty()
        .addListener(
            (a) -> {
              System.out.println("Height: " + stage.getHeight());
            });
  }

  private void handleWindowClose(WindowEvent event) {
    FreeTextToSpeech.deallocateSynthesizer();
  }
}
