package model.states;

import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;
import model.unit.Unit;
import util.Point;

import java.util.HashMap;

public class NoUnitSelectedState implements StateInterface {

    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();

    private Game game;

    public NoUnitSelectedState(Game game) {
        this.game = game;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            game.getCursor().movePoint(0, -1, game.getMap());
            game.handleCursorMoved();
        } else if (event.getCode() == KeyCode.DOWN) {
            game.getCursor().movePoint(0, 1, game.getMap());
            game.handleCursorMoved();
        } else if (event.getCode() == KeyCode.LEFT) {
            game.getCursor().movePoint(-1, 0, game.getMap());
            game.handleCursorMoved();
        } else if (event.getCode() == KeyCode.RIGHT) {
            game.getCursor().movePoint(1, 0, game.getMap());
            game.handleCursorMoved();
        } else if (event.getCode() == KeyCode.ENTER) {
            game.setSelectedUnit(game.getHoveredUnit());
        }
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
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

        for (Unit unit : game.getUnits()) {
            if (unit.getPhysicsComponent().getPoint().equals(selectionPoint)) {
                game.getUnitInfoItem().showInfo(new Point(1, 10), gc, unit.getInfo());
                break;
            }
        }

        HashMap<String, String> playerMap = new HashMap<>();
        playerMap.put("Player", String.valueOf(game.getPlayers().indexOf(game.getCurrentPlayer()) + 1));
        game.getPlayerTurnInfoItem().showInfo(w * 0.02, w * 0.02, gc, playerMap);
    }
}
