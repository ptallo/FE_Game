package model.unit;

import components.combat.CombatComponent;
import components.combat.WeaponEnum;
import components.physics.PhysicsComponent;
import components.render.RenderComponent;
import lombok.Getter;
import lombok.Setter;
import model.Player;

import java.util.Arrays;
import java.util.HashMap;

@Getter
@Setter
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

    public HashMap<String, String> getInfo() {
        HashMap<String, String> info = new HashMap<>();
        info.put("Owner", owner.getUuid());
        info.put("Name", name);
        info.put("Max Health", String.valueOf(combatComponent.getHealthCap()));
        info.put("Current Health", String.valueOf(combatComponent.getCurrentHealth()));
        info.put("Damage", String.valueOf(combatComponent.getWeapon().getDamage()));
        info.put("Ranges", Arrays.toString(combatComponent.getWeapon().getRanges()));
        return info;
    }
}
