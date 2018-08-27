package model.unit;

public enum UnitEnum {
    SPEARMAN("spearman.png");

    private String path;

    UnitEnum(String path) {
        this.path = path;
    }

    public Unit getUnitInstance(int x, int y) {
        return new Unit(path, x, y);
    }
}
