package model.map;

import components.combat.CombatComponent;
import components.physics.PhysicsComponent;
import components.render.RenderComponent;
import model.ObjectInterface;

public class MapTile implements ObjectInterface {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;
    private Integer travelCost;

    public MapTile(String path, int travelCost, int x, int y) {
        physicsComponent = new PhysicsComponent(x, y, 0);
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        this.travelCost = travelCost;
    }

    public Integer getTravelCost() {
        return travelCost;
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public CombatComponent getCombatComponent() {
        return null;
    }
}
