package components.combat;

import model.unit.Unit;
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
        fight(attacker.getCombatComponent(), defender.getCombatComponent());
        checkDeath(defender, units);
        checkDeath(attacker, units);
    }

    private void checkDeath(Unit unit, ArrayList<Unit> units) {
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
