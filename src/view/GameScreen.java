package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Level;

public class GameScreen {

    private Canvas canvas;
    private GraphicsContext gc;

    private Level level;

    public GameScreen(Level level, Group root, double width, double height) {
        this.level = level;
        initCanvas(width, height);
        root.getChildren().add(canvas);
    }

    private void initCanvas(double w, double h) {
        canvas = new Canvas(w, h);
        gc = canvas.getGraphicsContext2D();
    }

    public void initDrawLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (level != null) {
                    level.draw(gc, canvas.getWidth(), canvas.getHeight());
                }
            }
        }.start();
    }
}
