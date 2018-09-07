package view.hover;

import model.cursor.Cursor;
import model.map.MapTile;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTileHoverInfo extends HoverInfo<MapTile>{
    public MapTileHoverInfo(Cursor cursor) {
        super(cursor, true);
    }

    @Override
    ArrayList<String> getInfoKeys(MapTile item) {
        ArrayList<String> nameArrayList = new ArrayList<>();
        nameArrayList.add("Tile Name");
        nameArrayList.add("Travel Cost");
        return nameArrayList;
    }

    @Override
    HashMap<String, String> getInfoHashMap(MapTile item) {
        HashMap<String, String> infoHashMap = new HashMap<>();
        infoHashMap.put("Tile Name", item.getName());
        infoHashMap.put("Travel Cost", String.valueOf(item.getTravelCost()));
        return infoHashMap;
    }
}
