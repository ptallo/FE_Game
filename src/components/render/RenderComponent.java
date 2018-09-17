package components.render;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.TeamColorManager;

import java.io.File;
import java.util.ArrayList;

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

        TeamColorManager colorManager = new TeamColorManager();
        selectionImage = colorManager.convertToTeam(selectionImage, 1);
    }

    public long getLastAnimationTime() {
        return lastAnimationTime;
    }

    public long getAnimationDuration() {
        return animationDuration;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public double getFrameWidth() {
        return frameWidth;
    }

    public double getFrameHeight() {
        return frameHeight;
    }

    public Image getSelectionImage() {
        return selectionImage;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}
