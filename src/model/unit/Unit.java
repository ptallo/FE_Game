package model.unit;

import components.CombatComponent;
import components.PhysicsComponent;
import components.RenderComponent;

public class Unit {
    private RenderComponent renderComponent;
    private PhysicsComponent physicsComponent;
    private CombatComponent combatComponent;

    public Unit(String path, int x, int y) {
        renderComponent = new RenderComponent(path, 32, 32, 1000);
        physicsComponent = new PhysicsComponent(x, y, 3);
        combatComponent = new CombatComponent(10, 8);
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public CombatComponent getCombatComponent() {
        return combatComponent;
    }
}
