package nz.ac.auckland.se206;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/** A class that handles the transition of a sprite from one position to another. */
public class MugshotTransition {
  /** The sprite object that is being transitioned. */
  private final Node node;

  /** The line that the sprite will follow when transitioning to the final position. */
  private final Line forward;

  /** The line that the sprite will follow when transitioning back to the original position. */
  private final Line backward;

  /**
   * Creates a new MugshotTransition object.
   *
   * @param sprite The sprite object that is being transitioned.
   * @param endX The x-coordinate of the final position.
   * @param endY The y-coordinate of the final position.
   */
  public MugshotTransition(Node sprite, int endX, int endY) {
    // Set the sprite object
    node = sprite;
    // Get the parent of the sprite object
    Pane parent = (Pane) sprite.getParent();

    // Get the sprite object's position and dimensions
    double spriteX = sprite.getLayoutX(),
        spriteY = sprite.getLayoutY(),
        spriteWidth = sprite.getLayoutBounds().getWidth() / 2,
        spriteHeight = sprite.getLayoutBounds().getHeight() / 2;

    // Set the sprite object's position
    sprite.translateXProperty().set(spriteX);
    sprite.translateYProperty().set(spriteY);

    // Create the forward and backward lines
    sprite.setLayoutX(0);
    sprite.setLayoutY(0);

    // Create the forward and backward lines
    forward =
        new Line(
            spriteWidth + spriteX, spriteHeight + spriteY, spriteWidth + endX, spriteHeight + endY);
    backward =
        new Line(
            spriteWidth + endX, spriteHeight + endY, spriteWidth + spriteX, spriteHeight + spriteY);

    // Set the opacity of the lines to 0
    forward.setOpacity(0);
    backward.setOpacity(0);

    // Add the lines to the parent
    parent.getChildren().add(forward);
    parent.getChildren().add(backward);
  }

  /** Plays the transition from the original position to the final position. */
  public void playForwards() {
    var transition = createTransition();
    transition.setPath(forward);
    transition.play();
  }

  /** Plays the transition from the final position to the original position. */
  public void playBackwards() {
    //  Create a new PathTransition object
    var transition = createTransition();
    transition.setPath(backward);
    transition.play();
  }

  /** Creates a new PathTransition object. */
  private PathTransition createTransition() {
    // Create a new PathTransition object
    var transition = new PathTransition();
    transition.setDuration(Duration.millis(200));
    transition.setNode(node);

    return transition;
  }
}
