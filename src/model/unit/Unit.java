package model.unit;

import components.combat.CombatComponent;
import components.combat.WeaponEnum;
import components.physics.PhysicsComponent;
import components.render.RenderComponent;
import model.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Unit {
    private RenderComponent renderComponent;
    private PhysicsComponent physicsComponent;
    private CombatComponent combatComponent;
    private String name;
    private Player owner;

    public Unit(String path, Player owner, int ownerIndex, int x, int y) {
        renderComponent = new RenderComponent(path, 32, 32, 1000, ownerIndex);
        physicsComponent = new PhysicsComponent(x, y, 7);
        combatComponent = new CombatComponent(10, WeaponEnum.SWORD.getInstance());
        this.owner = owner;
        this.name = path;
    }

    public List<String> getOptions() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Wait");
        strings.add("Fight");
        return strings;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
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
