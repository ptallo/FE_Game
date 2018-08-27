package components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Map;
import model.Point;

import java.io.File;

public class RenderComponent {
    private long lastAnimationTime;
    private long animationDuration;

    private int frameCount;
    private int currentFrame;
    private double frameWidth;
    private double frameHeight;

    private Image selectionImage;

    public RenderComponent(String path, double frameWidth, double frameHeight, long animationDuration) {
        selectionImage = new Image(new File("resources/" + path).toURI().toString());
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        currentFrame = 0;
        frameCount = (int) Math.floor(selectionImage.getWidth() / frameWidth);
        lastAnimationTime = System.currentTimeMillis();
        this.animationDuration = animationDuration;
    }

    public void draw(GraphicsContext gc, Point point){
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - lastAnimationTime) > animationDuration) {
            if (currentFrame + 1 >= frameCount) {
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
                point.getX(),
                point.getY(),
                Map.Tile_Width,
                Map.Tile_Height
        );
    }
}
