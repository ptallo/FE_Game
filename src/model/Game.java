package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.cursor.Cursor;
import model.cursor.SelectionIndicator;
import model.map.Map;
import model.map.MapTile;
import model.unit.Unit;
import model.unit.UnitEnum;

import java.util.ArrayList;

public class Game {

    private Map map;
    private Cursor cursor;
    private SelectionIndicator selectionIndicator;

    private ArrayList<Unit> units;
    private Unit selectedUnit;

    public Game() {
        map = new Map();
        cursor = new Cursor();
        selectionIndicator = new SelectionIndicator();
        units = new ArrayList<>();
        units.add(UnitEnum.SPEARMAN.getUnitInstance(2, 3));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(4, 3));
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
            handleEnterKey();
        }
    }

    public void handleEnterKey() {
        //handle unit selection
        Unit selectedUnit = null;
        for (Unit unit : units) {
            if (cursor.getSelectionPoint().equals(unit.getPoint())) {
                selectedUnit = unit;
            }
        }

        //handle unit movement
        if (this.selectedUnit != null && selectedUnit == null) {
            Point movePoint = new Point(cursor.getSelectionPoint().getX(), cursor.getSelectionPoint().getY());
            if (map.getTileAtPoint(movePoint).getPassable()){
                this.selectedUnit.setPoint(movePoint);
            }
        }
        this.selectedUnit = selectedUnit;
    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        for (Unit unit : units) {
            unit.getRenderComponent().draw(gc, unit.getPoint());
        }

        if (selectedUnit != null) {
            selectedUnit.drawMovableArea(gc, map);
            selectionIndicator.getRenderComponent().draw(gc, selectedUnit.getPoint());
        }

        cursor.draw(gc);
    }
}
