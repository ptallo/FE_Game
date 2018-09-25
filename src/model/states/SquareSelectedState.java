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

import java.util.ArrayList;
import java.util.HashMap;

public class SquareSelectedState implements StateInterface {

    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();
    private CombatSystem combatSystem = new CombatSystem();

    private Game game;

    public SquareSelectedState(Game game) {
        this.game = game;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            game.getActionInfoItem().changeOption(-1);
        } else if (event.getCode() == KeyCode.DOWN) {
            game.getActionInfoItem().changeOption(1);
        } else if (event.getCode() == KeyCode.ENTER) {
            ArrayList<String> options = game.getOptions(game.getSelectedUnit());
            String selectedOption = options.get(game.getActionInfoItem().getOptionIndex());
            if (selectedOption.equalsIgnoreCase("Fight")) {
                // Go to combat state
            } else {
                game.getCurrentPlayerUnitsLeft().remove(game.getSelectedUnit());
                game.setSelectedUnit(null);
            }

            game.getActionInfoItem().setDrawItem(false);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Move the Unit back to its previous position
            game.getSelectedUnit().getPhysicsComponent().revertPosition();
            game.getActionInfoItem().setDrawItem(false);
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

        Point selectionPoint = game.getCursor().getSelectionPoint();
        renderSystem.draw(game.getCursor().getRenderComponent(), gc, selectionPoint);
        game.getActionInfoItem().draw(
                gc,
                new Point(selectionPoint.getX() + 1 + selectedUnit.getCombatComponent().getWeapon().getMaxRange(), selectionPoint.getY()),
                game.getOptions(selectedUnit)
        );

        HashMap<String, String> playerMap = new HashMap<>();
        playerMap.put("Player", String.valueOf(game.getPlayers().indexOf(game.getCurrentPlayer()) + 1));
        game.getPlayerTurnInfoItem().showInfo(w * 0.02, w * 0.02, gc, playerMap);
    }
}
