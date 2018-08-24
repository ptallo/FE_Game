package model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Map {

    public static final double Tile_Width = 32;
    public static final double Tile_Height = 32;

    public ArrayList<ArrayList<MapTile>> mapTiles;

    public Map() {
        ArrayList<String> mapDef = new ArrayList<>();
        mapDef.add("0011");
        mapDef.add("0000");
        mapDef.add("0000");
        mapDef.add("0000");
        initMapTiles(mapDef);
    }

    public void draw(GraphicsContext gc){
        for (int y = 0; y < mapTiles.size(); y++) {
            for (int x = 0; x < mapTiles.get(y).size(); x++) {
                mapTiles.get(y).get(x).draw(gc, new Point(x, y));
            }
        }
    }

    public void initMapTiles(ArrayList<String> mapDef) {
        mapTiles = new ArrayList<>();
        for (String row : mapDef) {
            ArrayList<MapTile> newRow = new ArrayList<>();
            for (char c : row.toCharArray()){
                newRow.add(MapTileEnum.values()[Character.getNumericValue(c)].getMapTileInstance());
            }
            mapTiles.add(newRow);
        }
    }
}
