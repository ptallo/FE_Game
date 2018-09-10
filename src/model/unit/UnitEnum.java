package model.unit;

import model.Player;

public enum UnitEnum {
    SPEARMAN("spearman.png");

    private String path;

    UnitEnum(String path) {
        this.path = path;
    }

    public Unit getUnitInstance(Player owner, int x, int y) {
        return new Unit(path, owner, x, y);
    }
}
