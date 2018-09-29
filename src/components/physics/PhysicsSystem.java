package components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.unit.Unit;
import util.Point;
import model.map.Map;
import model.map.MapTile;
import util.PriorityQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PhysicsSystem {
    public void setPoint(Unit unit, Point point, Map map, List<Unit> units) {
        if (point.inCollection(getMovablePoints(unit, map, units))) {
            unit.getPhysicsComponent().setPoint(point);
        }
    }

    public ArrayList<Point> getMovablePoints(Unit unit, Map map, List<Unit> units) {
        HashMap<MapTile, Integer> costSoFar = new HashMap<>();
        HashMap<MapTile, MapTile> cameFrom = new HashMap<>();
        PriorityQueue<MapTile> frontier = new PriorityQueue<>();

        MapTile startTile = map.getTileAtPoint(unit.getPhysicsComponent().getPoint());
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
                if ((costSoFar.get(tile) == null || newCost < costSoFar.get(tile)) &&
                        newCost <= unit.getPhysicsComponent().getTravelDistance() &&
                        pointIsMovable(map, units.stream().filter(unit1 -> unit1.getOwner() != unit.getOwner()).collect(Collectors.toList()), tile.getPhysicsComponent().getPoint())) {
                    costSoFar.put(tile, newCost);
                    frontier.add(tile, newCost);
                    cameFrom.put(tile, currentTile);
                }
            }
        }

        ArrayList<Point> pointsCollection = costSoFar.keySet().stream()
                .map(MapTile::getPhysicsComponent)
                .map(PhysicsComponent::getPoint)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Point> pointsToRemove = new ArrayList<>();
        for (Unit alliedUnit : units.stream().filter(unit1 -> unit1.getOwner() == unit.getOwner() && unit1 != unit).collect(Collectors.toList())) {
            for (Point point : pointsCollection) {
                if (alliedUnit.getPhysicsComponent().getPoint().equals(point)) {
                    pointsToRemove.add(point);
                }
            }
        }

        pointsCollection.removeAll(pointsToRemove);

        return pointsCollection;
    }

    private boolean pointIsMovable(Map map, List<Unit> units, Point testPoint) {
        boolean unitAtPoint = false;
        for (Unit unit : units) {
            if (unit.getPhysicsComponent().getPoint().equals(testPoint)) {
                unitAtPoint = true;
            }
        }

        MapTile tileAtPoint = map.getTileAtPoint(testPoint);
        return !unitAtPoint && tileAtPoint != null;
    }

    public void drawMovableArea(Unit unit, GraphicsContext gc, Map map, List<Unit> units) {
        List<Point> movablePoints = getMovablePoints(unit, map, units);
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
