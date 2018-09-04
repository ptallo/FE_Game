package model.map;

public enum MapTileEnum {
    GRASSTILE("grasstile.png", 1),
    SANDTILE("sandtile.png", 99),
    FOREST("forest.png", 3);

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


