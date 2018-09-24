package model.states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public interface StateInterface {
    void handleKeyEvent(KeyEvent event);
    void draw(GraphicsContext gc, double w, double h);
}
