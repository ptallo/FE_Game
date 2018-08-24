package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {

    private Map map;

    public Game() {
        map = new Map();
    }

    public void update() {

    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
    }
}
