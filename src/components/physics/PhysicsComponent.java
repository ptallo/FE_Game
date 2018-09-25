package components.physics;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import util.Point;

@Getter
@Setter
public class PhysicsComponent {
    private Point previousPosition;
    @Setter(AccessLevel.NONE) private Point point;
    private Integer travelDistance;

    public PhysicsComponent(int x, int y, int travelDistance) {
        this.point = new Point(x, y);
        this.travelDistance = travelDistance;
    }

    public void setPoint(Point point) {
        this.previousPosition = this.point;
        this.point = point;
    }

    public void revertPosition() {
        this.point = previousPosition;
        this.previousPosition = null;
    }
}
