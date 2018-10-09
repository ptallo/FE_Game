package components.combat;

public enum WeaponEnum {
    SWORD(6, DamageType.PHYSICAL, 1),
    BOW(4, DamageType.PHYSICAL, 2),
    TOME(3, DamageType.MAGIC, 1, 2);

    private int damage;
    private DamageType damageType;
    private int[] ranges;

    WeaponEnum(int damage, DamageType damageType, int... ranges) {
        this.damage = damage;
        this.damageType = damageType;
        this.ranges = ranges;
    }

    public Weapon getInstance() {
        return new Weapon(damage, damageType, ranges);
    }
}
