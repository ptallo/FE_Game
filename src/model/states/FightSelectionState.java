package model.states;

import components.combat.CombatSystem;
import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Game;
import model.map.Map;
import model.unit.Unit;
import util.Point;

public class FightSelectionState implements StateInterface{

    private PhysicsSystem physicsSystem = new PhysicsSystem();
    private RenderSystem renderSystem = new RenderSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private Game game;

    public FightSelectionState(Game game) {
        this.game = game;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            // Change the selected enemy unit
        } else if (event.getCode() == KeyCode.DOWN) {
            // Change the selected enemy unit
        } else if (event.getCode() == KeyCode.ENTER) {
            // Complete a Fight between the Fight Unit and the Next Selected Unit
            combatSystem.completeCombat(game.getSelectedUnit(), game.getEnemySelectedUnit(), game.getUnits());
            game.getCurrentPlayerUnitsLeft().remove(game.getSelectedUnit());
            game.setEnemySelectedUnit(null);
            game.setSelectedUnit(null);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Go back to the Square Selected State
            game.setEnemySelectedUnit(null);
            game.getCursor().setPoint(game.getSelectedUnit().getPhysicsComponent().getPoint(), game.getMap());
            game.getActionInfoItem().setDrawItem(true);
        }
        game.checkChangeTurn();
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
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

        renderSystem.draw(game.getCursor().getRenderComponent(), gc, game.getCursor().getSelectionPoint());
    }
}
