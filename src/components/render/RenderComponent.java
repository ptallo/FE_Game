package components.render;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import util.TeamColorManager;

import java.io.File;
import java.util.ArrayList;

@Getter
@Setter
public class RenderComponent {
    private long lastAnimationTime;
    private long animationDuration;

    private int frameCount;
    private int currentFrame;
    private double frameWidth;
    private double frameHeight;

    private Image selectionImage;

    public RenderComponent(String path, double frameWidth, double frameHeight, long animationDuration, int ownerIndex) {
        selectionImage = new Image(new File("resources/" + path).toURI().toString());
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        currentFrame = 0;
        frameCount = (int) Math.floor(selectionImage.getWidth() / frameWidth);
        lastAnimationTime = System.currentTimeMillis();
        this.animationDuration = animationDuration;

        if (ownerIndex >= 0) {
            TeamColorManager colorManager = new TeamColorManager();
            selectionImage = colorManager.convertToTeam(selectionImage, ownerIndex);
        }
    }

    public RenderComponent(String path, double frameWidth, double frameHeight, long animationDuration) {
        this(path, frameWidth, frameHeight, animationDuration, -1);
    }
}
