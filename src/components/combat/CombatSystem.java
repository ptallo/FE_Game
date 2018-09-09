package components.combat;

import model.ObjectInterface;

import java.util.ArrayList;

public class CombatSystem {
    public void completeCombat(ObjectInterface attacker, ObjectInterface defender, ArrayList<ObjectInterface> units) {
        fight(attacker.getCombatComponent(), defender.getCombatComponent());
        checkDeath(defender, units);
        checkDeath(attacker, units);
    }

    private void checkDeath(ObjectInterface unit, ArrayList<ObjectInterface> units) {
        if (unit.getCombatComponent().isDead()){
            units.remove(unit);
        }
    }

    private void fight(CombatComponent attacker, CombatComponent defender) {
        //return -1 if attacker died, 0 if neither died and 1 if defender died
        takeDamage(defender, attacker);

        if (defender.isDead()) {
            return;
        }

        takeDamage(attacker, defender);
    }

    private void takeDamage(CombatComponent defender, CombatComponent attacker){
        if (attacker.getWeapon() != null) {
            int newHealth = defender.getCurrentHealth() - attacker.getWeapon().getDamage();
            if (newHealth <= 0) {
                newHealth = 0;
            }
            defender.setCurrentHealth(newHealth);
        }
    }
}
