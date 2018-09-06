package view.hover;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class HoverInfo<T> {

    abstract ArrayList<String> getInfoKeys(T item);
    abstract HashMap<String, String> getInfoHashMap(T item);

    public void showInfo(double x, double y, GraphicsContext gc, T item) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, 100, 50);

        HashMap<String, String> infoMap = getInfoHashMap(item);
        ArrayList<String> infoKeys = getInfoKeys(item);

        gc.setFill(Color.LIGHTGREY);
        for (int i = 0; i < infoKeys.size(); i++) {
            String infoText = infoKeys.get(i) + ": " + infoMap.get(infoKeys.get(i));
            gc.fillText(infoText, x + 3, y + (i * 10), 94);
        }
    }
}
