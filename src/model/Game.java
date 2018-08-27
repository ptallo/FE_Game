package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.map.Map;
import model.unit.Unit;
import model.unit.UnitEnum;

import java.util.ArrayList;

public class Game {

    private Map map;
    private Cursor cursor;
    private ArrayList<Unit> units;

    public Game() {
        map = new Map();
        cursor = new Cursor();
        units = new ArrayList<>();
        units.add(UnitEnum.SPEARMAN.getUnitInstance(2, 3));
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
            selectUnit();
        }
    }

    public void selectUnit() {
        for (Unit unit : units) {
            if (unit.getPoint().getX().equals(cursor.getSelectionPoint().getX()) && unit.getPoint().getY().equals(cursor.getSelectionPoint().getY())) {
                System.out.println("selected");
            }
        }
    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        for (Unit unit : units) {
            unit.getRenderComponent().draw(gc, unit.getPoint());
        }
        cursor.draw(gc);
    }
}
