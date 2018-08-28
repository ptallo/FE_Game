package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.cursor.Cursor;
import model.cursor.SelectionIndicator;
import model.map.Map;
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
        units.add(UnitEnum.SPEARMAN.getUnitInstance(1, 3));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(1, 4));
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

    private void handleEnterKey() {
        //handle unit selection
        Unit selectedUnit = null;
        for (Unit unit : units) {
            if (cursor.getSelectionPoint().equals(unit.getPoint())) {
                selectedUnit = unit;
            }
        }

        //handle unit movement
        if (this.selectedUnit != null && selectedUnit == null) {
            if (cursor.getSelectionPoint().inCollection(this.selectedUnit.getMovablePoints(map, units))) {
                this.selectedUnit.setPoint(cursor.getSelectionPoint().clone());
            }
        }
        this.selectedUnit = selectedUnit;
    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        if (selectedUnit != null) {
            selectedUnit.drawMovableArea(gc, map, units);
            selectionIndicator.getRenderComponent().draw(gc, selectedUnit.getPoint());
        }

        for (Unit unit : units) {
            unit.getRenderComponent().draw(gc, unit.getPoint());
        }

        cursor.draw(gc);
    }
}
