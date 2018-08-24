import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameScreen {

    private Canvas canvas;
    private GraphicsContext gc;

    private Game game;

    public GameScreen(Game game, Group root) {
        this.game = game;
        initCanvas();
        root.getChildren().add(canvas);
    }

    private void initCanvas() {
        canvas = new Canvas(600, 600);
        gc = canvas.getGraphicsContext2D();
    }

    public void initDrawLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game != null) {
                    game.draw(gc);
                }
            }
        }.start();
    }
}
