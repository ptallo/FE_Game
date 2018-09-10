package model;

import components.combat.CombatSystem;
import components.physics.PhysicsComponent;
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
import view.hover.InfoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private Map map = new Map();
    private Cursor cursor = new Cursor();
    private SelectionIndicator selectionIndicator = new SelectionIndicator();
    private InfoItem playerTurnInfoItem = new InfoItem();

    private ArrayList<Unit> units;
    private Unit selectedUnit;
    private Unit hoveredUnit;

    private ArrayList<Player> players;
    private Player currentPlayer;

    public Game() {
        players = new ArrayList<>();
        currentPlayer = new Player();
        players.add(currentPlayer);
        players.add(new Player());

        units = new ArrayList<>();
        units.add(UnitEnum.SPEARMAN.getUnitInstance(currentPlayer, 14, 5));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(players.get(1), 1, 4));
    }

    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            cursor.movePoint(0, -1, map);
            handleCursorMoved();
        } else if (event.getCode() == KeyCode.DOWN) {
            cursor.movePoint(0, 1, map);
            handleCursorMoved();
        } else if (event.getCode() == KeyCode.LEFT) {
            cursor.movePoint(-1, 0, map);
            handleCursorMoved();
        } else if (event.getCode() == KeyCode.RIGHT) {
            cursor.movePoint(1, 0, map);
            handleCursorMoved();
        } else if (event.getCode() == KeyCode.ENTER) {
            handleEnterKey();
        }
    }

    private void handleCursorMoved() {
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

        HashMap<String, String> playerMap = new HashMap<>();
        playerMap.put("Player", String.valueOf(players.indexOf(currentPlayer) + 1));
        playerTurnInfoItem.showInfo(w * 0.02, w * 0.02, gc, playerMap);
    }

}
