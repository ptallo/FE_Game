package model.states;

import components.combat.CombatSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import lombok.Getter;
import model.Game;
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

    private Game game;

    public SquareSelectedState(Game game) {
        this.game = game;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            actionInfoItem.changeOption(-1);
        } else if (event.getCode() == KeyCode.DOWN) {
            actionInfoItem.changeOption(1);
        } else if (event.getCode() == KeyCode.ENTER) {
            ArrayList<String> options = game.getOptions(game.getSelectedUnit());
            String selectedOption = options.get(actionInfoItem.getOptionIndex());
            if (selectedOption.equalsIgnoreCase("Fight")) {
                // Go to combat state
                Unit enemySelectedUnit = combatSystem.getAttackableUnits(game.getUnits(), game.getSelectedUnit()).get(0);
                game.setEnemySelectedUnit(enemySelectedUnit);
                game.getCursor().setPoint(enemySelectedUnit.getPhysicsComponent().getPoint(), game.getMap());
                game.setCurrentState(game.getFightSelectionState());
            } else {
                game.getCurrentPlayerUnitsLeft().remove(game.getSelectedUnit());
                game.setSelectedUnit(null);
                game.setCurrentState(game.getNoUnitSelectedState());
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Move the Unit back to its previous position
            game.getSelectedUnit().getPhysicsComponent().revertPosition();
            game.setCurrentState(game.getUnitSelectedState());
        }
        game.checkChangeTurn();
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        game.getCursor().handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        game.getMap().draw(gc);

        Unit selectedUnit = game.getSelectedUnit();
        for (Point point : combatSystem.getAttackablePoints(selectedUnit.getCombatComponent(), selectedUnit.getPhysicsComponent().getPoint())) {
            gc.setFill(Color.rgb(255, 0, 0, 0.2));
            gc.fillRect(
                    point.getRealX(),
                    point.getRealY(),
                    Map.Tile_Width,
                    Map.Tile_Height
            );
        }

        for (Unit unit : game.getUnits()) {
            if (unit.getRenderComponent() != null) {
                boolean drawGrey = false;
                if (game.getCurrentPlayer() == unit.getOwner() && game.getCurrentPlayerUnitsLeft().indexOf(unit) == -1) {
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

        Point selectionPoint = game.getCursor().getSelectionPoint();
        renderSystem.draw(game.getCursor().getRenderComponent(), gc, selectionPoint);

        infoItem.draw(gc, w, h, game.getSelectedUnit().getInfo());

        actionInfoItem.draw(
                gc,
                new Point(selectionPoint.getX() + 1 + selectedUnit.getCombatComponent().getWeapon().getMaxRange(), selectionPoint.getY()),
                game.getOptions(selectedUnit)
        );
    }
}
