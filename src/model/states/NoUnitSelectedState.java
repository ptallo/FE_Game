package model.states;

import components.physics.PhysicsSystem;
import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Level;
import model.unit.Unit;
import util.Point;
import view.InfoItem;

public class NoUnitSelectedState implements StateInterface {

    private RenderSystem renderSystem = new RenderSystem();
    private PhysicsSystem physicsSystem = new PhysicsSystem();

    private InfoItem infoItem = new InfoItem();

    private Level level;

    public NoUnitSelectedState(Level level) {
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
            level.setSelectedUnit(level.getHoveredUnit());
            if (level.getSelectedUnit() != null) {
                level.setCurrentState(level.getUnitSelectedState());
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, double w, double h) {
        level.getCursor().handleTransform(gc, w, h);
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), w, h);

        level.getMap().draw(gc);

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

        for (Unit unit : level.getUnits()) {
            if (unit.getPhysicsComponent().getPoint().equals(level.getCursor().getSelectionPoint())) {
                infoItem.draw(gc, w, h, unit.getInfo());
                break;
            }
        }
        Point selectionPoint = level.getCursor().getSelectionPoint();
        renderSystem.draw(level.getCursor().getRenderComponent(), gc, selectionPoint);
    }
}
