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
import model.states.UnitSelectedState;
import model.unit.Unit;
import model.unit.UnitEnum;
import util.Point;
import view.ActionInfoItem;
import view.InfoItem;

import java.util.ArrayList;
import java.util.Collections;
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

    private ActionInfoItem actionInfoItem = new ActionInfoItem();

    private ArrayList<Unit> units;
    private ArrayList<Unit> currentPlayerUnitsLeft;
    private Unit selectedUnit;
    private Unit hoveredUnit;

    private ArrayList<Player> players;
    private Player currentPlayer;

    private UnitSelectedState unitSelectedState = new UnitSelectedState(this);

    public Game() {
        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());

        units = new ArrayList<>();
        units.add(UnitEnum.SPEARMAN.getUnitInstance(players.get(0), 0,7, 7));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(players.get(0), 0,7, 5));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(players.get(1), 1, 1, 6));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(players.get(1), 1, 1, 4));

        currentPlayerUnitsLeft = new ArrayList<>();
        currentPlayer = players.get(players.size() - 1);
        checkChangeTurn();
    }

    public void handleEvent(KeyEvent event) {
        // States : No Unit Selected, Unit Selected, Square Selected, Action Selected
        if (selectedUnit == null) {
            handleKeyEventNoUnit(event);
        } else if (actionInfoItem.getDrawItem()) {
            handleKeyEventSquareSelected(event);
        } else {
            unitSelectedState.handleKeyEvent(event);
        }
    }

    public void draw(GraphicsContext gc, double w, double h) {
        cursor.handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        map.draw(gc);

        if (selectedUnit == null) {
            drawNoUnit(gc, w, h);
        } else if (actionInfoItem.getDrawItem()) {
            drawSelectedSquare(gc, w, h);
        } else {
            unitSelectedState.draw(gc, w, h);
        }
    }

    private void handleKeyEventNoUnit(KeyEvent event) {
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
            this.selectedUnit = hoveredUnit;
        }
    }

    private void drawNoUnit(GraphicsContext gc, double w, double h) {
        for (Unit unit : units) {
            if (unit.getRenderComponent() != null) {
                boolean drawGrey = false;
                if (currentPlayer == unit.getOwner() && currentPlayerUnitsLeft.indexOf(unit) == -1) {
                    drawGrey = true;
                }

                renderSystem.draw(
                        unit.getRenderComponent(),
                        gc,
                        unit.getPhysicsComponent().getPoint(),
                        drawGrey
                );
            }
        }

        Point selectionPoint = cursor.getSelectionPoint();
        renderSystem.draw(cursor.getRenderComponent(), gc, selectionPoint);

        HashMap<String, String> playerMap = new HashMap<>();
        playerMap.put("Player", String.valueOf(players.indexOf(currentPlayer) + 1));
        playerTurnInfoItem.showInfo(w * 0.02, w * 0.02, gc, playerMap);
    }

    private void handleKeyEventSquareSelected(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            actionInfoItem.changeOption(-1);
        } else if (event.getCode() == KeyCode.DOWN) {
            actionInfoItem.changeOption(1);
        } else if (event.getCode() == KeyCode.ENTER) {
            ArrayList<String> options = getOptions(selectedUnit);
            String selectedOption = options.get(actionInfoItem.getOptionIndex());
            if (selectedOption.equalsIgnoreCase("Fight")) {
//                 combatSystem.completeCombat(selectedUnit);
            } else {
                currentPlayerUnitsLeft.remove(selectedUnit);
                selectedUnit = null;
            }

            actionInfoItem.setDrawItem(false);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Move the Unit back to its previous position
            selectedUnit.getPhysicsComponent().revertPosition();
            actionInfoItem.setDrawItem(false);
        }
        checkChangeTurn();
    }

    private void drawSelectedSquare(GraphicsContext gc, double w, double h) {
        for (Unit unit : units) {
            if (unit.getRenderComponent() != null) {
                boolean drawGrey = false;
                if (currentPlayer == unit.getOwner() && currentPlayerUnitsLeft.indexOf(unit) == -1) {
                    drawGrey = true;
                }

                renderSystem.draw(
                        unit.getRenderComponent(),
                        gc,
                        unit.getPhysicsComponent().getPoint(),
                        drawGrey
                );
            }
        }

        Point selectionPoint = cursor.getSelectionPoint();
        renderSystem.draw(cursor.getRenderComponent(), gc, selectionPoint);
        actionInfoItem.draw(
                gc,
                new Point(
                        selectionPoint.getX() + 1 + selectedUnit.getCombatComponent().getWeapon().getMaxRange(),
                        selectionPoint.getY()
                ),
                getOptions(selectedUnit)
        );

        HashMap<String, String> playerMap = new HashMap<>();
        playerMap.put("Player", String.valueOf(players.indexOf(currentPlayer) + 1));
        playerTurnInfoItem.showInfo(w * 0.02, w * 0.02, gc, playerMap);
    }

    public void handleCursorMoved() {
        Unit hoveredUnit = null;
        for (Unit unit : currentPlayerUnitsLeft) {
            if (cursor.getSelectionPoint().equals(unit.getPhysicsComponent().getPoint())) {
                hoveredUnit = unit;
            }
        }
        this.hoveredUnit = hoveredUnit;
    }

    private void checkChangeTurn() {
        if (currentPlayerUnitsLeft.size() == 0) {
            int index = players.indexOf(currentPlayer);
            if (index < players.size() - 1) {
                currentPlayer = players.get(index + 1);
            } else {
                currentPlayer = players.get(0);
            }

            currentPlayerUnitsLeft = units.stream()
                    .filter(unit -> unit.getOwner().getUuid().equals(currentPlayer.getUuid()))
                    .collect(Collectors.toCollection(ArrayList::new));

            Unit unit = currentPlayerUnitsLeft.get(0);
            cursor.setPoint(unit.getPhysicsComponent().getPoint(), map);
            hoveredUnit = unit;
        }
    }

    private ArrayList<String> getOptions(Unit unit) {
        ArrayList<String> options = new ArrayList<>();
        options.add("Wait");
        if (combatSystem.getAttackableUnits(units, unit).size() != 0) {
            options.add("Fight");
        }
        return options;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public ActionInfoItem getActionInfoItem() {
        return actionInfoItem;
    }

    public SelectionIndicator getSelectionIndicator() {
        return selectionIndicator;
    }

    public InfoItem getPlayerTurnInfoItem() {
        return playerTurnInfoItem;
    }

    public ArrayList<Unit> getCurrentPlayerUnitsLeft() {
        return currentPlayerUnitsLeft;
    }

    public Unit getHoveredUnit() {
        return hoveredUnit;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
