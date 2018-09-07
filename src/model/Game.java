package model;

import components.combat.CombatSystem;
import components.physics.PhysicsComponent;
import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.cursor.Cursor;
import model.cursor.SelectionIndicator;
import model.map.Map;
import model.map.MapTile;
import model.unit.UnitEnum;
import view.hover.MapTileHoverInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private Map map = new Map();
    private Cursor cursor = new Cursor();
    private SelectionIndicator selectionIndicator = new SelectionIndicator();
    private MapTileHoverInfo mapTileHoverInfo = new MapTileHoverInfo();

    private ArrayList<ObjectInterface> units;
    private ObjectInterface selectedUnit;

    public Game() {
        units = new ArrayList<>();
        units.add(UnitEnum.SPEARMAN.getUnitInstance(14, 5));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(1, 4));
    }

    public void handleMouseEvent(MouseEvent event) {
        double xCoord = Math.floor((event.getX() - cursor.getxTransform()) / Map.Tile_Height);
        double yCoord = Math.floor((event.getY() - cursor.getyTransform()) / Map.Tile_Height);
        Point point = new Point((int) xCoord, (int) yCoord);
        cursor.setPoint(point, map);
        handleEnterKey();
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
        ObjectInterface selectedUnit = null;
        for (ObjectInterface unit : units) {
            if (cursor.getSelectionPoint().equals(unit.getPhysicsComponent().getPoint())) {
                selectedUnit = unit;
            }
        }

        if (this.selectedUnit != null && selectedUnit != this.selectedUnit) {
            if (selectedUnit == null) {
                List<PhysicsComponent> componentList = units.stream().map(ObjectInterface::getPhysicsComponent).collect(Collectors.toList());
                physicsSystem.setPoint(this.selectedUnit.getPhysicsComponent(), cursor.getSelectionPoint(), map, componentList);
            } else {
                combatSystem.completeCombat(this.selectedUnit, selectedUnit, units);
                selectedUnit = null;
            }
        }
        this.selectedUnit = selectedUnit;
    }

    public void draw(GraphicsContext gc, double w, double h) {
        cursor.handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        map.draw(gc);
        if (selectedUnit != null) {
            if (selectedUnit.getPhysicsComponent() != null) {
                List<PhysicsComponent> componentList = units.stream().map(ObjectInterface::getPhysicsComponent).collect(Collectors.toList());
                physicsSystem.drawMovableArea(selectedUnit.getPhysicsComponent(), gc, map, componentList);
                renderSystem.draw(selectionIndicator.getRenderComponent(), gc, selectedUnit.getPhysicsComponent().getPoint());
            }
        }

        for (ObjectInterface unit : units) {
            if (unit.getRenderComponent() != null) {
                renderSystem.draw(unit.getRenderComponent(), gc, unit.getPhysicsComponent().getPoint());
            }
        }

        renderSystem.draw(cursor.getRenderComponent(), gc, cursor.getSelectionPoint());
        mapTileHoverInfo.showInfo(10, 10, gc, map.getTileAtPoint(cursor.getSelectionPoint()));
    }

}
