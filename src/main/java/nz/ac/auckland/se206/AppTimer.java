package nz.ac.auckland.se206;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class AppTimer {

  public static final int GAMETIME = 10;
  public static final int GUESSTIME = 1 * 60;

  private Label timerLabel;

  private Timer timer;
  private int startingTime;
  private int timeLeft;

  public AppTimer(Label timerLabel, int startingTime) {
    if (timerLabel == null) {
      System.out.println("TimerLabel is null!");
    }

    this.timerLabel = timerLabel;
    timer = new Timer(true);
    this.startingTime = startingTime;
    timeLeft = startingTime;
  }

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
                  String digitaltimeLeft = null;
                  if (secondsLeft < 10) {
                    digitaltimeLeft = "0" + minutesLeft + ":0" + secondsLeft;
                  } else {
                    digitaltimeLeft = "0" + minutesLeft + ":" + secondsLeft;
                  }
                  timerLabel.setText(digitaltimeLeft);
                  System.out.println(digitaltimeLeft);

                  // Stop the timer if 0 is reached and handle it.
                  if (timeLeft <= 0) {
                    timer.cancel();
                    handleTimeUp();
                  }
                });
          }
        };

    timer.scheduleAtFixedRate(task, 1000, 1000);
  }

  private void handleTimeUp() {
    if (startingTime == GAMETIME) {
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS));
    } else if (startingTime == GUESSTIME) {
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
    }
  }
}
