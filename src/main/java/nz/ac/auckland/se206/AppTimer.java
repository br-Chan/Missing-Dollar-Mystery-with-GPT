package nz.ac.auckland.se206;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * A timer to be used by certain FXML controllers (AppTimerUser's) to start and manage counting down
 * from a given number in seconds. Once the time is up, it then gets the app timer user to handle
 * that time up (usually switching to a different scene).
 */
public class AppTimer {

  public static final int GAMETIME = 5 * 60;
  public static final int GUESSTIME = 1 * 60;

  private AppTimerUser appTimerUser;

  private Label timerLabel;

  private Timer timer;
  private int timeLeft;

  /**
   * Initialises the app timer instance, and starting the countdown.
   *
   * @param appTimerUser the user of the timer
   * @param timerLabel label to show and update time
   * @param startingTime the starting time
   */
  public AppTimer(AppTimerUser appTimerUser, Label timerLabel, int startingTime) {
    this.appTimerUser = appTimerUser;
    this.timerLabel = timerLabel;
    timer = new Timer(true);
    timeLeft = startingTime;
  }

  /**
   * Starts the countdown from the given starting time, and calls the app timer user's 'handling
   * method' when the timer runs out.
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

                  // Stop the timer if 0 is reached and handle time up.
                  if (timeLeft <= 0) {
                    timer.cancel();
                    appTimerUser.handleTimeUp();
                  }
                });
          }
        };

    timer.scheduleAtFixedRate(task, 1000, 1000);
  }

  /** Cancels the Timer object in this class. */
  public void cancelTimer() {
    timer.cancel();
  }
}
