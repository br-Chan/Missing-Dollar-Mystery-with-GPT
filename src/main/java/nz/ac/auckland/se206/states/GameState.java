package nz.ac.auckland.se206.states;

import java.io.IOException;

/**
 * Interface representing the state of the game. Defines methods to handle user interactions such as
 * clicking on a rectangle and making a guess.
 */
public interface GameState {
  /**
   * Handles the event when the guess button is clicked. TODO: potentially can reuse this method for
   * a future guess button. Or just implement that functionality in a different class.
   *
   * @throws IOException if there is an I/O error
   */
  void handleGuessClick() throws IOException;
}
