package model.unit;

import components.RenderComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;
import model.map.Map;

public class Unit {
    public RenderComponent renderComponent;
    public Point point;
    private Integer travelDistance;

    public Unit(String path, int x, int y) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        point = new Point(x, y);
        travelDistance = 2;
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

    public Integer getTravelDistance() {
        return travelDistance;
    }

    public void drawMovableArea(GraphicsContext gc, Map map) {
        for (int x = 0; x < map.getMapTiles().get(0).size(); x++) {
            for (int y = 0; y < map.getMapTiles().size(); y++) {
                Point testPoint = new Point(x, y);
                if (point.getTravelDistance(testPoint) <= travelDistance && map.getTileAtPoint(testPoint).getPassable()){
                    gc.setFill(Color.rgb(0, 0, 255, 0.3));
                    gc.fillRect(
                            testPoint.getX() * Map.Tile_Width,
                            testPoint.getY() * Map.Tile_Height,
                            Map.Tile_Width,
                            Map.Tile_Height);
                }
            }
        }
    }
}
