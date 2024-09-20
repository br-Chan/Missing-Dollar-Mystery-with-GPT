package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.ResultController;

public class AppTimer {

  public static final int GAMETIME = 5 * 60;
  public static final int GUESSTIME = 1 * 60;

  private Label timerLabel;

  private Timer timer;
  private int startingTime;
  private int timeLeft;

  public AppTimer(Label timerLabel, int startingTime) {
    this.timerLabel = timerLabel;
    timer = new Timer(true);
    this.startingTime = startingTime;
    timeLeft = startingTime;
  }

  /**
   * Starts the countdown from the given starting time, and automatically handles when the timer
   * runs out.
   */
  public void beginCountdown() {
    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  // Decrement the time left and update the label.
                  timeLeft--;
                  int minutesLeft = timeLeft / 60;
                  int secondsLeft = timeLeft - 60 * minutesLeft;
                  String digitaltimeLeft;
                  if (secondsLeft < 10) {
                    digitaltimeLeft = "0" + minutesLeft + ":0" + secondsLeft;
                  } else {
                    digitaltimeLeft = "0" + minutesLeft + ":" + secondsLeft;
                  }
                  timerLabel.setText(digitaltimeLeft);

                  // Stop the timer if 0 is reached and handle it.
                  if (timeLeft <= 0) {
                    timer.cancel();
                    try {
                      handleTimeUp();
                    } catch (IOException e) {
                      e.printStackTrace();
                      System.err.println("Could not find FXML to switch to when handling time up.");
                    }
                  }
                });
          }
        };

    timer.scheduleAtFixedRate(task, 1000, 1000);
  }

  /**
   * Switches the scene to either the guess scene or results scene depending on how much total time
   * the timer was counting down from.
   */
  private void handleTimeUp() throws IOException {
    if (startingTime == GAMETIME) {
      SceneManager.addUi(AppUi.GUESS, App.loadFxml("guess"));
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS));
    } else if (startingTime == GUESSTIME) {
      var loader = new FXMLLoader(AppTimer.class.getResource("/fxml/result.fxml"));
      SceneManager.addUi(AppUi.RESULT, loader.load());
      ResultController controller = loader.getController();
      // controller.setResult(false, null);
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
    }
  }

  /**
   * Cancels the timer; particularly useful when clicking a button to switch the scene while the
   * timer is still going, and therefore need to cancel the timer on its own.
   *
   * <p>Suggestion: Could possibly just call handleTimeUp() instead of this, and timer.cancel() is
   * called at the start of handleTimeUp() and nowhere else. So when you click the guess button in
   * the game scene for example, handleTimeUp() is called and it's as if the timer had run out, and
   * everything works as expected without the need for this method.
   */
  public void cancelTimer() {
    timer.cancel();
  }
}
