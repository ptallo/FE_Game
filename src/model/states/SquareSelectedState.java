package model.states;

import components.combat.CombatSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import lombok.Getter;
import model.Level;
import model.map.Map;
import model.unit.Unit;
import util.Point;
import view.ActionInfoItem;
import view.InfoItem;

import java.util.ArrayList;

@Getter
public class SquareSelectedState implements StateInterface {

    private RenderSystem renderSystem = new RenderSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private ActionInfoItem actionInfoItem = new ActionInfoItem();
    private InfoItem infoItem = new InfoItem();

    private Level level;

    public SquareSelectedState(Level level) {
        this.level = level;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            actionInfoItem.changeOption(-1);
        } else if (event.getCode() == KeyCode.DOWN) {
            actionInfoItem.changeOption(1);
        } else if (event.getCode() == KeyCode.ENTER) {
            ArrayList<String> options = level.getOptions(level.getSelectedUnit());
            String selectedOption = options.get(actionInfoItem.getOptionIndex());
            if (selectedOption.equalsIgnoreCase("Fight")) {
                // Go to combat state
                Unit enemySelectedUnit = combatSystem.getAttackableUnits(level.getUnits(), level.getSelectedUnit()).get(0);
                level.setEnemySelectedUnit(enemySelectedUnit);
                level.getCursor().setPoint(enemySelectedUnit.getPhysicsComponent().getPoint(), level.getMap());
                level.setCurrentState(level.getFightSelectionState());
            } else {
                level.getCurrentPlayerUnitsLeft().remove(level.getSelectedUnit());
                level.setSelectedUnit(null);
                level.setCurrentState(level.getNoUnitSelectedState());
                level.checkChangeTurn();
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Move the Unit back to its previous position
            level.getSelectedUnit().getPhysicsComponent().revertPosition();
            level.setCurrentState(level.getUnitSelectedState());
        }
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

        Point selectionPoint = level.getCursor().getSelectionPoint();
        renderSystem.draw(level.getCursor().getRenderComponent(), gc, selectionPoint);

        infoItem.draw(gc, w, h, level.getSelectedUnit().getInfo());

        actionInfoItem.draw(
                gc,
                new Point(selectionPoint.getX() + 1 + selectedUnit.getCombatComponent().getWeapon().getMaxRange(), selectionPoint.getY()),
                level.getOptions(selectedUnit)
        );
    }
}
