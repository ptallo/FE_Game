package model.states;

import components.physics.PhysicsComponent;
import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Game;
import model.unit.Unit;
import util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UnitSelectedState implements StateInterface {
    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();

    private Game game;

    public UnitSelectedState(Game game) {
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
            physicsSystem.setPoint(game.getSelectedUnit(), game.getCursor().getSelectionPoint(), game.getMap(), game.getUnits());
            game.getActionInfoItem().setDrawItem(true);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            game.setSelectedUnit(null);
        }
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        ArrayList<Unit> units = game.getUnits();
        physicsSystem.drawMovableArea(game.getSelectedUnit(), gc, game.getMap(), game.getUnits());
        renderSystem.draw(game.getSelectionIndicator().getRenderComponent(), gc, game.getSelectedUnit().getPhysicsComponent().getPoint());

        for (Unit unit : units) {
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
    }
}
