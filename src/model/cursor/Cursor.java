package model.cursor;

import components.RenderComponent;
import javafx.scene.canvas.GraphicsContext;
import model.Point;
import model.map.Map;

public class Cursor {

    private Point selectionPoint;
    private RenderComponent renderComponent;

    public Cursor() {
        selectionPoint = new Point(0, 0);
        renderComponent = new RenderComponent("selection_cursor.png", 32, 32, 1000);
    }

    public void draw(GraphicsContext gc){
        renderComponent.draw(gc, selectionPoint);
    }

    public void movePoint(int x, int y, Map map) {
        int newX = selectionPoint.getX() + x;
        int newY = selectionPoint.getY() + y;

        int xTileCount = map.getMapTiles().get(0).size();
        int yTileCount = map.getMapTiles().size();

        if (newX < 0) {
            newX = 0;
        } else if (newX > xTileCount - 1) {
            newX = xTileCount - 1;
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY > yTileCount - 1) {
            newY = yTileCount - 1;
        }

        selectionPoint = new Point(newX, newY);
    }

    public Point getSelectionPoint() {
        return selectionPoint;
    }
}
