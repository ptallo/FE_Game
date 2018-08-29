package components;

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

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public boolean isDead(){
        return !isAlive();
    }

    public void fight(CombatComponent opponent) {
        opponent.takeDamage(damage);

        if (opponent.isDead()) {
            return;
        }

        this.takeDamage(opponent.getDamage());
    }

    public void takeDamage(int amount){
        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0;
        }
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > healthCap) {
            currentHealth = healthCap;
        }
    }
}
