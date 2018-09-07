package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Game;

public class GameScreen {

    private Canvas canvas;
    private GraphicsContext gc;

    private Game game;

    public GameScreen(Game game, Group root, double width, double height) {
        this.game = game;
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
                if (game != null) {
                    game.draw(gc, canvas.getWidth(), canvas.getHeight());
                }
            }
        }.start();
    }
}
