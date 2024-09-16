package nz.ac.auckland.se206;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class AppTimer {

  public static final int GAMETIME = 5 * 60;
  public static final int GUESSTIME = 1 * 60;

  private Label timerLabel;

  private Timer timer;
  private int timeToElapse;

  public AppTimer(Label timerLabel, int timeToElapse) {
    if (timerLabel == null) {
      System.out.println("TimerLabel is null!");
    }

    this.timerLabel = timerLabel;
    timer = new Timer();
    this.timeToElapse = timeToElapse;
  }

  public void beginCountdown() {
    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  // Decrement the time left and update the label.
                  timeToElapse--;
                  int minutesLeft = timeToElapse / 60;
                  int secondsLeft = timeToElapse - 60 * minutesLeft;
                  String digitaltimeToElapse = null;
                  if (secondsLeft < 10) {
                    digitaltimeToElapse = "0" + minutesLeft + ":0" + secondsLeft;
                  } else {
                    digitaltimeToElapse = "0" + minutesLeft + ":" + secondsLeft;
                  }
                  timerLabel.setText(digitaltimeToElapse);
                  System.out.println(digitaltimeToElapse);

                  // Stop the timer if 0 is reached and handle it.
                  if (timeToElapse <= 0) {
                    timer.cancel();
                    handleTimeUp();
                  }
                });
          }
        };

    timer.scheduleAtFixedRate(task, 1000, 1000);
  }

  private void handleTimeUp() {
    if (timeToElapse == GAMETIME) {
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.GUESS));
    } else if (timeToElapse == GUESSTIME) {
      App.getScene().setRoot(SceneManager.getUiRoot(AppUi.RESULT));
    }
  }
}
