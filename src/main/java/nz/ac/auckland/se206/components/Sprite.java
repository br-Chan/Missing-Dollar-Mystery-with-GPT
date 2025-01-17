package nz.ac.auckland.se206.components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javafx.beans.NamedArg;
import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;

/** This sprite class turns images into custom sprite elements. */
public class Sprite extends Pane {
  private BufferedImage image;
  private Canvas canvas;

  private SimpleStringProperty spriteUrl;
  private SimpleDoubleProperty scale;

  /**
   * Sets up the sprite component using an image.
   *
   * @param spriteUrl image url for the sprite component
   * @param scale scaling factor for image size
   */
  public Sprite(@NamedArg("spriteUrl") String spriteUrl, @NamedArg("scale") double scale) {
    super();
    // Temporary variable to store image into so I can make the image final
    canvas = new Canvas();
    this.getChildren().add(canvas);

    this.spriteUrl = new SimpleStringProperty(spriteUrl);
    this.scale = new SimpleDoubleProperty(scale);

    scaleProperty().addListener((event) -> renderImage());
    spriteUrlProperty().addListener((event) -> loadImage());

    loadImage();
  }

  public SimpleStringProperty spriteUrlProperty() {
    return spriteUrl;
  }

  public SimpleDoubleProperty scaleProperty() {
    return scale;
  }

  private void loadImage() {
    try {
      image = ImageIO.read(new URL(spriteUrl.get())); // Initialises sprite url for the image
    } catch (IOException ex) {
      image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
    }

    renderImage(); // Outputs the image render
  }

  private void renderImage() {
    // Resize the image
    var width = (int) Math.floor(image.getWidth() * scale.get());
    var height = (int) Math.floor(image.getHeight() * scale.get());

    setWidth(width);
    setHeight(height);

    // Set the canvas size
    canvas.setWidth(width);
    canvas.setHeight(height);

    // Clear the canvas
    canvas.getGraphicsContext2D().clearRect(0, 0, width, height);

    // Draw the image
    if (width == 0 || height == 0) {
      return;
    }

    // Resize the image
    var imageWidth = image.getWidth();
    var imageHeight = image.getHeight();

    var scaleX = (double) imageWidth / (double) width;
    var scaleY = (double) imageHeight / (double) height;

    var outputImage = new WritableImage(width, height);
    var pixelWriter = outputImage.getPixelWriter();

    // Draw the image
    for (var x = 0; x < width; x++) {
      for (var y = 0; y < height; y++) {
        var textureX = (int) Math.floor(x * scaleX);
        var textureY = (int) Math.floor(y * scaleY);

        var rgba = image.getRGB(textureX, textureY);
        pixelWriter.setArgb(x, y, rgba);
      }
    }

    // Draw the image
    canvas.getGraphicsContext2D().drawImage(outputImage, 0, 0);
  }

  public void setX(double x) {
    translateXProperty().set(x);
  }

  public void setY(double y) {
    translateYProperty().set(y);
  }
}
