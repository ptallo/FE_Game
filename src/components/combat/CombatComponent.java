package components.combat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatComponent {

    private int healthCap;
    private int currentHealth;
    private Weapon weapon;

    public CombatComponent(int healthCap, Weapon weapon) {
        if (healthCap < 1) {
            this.healthCap = 1;
        } else {
            this.healthCap = healthCap;
        }
        this.currentHealth = healthCap;
        this.weapon = weapon;
    }

    boolean isAlive() {
        return currentHealth > 0;
    }

    boolean isDead(){
        return !isAlive();
    }
}
