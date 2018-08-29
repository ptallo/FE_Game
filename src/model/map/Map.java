package model.map;

import components.render.RenderSystem;
import javafx.scene.canvas.GraphicsContext;
import model.Point;

import java.util.ArrayList;

public class Map {

    private RenderSystem renderSystem = new RenderSystem();
    public static final double Tile_Width = 32;
    public static final double Tile_Height = 32;

    public ArrayList<ArrayList<MapTile>> mapTiles;

    public Map() {
        ArrayList<String> mapDef = new ArrayList<>();
        mapDef.add("0000000000");
        mapDef.add("0000111000");
        mapDef.add("0000100000");
        mapDef.add("0000101000");
        mapDef.add("1000000000");
        mapDef.add("1100000000");
        mapDef.add("0110000000");
        mapDef.add("0010000000");
        mapDef.add("0000000000");
        mapDef.add("0000000000");
        initMapTiles(mapDef);
    }

    public void draw(GraphicsContext gc){
        for (int y = 0; y < mapTiles.size(); y++) {
            for (int x = 0; x < mapTiles.get(y).size(); x++) {
                renderSystem.draw(mapTiles.get(y).get(x).getRenderComponent(), gc, new Point(x, y));
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

    public ArrayList<ArrayList<MapTile>> getMapTiles() {
        return mapTiles;
    }

    public MapTile getTileAtPoint(Point point) {
        if (point.getY() >= mapTiles.size() || point.getY() < 0 || point.getX() >= mapTiles.get(0).size() || point.getX() < 0) {
            return null;
        }
        return mapTiles.get(point.getY()).get(point.getX());
    }
}
