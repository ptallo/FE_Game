package model.map;

import components.physics.PhysicsComponent;
import components.render.RenderComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapTile {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;
    private Integer travelCost;
    private String name;

    public MapTile(String path, int travelCost, int x, int y) {
        physicsComponent = new PhysicsComponent(x, y, 0);
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        this.travelCost = travelCost;
        this.name = path;
    }
}
