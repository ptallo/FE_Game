package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class Cursor {

    private Point selectionPoint = new Point(0, 0);

    long lastAnimationTime;
    long animationDuration;

    private int frameCount;
    private int currentFrame;
    private double frameWidth;
    private double frameHeight;

    private Image selectionImage;

    public Cursor() {
        selectionImage = new Image(new File("resources/selection_cursor.png").toURI().toString());
        frameHeight = 32;
        frameWidth = 32;
        currentFrame = 0;
        frameCount = (int) Math.floor(selectionImage.getWidth() / frameWidth);
        lastAnimationTime = System.currentTimeMillis();
        animationDuration = 500;
    }

    public void draw(GraphicsContext gc){
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - lastAnimationTime) > animationDuration) {
            if (currentFrame + 1 > frameCount) {
                currentFrame = 0;
            } else {
                currentFrame++;
            }
            lastAnimationTime = currentTime;
        }

        gc.drawImage(
                selectionImage,
                currentFrame * frameWidth,
                0,
                frameWidth,
                frameHeight,
                selectionPoint.getX(),
                selectionPoint.getY(),
                Map.Tile_Width,
                Map.Tile_Height
        );
    }
}
