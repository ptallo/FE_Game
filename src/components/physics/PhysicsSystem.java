package components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;
import model.map.Map;
import model.map.MapTile;
import util.PriorityQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PhysicsSystem {
    public void setPoint(PhysicsComponent component, Point point, Map map, List<PhysicsComponent> units) {
        if (point.inCollection(getMovablePoints(component, map, units))) {
            component.setPoint(point);
        }
    }

    private ArrayList<Point> getMovablePoints(PhysicsComponent component, Map map, List<PhysicsComponent> units) {
        ArrayList<Point> movablePoints = new ArrayList<>();

        HashMap<MapTile, Integer> costSoFar = new HashMap<>();
        HashMap<MapTile, MapTile> cameFrom = new HashMap<>();
        PriorityQueue<MapTile> frontier = new PriorityQueue<>();

        MapTile startTile = map.getTileAtPoint(component.getPoint());
        costSoFar.put(startTile, 0);
        cameFrom.put(startTile, null);
        frontier.add(startTile, 0);

        while (!frontier.isEmpty()) {
            MapTile currentTile = frontier.get();

            ArrayList<MapTile> neighborTiles = new ArrayList<>();
            for (Point point : currentTile.getPhysicsComponent().getPoint().getNeighbors()) {
                MapTile tileAtPoint = map.getTileAtPoint(point);
                if (tileAtPoint != null) {
                    neighborTiles.add(tileAtPoint);
                }
            }

            for (MapTile tile : neighborTiles) {
                int newCost = costSoFar.get(currentTile) + tile.getTravelCost();
                if ((costSoFar.get(tile) == null || newCost < costSoFar.get(tile)) && newCost <= component.getTravelDistance()) {
                    costSoFar.put(tile, newCost);
                    frontier.add(tile, newCost);
                    cameFrom.put(tile, currentTile);
                }
            }
        }

        movablePoints.addAll(
                costSoFar.keySet().stream()
                        .map(MapTile::getPhysicsComponent)
                        .map(PhysicsComponent::getPoint)
                        .collect(Collectors.toList())
        );
        return movablePoints;
    }

    private boolean pointIsMovable(Map map, List<PhysicsComponent> units, Point testPoint) {
        boolean unitAtPoint = false;
        for (PhysicsComponent unit : units) {
            if (unit.getPoint().equals(testPoint)) {
                unitAtPoint = true;
            }
        }

        MapTile tileAtPoint = map.getTileAtPoint(testPoint);
        return !unitAtPoint && tileAtPoint != null && tileAtPoint.getTravelCost() < 99;
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
