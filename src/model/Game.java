package model;

import components.CombatSystem;
import components.physics.PhysicsSystem;
import components.render.RenderSystem;
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

    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private Map map = new Map();
    private Cursor cursor = new Cursor();
    private SelectionIndicator selectionIndicator = new SelectionIndicator();

    private ArrayList<Unit> units;
    private Unit selectedUnit;

    public Game() {
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
            if (cursor.getSelectionPoint().equals(unit.getPhysicsComponent().getPoint())) {
                selectedUnit = unit;
            }
        }

        //handle unit movement
        if (this.selectedUnit != null) {
            if (selectedUnit == null) {
                physicsSystem.setPoint(this.selectedUnit.getPhysicsComponent(), cursor.getSelectionPoint(), map, units);
            } else {
                combatSystem.completeCombat(this.selectedUnit, selectedUnit, units);
                selectedUnit = null;
            }
        }
        this.selectedUnit = selectedUnit;
    }

    public void draw(GraphicsContext gc) {
        map.draw(gc);
        if (selectedUnit != null) {
            physicsSystem.drawMovableArea(selectedUnit.getPhysicsComponent(), gc, map, units);
            renderSystem.draw(selectionIndicator.getRenderComponent(), gc, selectedUnit.getPhysicsComponent().getPoint());
        }

        for (Unit unit : units) {
            renderSystem.draw(unit.getRenderComponent(), gc, unit.getPhysicsComponent().getPoint());
        }

        renderSystem.draw(cursor.getRenderComponent(), gc, cursor.getSelectionPoint());
    }
}
