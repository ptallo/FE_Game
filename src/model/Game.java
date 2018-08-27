package model;

import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Map map;
    private Cursor cursor = new Cursor();

    public Game() {
        map = new Map();
    }

    public void update() {

    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        cursor.draw(gc);
    }
}
