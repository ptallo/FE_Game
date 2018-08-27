package model.map;

public enum MapTileEnum {
    GRASSTILE("grasstile.png"),
    SANDTILE("sandtile.png");

    private String path;

    MapTileEnum(String path) {
        this.path = path;
    }

    public MapTile getMapTileInstance() {
        return new MapTile(path);
    }
}


