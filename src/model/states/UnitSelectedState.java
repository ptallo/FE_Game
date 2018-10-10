package model.states;

import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Level;
import model.cursor.SelectionIndicator;
import model.unit.Unit;
import util.Point;
import view.InfoItem;

import java.util.ArrayList;

public class UnitSelectedState implements StateInterface {
    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();

    private SelectionIndicator selectionIndicator = new SelectionIndicator();
    private InfoItem infoItem = new InfoItem();

    private Level level;

    public UnitSelectedState(Level level) {
        this.level = level;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            level.getCursor().movePoint(0, -1, level.getMap());
            level.handleCursorMoved();
        } else if (event.getCode() == KeyCode.DOWN) {
            level.getCursor().movePoint(0, 1, level.getMap());
            level.handleCursorMoved();
        } else if (event.getCode() == KeyCode.LEFT) {
            level.getCursor().movePoint(-1, 0, level.getMap());
            level.handleCursorMoved();
        } else if (event.getCode() == KeyCode.RIGHT) {
            level.getCursor().movePoint(1, 0, level.getMap());
            level.handleCursorMoved();
        } else if (event.getCode() == KeyCode.ENTER) {
            physicsSystem.setPoint(level.getSelectedUnit(), level.getCursor().getSelectionPoint(), level.getMap(), level.getUnits());
            level.setCurrentState(level.getSquareSelectedState());
            level.getSquareSelectedState().getActionInfoItem().setOptionIndex(0);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            level.setSelectedUnit(null);
            level.setCurrentState(level.getNoUnitSelectedState());
        }
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        level.getCursor().handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        level.getMap().draw(gc);

        ArrayList<Unit> units = level.getUnits();
        physicsSystem.drawMovableArea(level.getSelectedUnit(), gc, level.getMap(), level.getUnits());
        renderSystem.draw(selectionIndicator.getRenderComponent(), gc, level.getSelectedUnit().getPhysicsComponent().getPoint());

        for (Unit unit : units) {
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

        infoItem.draw(gc, w, h, level.getSelectedUnit().getInfo());

        Point selectionPoint = level.getCursor().getSelectionPoint();
        renderSystem.draw(level.getCursor().getRenderComponent(), gc, selectionPoint);
    }
}
