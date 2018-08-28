package components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;
import model.map.Map;
import model.map.MapTile;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class PhysicsComponent {
    private Point point;
    private Integer travelDistance;

    public PhysicsComponent(int x, int y, int travelDistance) {
        this.point = new Point(x, y);
        this.travelDistance = travelDistance;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point, Map map, ArrayList<Unit> units) {
        if (point.inCollection(getMovablePoints(map, units))) {
            this.point = point;
        }
    }

    public ArrayList<Point> getMovablePoints(Map map, ArrayList<Unit> units) {
        return getMovablePoints(map, units, travelDistance);
    }

    public ArrayList<Point> getMovablePoints(Map map, ArrayList<Unit> units, int distance) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(point);

        for (int i = distance; i > 0; i--) {
            ArrayList<Point> neighborsToAdd = new ArrayList<>();
            for (Point point : points) {
                for (Point neighbor : point.getNeighbors()) {
                    if (!neighbor.inCollection(points) && !neighbor.inCollection(neighborsToAdd) && pointIsMovable(map, units, neighbor)) {
                        neighborsToAdd.add(neighbor);
                    }
                }
            }
            points.addAll(neighborsToAdd);
        }

        points.remove(point);
        return points;
    }

    private boolean pointIsMovable(Map map, ArrayList<Unit> units, Point testPoint) {
        boolean unitAtPoint = false;
        for (Unit unit : units) {
            if (unit.getPhysicsComponent().getPoint().equals(testPoint)) {
                unitAtPoint = true;
            }
        }

        MapTile tileAtPoint = map.getTileAtPoint(testPoint);
        return !unitAtPoint && tileAtPoint != null && tileAtPoint.getPassable();
    }

    public void drawMovableArea(GraphicsContext gc, Map map, ArrayList<Unit> units) {
        List<Point> movablePoints = getMovablePoints(map, units);
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
}
