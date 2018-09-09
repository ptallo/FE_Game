package view.hover;

import model.cursor.Cursor;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UnitHoverInfo extends HoverInfo<Unit> {
    public UnitHoverInfo(Cursor cursor, boolean left, boolean top) {
        super(cursor, false, left, top);
    }

    @Override
    ArrayList<String> getInfoKeys(Unit item) {
        ArrayList<String> nameArrayList = new ArrayList<>();
        nameArrayList.add("Tile Name");
        nameArrayList.add("Total Health");
        nameArrayList.add("Current Health");
        nameArrayList.add("Range");
        nameArrayList.add("Damage");
        return nameArrayList;
    }

    @Override
    HashMap<String, String> getInfoHashMap(Unit item) {
        HashMap<String, String> infoHashMap = new HashMap<>();
        infoHashMap.put("Tile Name", item.getName());
        infoHashMap.put("Total Health", String.valueOf(item.getCombatComponent().getHealthCap()));
        infoHashMap.put("Current Health", String.valueOf(item.getCombatComponent().getCurrentHealth()));

        String ranges = getRangesStRing(item);
        infoHashMap.put("Range", ranges);
        infoHashMap.put("Damage", String.valueOf(item.getCombatComponent().getWeapon().getDamage()));
        return infoHashMap;
    }

    private String getRangesStRing(Unit item) {
        String ranges = "";
        for (int i = 0; i < item.getCombatComponent().getWeapon().getRanges().length; i++) {
            String rangeItem = String.valueOf(item.getCombatComponent().getWeapon().getRanges()[i]);
            if (i == 0) {
                ranges = rangeItem;
            } else {
                ranges = ", " + rangeItem;
            }
        }
        return ranges;
    }
}
