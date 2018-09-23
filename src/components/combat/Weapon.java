package components.combat;

public class Weapon {

    private int damage;
    private int[] ranges;

    public Weapon(int damage, int... range) {
        this.damage = damage;
        this.ranges = range;
    }

    public int getDamage() {
        return damage;
    }

    public int[] getRanges() {
        return ranges;
    }

    public int getMaxRange() {
        int max = ranges[0];
        for (int range : ranges) {
            if (range > max) {
                max = range;
            }
        }
        return max;
    }
}
