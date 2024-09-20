package nz.ac.auckland.se206.states;

import java.io.IOException;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * The GameOver state of the game. Handles interactions after the game has ended, informing the
 * player that the game is over and no further actions can be taken.
 */
public class GameOver implements GameState {

  /**
   * Constructs a new GameOver state with the given game state context.
   *
   * @param context the context of the game state
   */
  public GameOver() {
    System.out.println("GameOver");
  }

  /**
   * Handles the event when the guess button is clicked. Informs the player that the game is over
   * and no further guesses can be made.
   *
   * @throws IOException if there is an I/O error
   */
  @Override
  public void handleGuessClick() throws IOException {
    TextToSpeech.speak("You have already guessed!");
  }
}
