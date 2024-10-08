package nz.ac.auckland.se206;

import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class MugshotTransition {
  private final Node node;
  private final Line forward;
  private final Line backward;

  public MugshotTransition(Node sprite, int endX, int endY) {
    node = sprite;
    Pane parent = (Pane) sprite.getParent();

    double spriteX = sprite.getLayoutX(), spriteY = sprite.getLayoutY(),
        spriteWidth = sprite.getLayoutBounds().getWidth() / 2, spriteHeight = sprite.getLayoutBounds().getHeight() / 2;

    sprite.translateXProperty().set(spriteX);
    sprite.translateYProperty().set(spriteY);

    sprite.setLayoutX(0);
    sprite.setLayoutY(0);

    forward = new Line(spriteWidth + spriteX, spriteHeight + spriteY, spriteWidth + endX, spriteHeight + endY);
    backward = new Line(spriteWidth + endX, spriteHeight + endY, spriteWidth + spriteX, spriteHeight  + spriteY);

    forward.setOpacity(0);
    backward.setOpacity(0);

    parent.getChildren().add(forward);
    parent.getChildren().add(backward);
  }

  public void playForwards() {
    var transition = createTransition();
    transition.setPath(forward);
    transition.play();
  }


  public void playBackwards() {
    var transition = createTransition();
    transition.setPath(backward);
    transition.play();
  }


  private PathTransition createTransition() {
    var transition = new PathTransition();
    transition.setDuration(Duration.millis(200));
    transition.setNode(node);

    return transition;
  }
}
