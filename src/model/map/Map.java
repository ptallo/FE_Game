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
        mapDef.add("000000000001000000000000000000000000000000000000100000000021");
        mapDef.add("000000000001000000000000000000000000000000000000100000000021");
        mapDef.add("000000000001000000000000000000000000000000000000100000000021");
        mapDef.add("000000000001000000000000000000000000000000000000100000000021");
        mapDef.add("000000000000000000000000000000000000000000000000000000000021");
        mapDef.add("000000000000000000000000000000000000000000000000000000000021");
        mapDef.add("000000000000000000000000000000000000000000000000000000000021");
        mapDef.add("000000000000000000000000000000000000000000000000000000000021");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("222220000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("222220000000000000000000000000000000000000000000000000000000");
        mapDef.add("000000000000000000000000000000000000000000000000000000000000");
        mapDef.add("111111111110000000000000000000000000000000000000000000000000");
        initMapTiles(mapDef);
    }

    public void draw(GraphicsContext gc){
        for (ArrayList<MapTile> row : mapTiles) {
            for (MapTile tile : row) {
                renderSystem.draw(tile.getRenderComponent(), gc, tile.getPhysicsComponent().getPoint());
            }
        }
    }

    public void initMapTiles(ArrayList<String> mapDef) {
        mapTiles = new ArrayList<>();
        for (int y = 0; y < mapDef.size(); y++){
            ArrayList<MapTile> newRow = new ArrayList<>();
            char[] row = mapDef.get(y).toCharArray();
            for (int x = 0; x < row.length; x++){
                int value = Character.getNumericValue(row[x]);
                newRow.add(MapTileEnum.values()[value].getMapTileInstance(x, y));
            }
            mapTiles.add(newRow);
        }
    }

    public ArrayList<ArrayList<MapTile>> getMapTiles() {
        return mapTiles;
    }

    public ArrayList<MapTile> getMapTilesArray() {
        ArrayList<MapTile> mapTileArray = new ArrayList<>();
        for (ArrayList<MapTile> row : mapTiles) {
            mapTileArray.addAll(row);
        }
        return mapTileArray;
    }

    public MapTile getTileAtPoint(Point point) {
        if (point.getY() >= mapTiles.size() || point.getY() < 0 || point.getX() >= mapTiles.get(0).size() || point.getX() < 0) {
            return null;
        }
        return mapTiles.get(point.getY()).get(point.getX());
    }
}
