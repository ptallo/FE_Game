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
import model.unit.Unit;
import model.unit.UnitEnum;
import view.hover.MapTileHoverInfo;
import view.hover.UnitHoverInfo;

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
    private MapTileHoverInfo mapTileHoverInfo;
    private UnitHoverInfo unitHoverInfo;

    private ArrayList<Unit> units;
    private Unit selectedUnit;
    private Unit hoveredUnit;

    private ArrayList<Player> players;
    private Player user;

    public Game() {
        players = new ArrayList<>();
        user = new Player();
        players.add(user);
        players.add(new Player());

        mapTileHoverInfo = new MapTileHoverInfo(cursor, true, false);
        unitHoverInfo = new UnitHoverInfo(cursor, true, true);
        units = new ArrayList<>();
        units.add(UnitEnum.SPEARMAN.getUnitInstance(14, 5));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(1, 4));
    }

    public void handleMouseEvent(MouseEvent event) {
        double xCoord = Math.floor((event.getX() - cursor.getxTransform()) / Map.Tile_Height);
        double yCoord = Math.floor((event.getY() - cursor.getyTransform()) / Map.Tile_Height);
        Point point = new Point((int) xCoord, (int) yCoord);
        cursor.setPoint(point, map);
        handleMouseMoved();
        handleEnterKey();
    }

    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            cursor.movePoint(0, -1, map);
            handleMouseMoved();
        } else if (event.getCode() == KeyCode.DOWN) {
            cursor.movePoint(0, 1, map);
            handleMouseMoved();
        } else if (event.getCode() == KeyCode.LEFT) {
            cursor.movePoint(-1, 0, map);
            handleMouseMoved();
        } else if (event.getCode() == KeyCode.RIGHT) {
            cursor.movePoint(1, 0, map);
            handleMouseMoved();
        } else if (event.getCode() == KeyCode.ENTER) {
            handleEnterKey();
        }
    }

    private void handleMouseMoved() {
        Unit hoveredUnit = null;
        for (Unit unit : units) {
            if (cursor.getSelectionPoint().equals(unit.getPhysicsComponent().getPoint())) {
                hoveredUnit = unit;
            }
        }
        this.hoveredUnit = hoveredUnit;
    }

    private void handleEnterKey() {
        if (selectedUnit != null && hoveredUnit != selectedUnit) {
            if (hoveredUnit == null) {
                List<PhysicsComponent> componentList = units.stream().map(Unit::getPhysicsComponent).collect(Collectors.toList());
                physicsSystem.setPoint(selectedUnit.getPhysicsComponent(), cursor.getSelectionPoint(), map, componentList);
            } else if (selectedUnit.getCombatComponent() != null && hoveredUnit.getCombatComponent() != null) {
                combatSystem.completeCombat(selectedUnit, hoveredUnit, units);
                selectedUnit = null;
            }
        }
        this.selectedUnit = hoveredUnit;
    }

    public void draw(GraphicsContext gc, double w, double h) {
        cursor.handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        map.draw(gc);
        if (selectedUnit != null) {
            if (selectedUnit.getPhysicsComponent() != null) {
                List<PhysicsComponent> componentList = units.stream().map(Unit::getPhysicsComponent).collect(Collectors.toList());
                physicsSystem.drawMovableArea(selectedUnit.getPhysicsComponent(), gc, map, componentList);
                combatSystem.drawAttackableArea(gc, selectedUnit, map, units);
                renderSystem.draw(selectionIndicator.getRenderComponent(), gc, selectedUnit.getPhysicsComponent().getPoint());
            }
        }

        for (Unit unit : units) {
            if (unit.getRenderComponent() != null) {
                renderSystem.draw(unit.getRenderComponent(), gc, unit.getPhysicsComponent().getPoint());
            }
        }

        renderSystem.draw(cursor.getRenderComponent(), gc, cursor.getSelectionPoint());
        mapTileHoverInfo.showInfo(w, h, gc, map.getTileAtPoint(cursor.getSelectionPoint()));
        if (this.selectedUnit != null) {
            unitHoverInfo.showInfo(w, h, gc, selectedUnit);
        } else if (this.hoveredUnit != null) {
            unitHoverInfo.showInfo(w, h, gc, hoveredUnit);

        }
    }

}
