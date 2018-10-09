package components.combat;

import model.unit.Unit;
import util.ArrayUtils;
import util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CombatSystem {

    public ArrayList<Unit> getAttackableUnits(ArrayList<Unit> units, Unit unit) {
        units = units.stream()
                .filter(unit1 -> unit.getOwner() != unit1.getOwner())
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Point> attackablePoints = new CombatSystem().getAttackablePoints(unit.getCombatComponent(), unit.getPhysicsComponent().getPoint());

        ArrayList<Unit> attackableUnits = new ArrayList<>();
        for (Point point : attackablePoints) {
            for (Unit aUnit : units) {
                if (point.equals(aUnit.getPhysicsComponent().getPoint())) {
                    attackableUnits.add(aUnit);
                }
            }
        }

        return attackableUnits;
    }

    public ArrayList<Point> getAttackablePoints(CombatComponent combatComponent, Point point) {
        ArrayList<Point> attackablePoints = new ArrayList<>();
        int[] ranges = combatComponent.getWeapon().getRanges();

        for (int range : ranges) {
            HashMap<Integer, Integer> xyPossibilities = getRangePossibilities(range);
            for (Integer x : xyPossibilities.keySet()) {
                Integer y = xyPossibilities.get(x);
                // + x + y, + x -y, -x + y, -x -y
                // Range 2: 0 2, 1 1, 2 0
                Point[] tempPoints = new Point[4];
                Point point1 = new Point(point.getX() + x, point.getY() + y);
                tempPoints[0] = point1;
                Point point2 = new Point(point.getX() + x, point.getY() - y);
                tempPoints[1] = point2;
                Point point3 = new Point(point.getX() - x, point.getY() + y);
                tempPoints[2] = point3;
                Point point4 = new Point(point.getX() - x, point.getY() - y);
                tempPoints[3] = point4;

                for (Point tempPoint : tempPoints) {
                    if (!tempPoint.inCollection(attackablePoints)) {
                        attackablePoints.add(tempPoint);
                    }
                }
            }
        }

        return attackablePoints;
    }

    private HashMap<Integer, Integer> getRangePossibilities(int range) {
        HashMap<Integer, Integer> xyPossibilities = new HashMap<>();
        for (int i = 0; i <= range; i++) {
            xyPossibilities.put(i, range - i);
        }
        return xyPossibilities;
    }

    public void completeCombat(Unit attacker, Unit defender, ArrayList<Unit> units) {
        fight(attacker, defender);
        checkDeath(defender, units);
        checkDeath(attacker, units);
    }

    private void checkDeath(Unit unit, ArrayList<Unit> units) {
        if (unit.getCombatComponent().isDead()) {
            units.remove(unit);
        }
    }

    private void fight(Unit attacker, Unit defender) {
        CombatComponent attackerComponent = attacker.getCombatComponent();
        CombatComponent defenderComponent = defender.getCombatComponent();
        takeDamage(attackerComponent, defenderComponent);

        if (defenderComponent.isDead()) {
            return;
        }

        int distance = attacker.getPhysicsComponent().getPoint().getDistance(defender.getPhysicsComponent().getPoint());
        if (ArrayUtils.contains(defender.getCombatComponent().getWeapon().getRanges(), distance)) {
            takeDamage(defenderComponent, attackerComponent);
            if (defenderComponent.getSpeed() >= attackerComponent.getSpeed() + 5 ) {
                takeDamage(defenderComponent, attackerComponent);
            }
        }

        if (attackerComponent.getSpeed() >= defenderComponent.getSpeed() + 5 ) {
            takeDamage(attackerComponent, defenderComponent);
        }
    }

    private void takeDamage(CombatComponent attacker, CombatComponent defender) {
        if (attacker.getWeapon() != null) {
            int totalIncomingDamage = getTotalIncomingDamage(attacker, defender);
            int newHealth = defender.getCurrentHealth() - (totalIncomingDamage > 0 ? totalIncomingDamage : 0);
            if (newHealth <= 0) {
                newHealth = 0;
            }
            defender.setCurrentHealth(newHealth);
        }
    }

    public int getTotalIncomingDamage(CombatComponent attacker, CombatComponent defender) {
        if (attacker.getWeapon().getDamageType() == DamageType.PHYSICAL) {
            return attacker.getWeapon().getDamage() + Math.floorDiv(attacker.getStrength(), 4) - Math.floorDiv(defender.getDefense(), 4);
        } else {
            return attacker.getWeapon().getDamage() + Math.floorDiv(attacker.getMagic(), 4) - Math.floorDiv(defender.getResistance(), 4);
        }
    }
}
