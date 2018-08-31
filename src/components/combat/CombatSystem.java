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
        takeDamage(defender, attacker.getDamage());

        if (defender.isDead()) {
            return;
        }

        takeDamage(attacker, defender.getDamage());
    }

    private void takeDamage(CombatComponent component, int amount){
        int newHealth = component.getCurrentHealth() - amount;
        if (newHealth <= 0) {
            newHealth = 0;
        }
        component.setCurrentHealth(newHealth);
    }
}
