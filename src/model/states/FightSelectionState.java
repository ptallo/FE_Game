package model.states;

import components.combat.CombatSystem;
import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Level;
import model.map.Map;
import model.unit.Unit;
import util.Point;
import view.CombatInfoItem;

import java.util.ArrayList;

public class FightSelectionState implements StateInterface{

    private PhysicsSystem physicsSystem = new PhysicsSystem();
    private RenderSystem renderSystem = new RenderSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private CombatInfoItem combatInfoItem = new CombatInfoItem();
    private Level level;

    public FightSelectionState(Level level) {
        this.level = level;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            // Change the selected enemy unit
            moveSelectedEnemyUp();
        } else if (event.getCode() == KeyCode.DOWN) {
            // Change the selected enemy unit
            moveSelectedEnemyDown();
        } else if (event.getCode() == KeyCode.LEFT) {
            moveSelectedEnemyDown();
        } else if (event.getCode() == KeyCode.RIGHT) {
            moveSelectedEnemyUp();
        } else if (event.getCode() == KeyCode.ENTER) {
            // Complete a Fight between the Fight Unit and the Next Selected Unit
            combatSystem.completeCombat(level.getSelectedUnit(), level.getEnemySelectedUnit(), level.getUnits());
            level.getCurrentPlayerUnitsLeft().remove(level.getSelectedUnit());
            level.setEnemySelectedUnit(null);
            level.setSelectedUnit(null);
            level.setCurrentState(level.getNoUnitSelectedState());
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Go back to the Square Selected State
            level.setEnemySelectedUnit(null);
            level.getCursor().setPoint(level.getSelectedUnit().getPhysicsComponent().getPoint(), level.getMap());
            level.setCurrentState(level.getSquareSelectedState());
        }
        level.checkChangeTurn();
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        level.getCursor().handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        level.getMap().draw(gc);

        Unit selectedUnit = level.getSelectedUnit();
        for (Point point : combatSystem.getAttackablePoints(selectedUnit.getCombatComponent(), selectedUnit.getPhysicsComponent().getPoint())) {
            gc.setFill(Color.rgb(255, 0, 0, 0.2));
            gc.fillRect(
                    point.getRealX(),
                    point.getRealY(),
                    Map.Tile_Width,
                    Map.Tile_Height
            );
        }

        for (Unit unit : level.getUnits()) {
            if (unit.getRenderComponent() != null) {
                boolean drawGrey = false;
                if (level.getCurrentPlayer() == unit.getOwner() && level.getCurrentPlayerUnitsLeft().indexOf(unit) == -1) {
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

        renderSystem.draw(level.getCursor().getRenderComponent(), gc, level.getEnemySelectedUnit().getPhysicsComponent().getPoint());

        combatInfoItem.draw(gc, w, h, level.getSelectedUnit(), level.getEnemySelectedUnit());
    }

    private void moveSelectedEnemyUp() {
        ArrayList<Unit> enemyUnits = combatSystem.getAttackableUnits(level.getUnits(), level.getSelectedUnit());
        int index = enemyUnits.indexOf(level.getEnemySelectedUnit());
        if (index == enemyUnits.size() - 1) {
            index = 0;
        } else {
            index += 1;
        }

        level.setEnemySelectedUnit(enemyUnits.get(index));
    }

    private void moveSelectedEnemyDown() {
        ArrayList<Unit> enemyUnits = combatSystem.getAttackableUnits(level.getUnits(), level.getSelectedUnit());
        int index = enemyUnits.indexOf(level.getEnemySelectedUnit());
        if (index == 0) {
            index = enemyUnits.size() - 1;
        } else {
            index -= 1;
        }

        level.setEnemySelectedUnit(enemyUnits.get(index));
    }
}
