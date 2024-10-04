package nz.ac.auckland.se206;

import java.io.IOException;

/**
 * An abstract class that contains the app timer and a method to handle time up. Subclasses of this
 * class are expected to be FXML controllers, and should implement the switchScene method to switch
 * to their own specified scene(s) as needed.
 */
public abstract class AppTimerUser {

  /**
   * The instance of AppTimer. Caveat: must actually initialise this in the subclass with the proper
   * input parameters.
   */
  protected AppTimer appTimer;

  /** Cancels the timer, then switches the scene. This method should not be overriden. */
  public final void handleTimeUp() {
    appTimer.cancelTimer();
    try {
      switchScene();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Could not find FXML to switch to when handling time up.");
    }
  }

  /**
   * Switches the scene; to be implemented by the controller subclass.
   *
   * @throws IOException if the scene to switch to was not found
   */
  public abstract void switchScene() throws IOException;
}
