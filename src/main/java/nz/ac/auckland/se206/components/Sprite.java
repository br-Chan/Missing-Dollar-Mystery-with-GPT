package nz.ac.auckland.se206.components;

import javafx.beans.NamedArg;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite extends Pane {
    private Canvas canvas;
//     private final BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private final BufferedImage image;

    public Sprite(@NamedArg("spriteUrl") String spriteUrl) {
        super();
        // Temporary variable to store image into so I can make the image final
        canvas = new Canvas();
        this.getChildren().add(canvas);
        BufferedImage image;

        try {
            image = ImageIO.read(new URL(spriteUrl));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Failed to load sprite: " + ex.getMessage());
            image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        }

        this.image = image;

        sceneProperty().addListener((event) -> {
            if (getScene() == null) {
                return;
            }

            getScene().widthProperty().addListener((_event) -> renderImage());
            getScene().heightProperty().addListener((_event) -> renderImage());
        });
    }


    private void renderImage() {
        var widowScale = Math.min(getScene().getWidth(), getScene().getHeight()) / 128d;

        // Resize the image
        var width = (int) Math.floor(image.getWidth() * widowScale);
        var height = (int) Math.floor(image.getHeight() * widowScale);

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
}
