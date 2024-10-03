package nz.ac.auckland.se206;

import java.io.IOException;

public abstract class AppTimerUser {

  protected AppTimer appTimer;

  public abstract void handleTimeUp() throws IOException;

}
