package view;

import components.combat.CombatSystem;
import javafx.geometry.HPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.unit.Unit;
import util.ArrayUtils;
import util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class CombatInfoItem {

    private CombatSystem combatSystem = new CombatSystem();
    private double margin = 0.025;
    private Font font = new Font("Monospaced", 14);
    private TextRect textRect = new TextRect(font, 0.05, 5);
    private String[] rowHeaders = {"HP", "Damage", "New HP"};
    private String[] columnHeaders = {"Attacker", "Defender"};

    public void draw(GraphicsContext gc, double w, double h, Unit attacker, Unit defender) {
        double distanceFromEdge = w * margin;
        double baseX = -gc.getTransform().getTx();
        double baseY = -gc.getTransform().getTy();

        int width = 300;
        Rectangle headerRect = new Rectangle(
                baseX + distanceFromEdge,
                baseY + distanceFromEdge,
                width,
                textRect.getHeight(new ArrayList<>(Collections.singletonList("Test")))
        );

        Rectangle rowRect = new Rectangle(
                headerRect.getX(),
                headerRect.getY() + headerRect.getHeight(),
                width,
                textRect.getHeight(new ArrayList<>(Arrays.asList(rowHeaders)))
        );

        headerRect.draw(gc, Color.BEIGE, false);
        rowRect.draw(gc, Color.BEIGE, false);

        Rectangle joinedRect = new Rectangle(
                headerRect.getX(),
                headerRect.getY(),
                width,
                headerRect.getHeight() + rowRect.getHeight()
        );
        joinedRect.draw(gc, Color.BLACK, true);

        textRect.draw(gc, rowRect.getX(), rowRect.getY(), width / 3, HPos.LEFT, new ArrayList<>(Arrays.asList(rowHeaders)));
        textRect.draw(gc, rowRect.getX() + (width / 3), headerRect.getY(), width / 3, HPos.LEFT, getAttackerInfo(attacker, defender));
        textRect.draw(gc, rowRect.getX() + (width * 2 / 3), headerRect.getY(), width / 3, HPos.LEFT, getDefenderInfo(attacker, defender));
    }

    public ArrayList<String> getAttackerInfo(Unit attacker, Unit defender) {
        ArrayList<String> info = new ArrayList<>();
        info.add("Attacker");
        info.add(String.valueOf(attacker.getCombatComponent().getCurrentHealth()));
        int attackerDamage = combatSystem.getTotalIncomingDamage(attacker.getCombatComponent(), defender.getCombatComponent());
        int defenderDamage = combatSystem.getTotalIncomingDamage(defender.getCombatComponent(), attacker.getCombatComponent());

        int distance = attacker.getPhysicsComponent().getPoint().getDistance(defender.getPhysicsComponent().getPoint());
        if (!ArrayUtils.contains(defender.getCombatComponent().getWeapon().getRanges(), distance)) {
            defenderDamage = 0;
        }

        if (attacker.getCombatComponent().getSpeed() > defender.getCombatComponent().getSpeed() + 5) {
            attackerDamage = attackerDamage * 2;
        } else if (defender.getCombatComponent().getSpeed() > attacker.getCombatComponent().getSpeed() + 5) {
            defenderDamage = defenderDamage * 2;
        }

        info.add(String.valueOf(attackerDamage));
        int newHealth = attacker.getCombatComponent().getCurrentHealth() - defenderDamage;
        info.add(String.valueOf(newHealth > 0 ? newHealth : 0));
        return info;
    }

    public ArrayList<String> getDefenderInfo(Unit attacker, Unit defender) {
        ArrayList<String> info = new ArrayList<>();
        info.add("Defender");
        info.add(String.valueOf(defender.getCombatComponent().getCurrentHealth()));
        int attackerDamage = combatSystem.getTotalIncomingDamage(attacker.getCombatComponent(), defender.getCombatComponent());
        int defenderDamage = combatSystem.getTotalIncomingDamage(defender.getCombatComponent(), attacker.getCombatComponent());

        int distance = attacker.getPhysicsComponent().getPoint().getDistance(defender.getPhysicsComponent().getPoint());
        if (!ArrayUtils.contains(defender.getCombatComponent().getWeapon().getRanges(), distance)) {
            defenderDamage = 0;
        }

        if (attacker.getCombatComponent().getSpeed() > defender.getCombatComponent().getSpeed() + 5) {
            attackerDamage = attackerDamage * 2;
        } else if (defender.getCombatComponent().getSpeed() > attacker.getCombatComponent().getSpeed() + 5) {
            defenderDamage = defenderDamage * 2;
        }

        info.add(String.valueOf(defenderDamage));
        int newHealth = defender.getCombatComponent().getCurrentHealth() - attackerDamage;
        info.add(String.valueOf(newHealth > 0 ? newHealth : 0));
        return info;
    }
}
