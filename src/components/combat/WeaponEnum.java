package components.combat;

public enum WeaponEnum {
    SWORD(6, 1),
    BOW(4, 2),
    TOME(3, 1, 2);

    private int damage;
    private int[] ranges;

    WeaponEnum(int damage, int... ranges) {
        this.damage = damage;
        this.ranges = ranges;
    }

    public Weapon getInstance() {
        return new Weapon(damage, ranges);
    }
}
