package components.combat;

public class CombatComponent {

    private int healthCap;
    private int currentHealth;
    private Weapon weapon;

    public CombatComponent(int healthCap) {
        if (healthCap < 1) {
            this.healthCap = 1;
        } else {
            this.healthCap = healthCap;
        }
        this.currentHealth = healthCap;
        this.weapon = WeaponEnum.SWORD.getInstance();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public boolean isDead(){
        return !isAlive();
    }
}
