package components.combat;

import components.physics.PhysicsSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.ObjectInterface;
import model.Point;
import model.map.Map;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CombatSystem {
    private PhysicsSystem physicsSystem = new PhysicsSystem();

    public void drawAttackableArea(GraphicsContext gc, ObjectInterface object, Map map, ArrayList<ObjectInterface> units) {
        ArrayList<Point> attackablePoints = getAttackableArea(object, map, units);
        ArrayList<Point> movablePoints = physicsSystem.getMovablePoints(
                object.getPhysicsComponent(),
                map,
                units.stream().map(ObjectInterface::getPhysicsComponent).collect(Collectors.toList())
        );
        
        for (Point point : attackablePoints) {
            if (!point.inCollection(movablePoints)){
                Color red = new Color(1, 0, 0, 0.3);
                gc.setFill(red);
                gc.fillRect(point.getRealX(), point.getRealY(), Map.Tile_Width, Map.Tile_Height);
            }
        }
    }

    public ArrayList<Point> getAttackableArea(ObjectInterface object, Map map, ArrayList<ObjectInterface> units) {
        ArrayList<Point> attackablePoints = new ArrayList<>();
        ArrayList<Point> movablePoints = physicsSystem.getMovablePoints(
                object.getPhysicsComponent(),
                map,
                units.stream().map(ObjectInterface::getPhysicsComponent).collect(Collectors.toList())
        );

        for (Point point : movablePoints) {
            ArrayList<Point> newPoints = getAttackablePoints(object.getCombatComponent(), point);
            for (Point newPoint : newPoints) {
                if (!newPoint.inCollection(attackablePoints)) {
                    attackablePoints.add(newPoint);
                }
            }
        }

        return attackablePoints;
    }

    private ArrayList<Point> getAttackablePoints(CombatComponent combatComponent, Point point) {
        ArrayList<Point> attackablePoints = new ArrayList<>();
        int[] ranges = combatComponent.getWeapon().getRanges();
        for (int i = 0; i < ranges.length; i++) {
            int range = ranges[i];
            Point leftPoint = new Point(point.getX() - range, point.getY());
            attackablePoints.add(leftPoint);
            Point rightPoint = new Point(point.getX() + range, point.getY());
            attackablePoints.add(rightPoint);
            Point upPoint = new Point(point.getX(), point.getY() - range);
            attackablePoints.add(upPoint);
            Point downPoint = new Point(point.getX(), point.getY() + range);
            attackablePoints.add(downPoint);
        }
        return attackablePoints;
    }

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
