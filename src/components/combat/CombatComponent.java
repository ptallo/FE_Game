package components.combat;

public class CombatComponent {

    private int healthCap;
    private int currentHealth;
    private int damage;

    public CombatComponent(int healthCap, int damage) {
        if (healthCap < 1) {
            this.healthCap = 1;
        } else {
            this.healthCap = healthCap;
        }
        this.currentHealth = healthCap;
        this.damage = damage;
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
