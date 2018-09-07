package model.cursor;

import components.render.RenderComponent;
import javafx.scene.canvas.GraphicsContext;
import model.Point;
import model.map.Map;

public class Cursor {

    private double xTransform = 0;
    private double yTransform = 0;

    private Point selectionPoint;
    private RenderComponent renderComponent;

    public Cursor() {
        selectionPoint = new Point(0, 0);
        renderComponent = new RenderComponent("selection_cursor.png", 32, 32, 1000);
    }

    public void setPoint(Point point, Map map) {
        int xTileCount = map.getMapTiles().get(0).size();
        int yTileCount = map.getMapTiles().size();

        if (point.getX() < 0) {
            point.setX(0);
        } else if (point.getX() > xTileCount - 1) {
            point.setY(xTileCount - 1);
        }

        if (point.getY() < 0) {
            point.setY(0);
        } else if (point.getY() > yTileCount - 1) {
            point.setY(yTileCount - 1);
        }

        selectionPoint = point;
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

    public void handleTransform(GraphicsContext gc, double w, double h) {
        double minX = -gc.getTransform().getTx();
        double maxX = minX + Math.floor(w / Map.Tile_Width) * Map.Tile_Width - Map.Tile_Width;

        double minY = -gc.getTransform().getTy();
        double maxY = minY + Math.floor(h / Map.Tile_Height) * Map.Tile_Height - Map.Tile_Height;

        if (minX > selectionPoint.getRealX()) {
            gc.setTransform(1, 0, 0, 1, gc.getTransform().getTx() + Map.Tile_Width, gc.getTransform().getTy());
        } else if (maxX < selectionPoint.getRealX()) {
            gc.setTransform(1, 0, 0, 1, gc.getTransform().getTx() - Map.Tile_Width, gc.getTransform().getTy());
        }

        if (minY > selectionPoint.getRealY()) {
            gc.setTransform(1, 0, 0, 1, gc.getTransform().getTx(), gc.getTransform().getTy() + Map.Tile_Height);
        } else if (maxY < selectionPoint.getRealY()) {
            gc.setTransform(1, 0, 0, 1, gc.getTransform().getTx(), gc.getTransform().getTy() - Map.Tile_Height);
        }

        xTransform = gc.getTransform().getTx();
        yTransform = gc.getTransform().getTy();
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public Point getSelectionPoint() {
        return selectionPoint;
    }

    public double getxTransform() {
        return xTransform;
    }

    public double getyTransform() {
        return yTransform;
    }
}
