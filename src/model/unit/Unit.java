package model.unit;

import components.RenderComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;
import model.map.Map;
import model.map.MapTile;

import java.util.ArrayList;

public class Unit {
    public RenderComponent renderComponent;
    public Point point;
    private Integer travelDistance;
    private ArrayList<Point> movablePoints;

    public Unit(String path, int x, int y) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        point = new Point(x, y);
        movablePoints = new ArrayList<>();
        travelDistance = 3;
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        if (point.getTravelDistance(this.point) <= travelDistance) {
            this.point = point;
        }
    }

    public void drawMovableArea(GraphicsContext gc, Map map, ArrayList<Unit> units) {
        ArrayList<Point> movablePoints = getMovablePoints(map, units);
        for (Point point : movablePoints) {
            gc.setFill(Color.rgb(0, 0, 255, 0.3));
            gc.fillRect(
                    point.getX() * Map.Tile_Width,
                    point.getY() * Map.Tile_Height,
                    Map.Tile_Width,
                    Map.Tile_Height
            );
        }
    }

    public ArrayList<Point> getMovablePoints(Map map, ArrayList<Unit> units) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(point);

        for (int i = travelDistance; i > 0; i--) {
            ArrayList<Point> neighborsToAdd = new ArrayList<>();
            for (Point point : points) {
                for (Point neighbor : point.getNeighbors()) {
                    if (!neighbor.inArray(points) && !neighbor.inArray(neighborsToAdd) && pointIsMovable(map, units, neighbor)) {
                        neighborsToAdd.add(neighbor);
                    }
                }
            }
            points.addAll(neighborsToAdd);
        }

        return points;
    }

    private boolean pointIsMovable(Map map, ArrayList<Unit> units, Point testPoint) {
        boolean unitAtPoint = false;
        for (Unit unit : units) {
            if (unit.getPoint().equals(testPoint)) {
                unitAtPoint = true;
            }
        }

        MapTile tileAtPoint = map.getTileAtPoint(testPoint);
        return !unitAtPoint && tileAtPoint != null && tileAtPoint.getPassable();
    }
}
