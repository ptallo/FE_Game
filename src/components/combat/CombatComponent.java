package components.combat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatComponent {

    private int health;
    private int currentHealth;

    private int strength;
    private int defense;
    private int magic;
    private int resistance;
    private int speed;

    private Weapon weapon;

    public CombatComponent(int health, int strength, int defense, int magic, int resistance, int speed) {
        if (health < 1) {
            this.health = 1;
        } else {
            this.health = health;
        }
        this.currentHealth = health;
        this.strength = strength;
        this.defense = defense;
        this.magic = magic;
        this.resistance = resistance;
        this.speed = speed;
    }

    boolean isAlive() {
        return currentHealth > 0;
    }

    boolean isDead(){
        return !isAlive();
    }
}
