package model;

import components.combat.CombatSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.Setter;
import model.cursor.Cursor;
import model.cursor.SelectionIndicator;
import model.map.Map;
import model.states.*;
import model.unit.Unit;
import model.unit.UnitEnum;
import view.ActionInfoItem;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class Game {
    private Map map = new Map();
    private Cursor cursor = new Cursor();

    private ArrayList<Unit> units;
    private ArrayList<Unit> currentPlayerUnitsLeft;
    private Unit selectedUnit;
    private Unit hoveredUnit;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Unit enemySelectedUnit;

    private DialogState dialogState = new DialogState(new ArrayList<>(), this, null);
    private NoUnitSelectedState noUnitSelectedState = new NoUnitSelectedState(this);
    private UnitSelectedState unitSelectedState = new UnitSelectedState(this);
    private SquareSelectedState squareSelectedState = new SquareSelectedState(this);
    private FightSelectionState fightSelectionState = new FightSelectionState(this);

    private StateInterface currentState;

    public Game() {
        currentState = dialogState;
        dialogState.setFollowingState(noUnitSelectedState);

        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());

        units = new ArrayList<>();
        units.add(UnitEnum.ARCHER.getUnitInstance(players.get(0), 0,4, 5));
        units.add(UnitEnum.ARCHER.getUnitInstance(players.get(0), 0,4, 4));
        units.add(UnitEnum.SPEARMAN.getUnitInstance(players.get(1), 1, 1, 4));

        currentPlayerUnitsLeft = new ArrayList<>();
        currentPlayer = players.get(players.size() - 1);
        checkChangeTurn();
    }

    public void handleEvent(KeyEvent event) {
        // States : No Unit Selected, Unit Selected, Square Selected, Action Selected
        currentState.handleKeyEvent(event);
    }

    public void draw(GraphicsContext gc, double w, double h) {
        currentState.draw(gc, w, h);
    }

    public void handleCursorMoved() {
        // whenever the cursor on the map is moved we handle populating the hovered unit object
        Unit hoveredUnit = null;
        for (Unit unit : currentPlayerUnitsLeft) {
            if (cursor.getSelectionPoint().equals(unit.getPhysicsComponent().getPoint())) {
                hoveredUnit = unit;
            }
        }
        this.hoveredUnit = hoveredUnit;
    }

    public void checkChangeTurn() {
        // this checks to see if the turn is over and if it is it switches the players turns
        for (Player player : players) {
            int count = 0;
            for (Unit unit : units) {
                if (unit.getOwner() == player) {
                    count++;
                }
            }
            if (count == 0) {
                currentState = new GameOverState(true);
                return;
            }
        }

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

    public ArrayList<String> getOptions(Unit unit) {
        ArrayList<String> options = new ArrayList<>();
        options.add("Wait");
        if (new CombatSystem().getAttackableUnits(units, unit).size() != 0) {
            options.add("Fight");
        }
        return options;
    }
}
