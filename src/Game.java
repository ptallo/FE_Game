import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {
    public void update() {

    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeRect(10, 10, 50, 50);
    }
}
