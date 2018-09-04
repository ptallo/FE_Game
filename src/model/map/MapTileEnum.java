package model.map;

public enum MapTileEnum {
    GRASSTILE("grasstile.png", true, 1),
    SANDTILE("sandtile.png", false, 99);

    private String path;
    private Boolean passable;
    private Integer travelCost;

    MapTileEnum(String path, Boolean passable, int travelCost) {
        this.path = path;
        this.passable = passable;
        this.travelCost = travelCost;
    }

    public MapTile getMapTileInstance(int x, int y) {
        return new MapTile(path, passable, travelCost, x, y);
    }
}


