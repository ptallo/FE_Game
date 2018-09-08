package components.combat;

import java.util.ArrayList;
import java.util.Arrays;

public class CombatComponent {

    private int healthCap;
    private int currentHealth;
    private int damage;
    private ArrayList<Integer> attackRanges;

    public CombatComponent(int healthCap, int damage, Integer... ranges) {
        if (healthCap < 1) {
            this.healthCap = 1;
        } else {
            this.healthCap = healthCap;
        }
        this.currentHealth = healthCap;
        this.damage = damage;
        attackRanges = new ArrayList<>();
        attackRanges.addAll(Arrays.asList(ranges));
    }

    public int getHealthCap() {
        return healthCap;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public boolean isDead(){
        return !isAlive();
    }
}
