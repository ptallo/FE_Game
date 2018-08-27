package model;

import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Map map;
    private Cursor cursor;

    public Game() {
        map = new Map();
        cursor = new Cursor();
    }

    public void update() {

    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        cursor.draw(gc);
    }
}
