package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.map.Map;

public class Game {

    private Map map;
    private Cursor cursor;

    public Game() {
        map = new Map();
        cursor = new Cursor();
    }

    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP){
            cursor.movePoint(0, -1, map);
        } else if (event.getCode() == KeyCode.DOWN) {
            cursor.movePoint(0, 1, map);
        } else if (event.getCode() == KeyCode.LEFT) {
            cursor.movePoint(-1, 0, map);
        } else if (event.getCode() == KeyCode.RIGHT) {
            cursor.movePoint(1, 0, map);
        } else if (event.getCode() == KeyCode.ENTER) {

        }
    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        cursor.draw(gc);
    }
}
