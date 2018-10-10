package model.unit;

import components.combat.WeaponEnum;
import model.Player;

public enum UnitEnum {
    SPEARMAN("units/spearman.png", WeaponEnum.SWORD, 4, 16, 8, 4, 4, 4, 0),
    ARCHER("units/archer.png", WeaponEnum.BOW, 8, 12, 4, 0, 4, 4, 10);

    private String path;
    private WeaponEnum weaponEnum;
    private int travelDistance;
    private int health;
    private int strength;
    private int defense;
    private int magic;
    private int resistance;
    private int speed;

    UnitEnum(String path, WeaponEnum weaponEnum, int travelDistance, int health, int strength, int defense, int magic, int resistance, int speed) {
        this.path = path;
        this.weaponEnum = weaponEnum;
        this.travelDistance = travelDistance;
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.magic = magic;
        this.resistance = resistance;
        this.speed = speed;
    }

    public Unit getUnitInstance(Player owner, int ownerIndex, int x, int y) {
        return new Unit(
                path,
                owner,
                ownerIndex,
                x,
                y,
                weaponEnum.getInstance(),
                travelDistance,
                health,
                strength,
                defense,
                magic,
                resistance,
                speed);
    }
}
