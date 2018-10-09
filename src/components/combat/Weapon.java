package components.combat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weapon {

    private DamageType damageType;
    private int damage;
    private int[] ranges;

    public Weapon(int damage, DamageType damageType, int... range) {
        this.damage = damage;
        this.damageType = damageType;
        this.ranges = range;
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
