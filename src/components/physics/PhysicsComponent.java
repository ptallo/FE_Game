package components.physics;

import util.Point;

public class PhysicsComponent {
    private Point previousPosition;
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
        this.previousPosition = this.point;
        this.point = point;
    }

    public Integer getTravelDistance() {
        return travelDistance;
    }

    public void revertPosition() {
        this.point = previousPosition;
        this.previousPosition = null;
    }
}
