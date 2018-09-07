package view.hover;

import model.cursor.Cursor;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitHoverInfo extends HoverInfo<Unit> {
    public UnitHoverInfo(Cursor cursor, boolean left, boolean top) {
        super(cursor, false, left, top);
    }

    @Override
    ArrayList<String> getInfoKeys(Unit item) {
        return null;
    }

    @Override
    HashMap<String, String> getInfoHashMap(Unit item) {
        return null;
    }
}
