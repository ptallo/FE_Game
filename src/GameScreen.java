import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameScreen {

    private Canvas canvas;
    private GraphicsContext gc;

    public GameScreen(Game game, Group root) {
        canvas = new Canvas(250,250);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.draw(gc);
            }
        }.start();
    }
}
