package components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;
import model.map.Map;
import model.map.MapTile;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem {
    public void setPoint(PhysicsComponent component, Point point, Map map, List<PhysicsComponent> units) {
        if (point.inCollection(getMovablePoints(component, map, units))) {
            component.setPoint(point);
        }
    }

    private ArrayList<Point> getMovablePoints(PhysicsComponent component, Map map, List<PhysicsComponent> units) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(component.getPoint());

        for (int i = component.getTravelDistance(); i > 0; i--) {
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

        points.remove(component.getPoint());
        return points;
    }

    private boolean pointIsMovable(Map map, List<PhysicsComponent> units, Point testPoint) {
        boolean unitAtPoint = false;
        for (PhysicsComponent unit : units) {
            if (unit.getPoint().equals(testPoint)) {
                unitAtPoint = true;
            }
        }

        MapTile tileAtPoint = map.getTileAtPoint(testPoint);
        return !unitAtPoint && tileAtPoint != null && tileAtPoint.getPassable();
    }

    public void drawMovableArea(PhysicsComponent component, GraphicsContext gc, Map map, List<PhysicsComponent> units) {
        List<Point> movablePoints = getMovablePoints(component, map, units);
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
