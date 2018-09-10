package model.map;

public enum MapTileEnum {
    GRASSTILE("tiles/grasstile.png", 1),
    SANDTILE("tiles/sandtile.png", 99),
    FOREST("tiles/forest.png", 3);

    private String path;
    private Integer travelCost;

    MapTileEnum(String path, int travelCost) {
        this.path = path;
        this.travelCost = travelCost;
    }

    public MapTile getMapTileInstance(int x, int y) {
        return new MapTile(path, travelCost, x, y);
    }
}


