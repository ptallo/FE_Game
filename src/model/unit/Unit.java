package model.unit;

import components.RenderComponent;
import model.Point;

public class Unit {
    public RenderComponent renderComponent;
    public Point point;

    public Unit(String path, int x, int y) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        point = new Point(x, y);
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public Point getPoint() {
        return point;
    }
}
