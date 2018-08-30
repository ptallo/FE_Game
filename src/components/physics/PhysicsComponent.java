package components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;
import model.map.Map;
import model.map.MapTile;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void setPoint(Point point) {
        this.point = point;
    }

    public Integer getTravelDistance() {
        return travelDistance;
    }
}
