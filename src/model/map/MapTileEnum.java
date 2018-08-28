package model.map;

public enum MapTileEnum {
    GRASSTILE("grasstile.png", true),
    SANDTILE("sandtile.png", false);

    private String path;
    private Boolean passable;

    MapTileEnum(String path, Boolean passable) {
        this.path = path;
        this.passable = passable;
    }

    public MapTile getMapTileInstance() {
        return new MapTile(path, passable);
    }
}


