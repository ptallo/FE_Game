package view.hover;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class HoverInfo<T> {

    private final int fontSize = 14;
    private final int textBuffer = 6;
    private final Font font = new Font("Arial", fontSize);

    abstract ArrayList<String> getInfoKeys(T item);
    abstract HashMap<String, String> getInfoHashMap(T item);

    public void showInfo(double x, double y, GraphicsContext gc, T item) {
        double width = determineWidth(item);
        double height = determinHeight(item);
        double xMargin = determineWidth(item) * 0.03;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y, width, height);

        gc.setLineWidth(3);
        gc.setStroke(Color.GRAY);
        gc.strokeRect(x, y, width, height);

        gc.setFill(new Color(0, 0, 0, 1));
        gc.setFont(font);
        for (int i = 0; i < getInfoKeys(item).size(); i++) {
            String infoText = getString(i, item);
            gc.fillText(infoText, x + xMargin, y + ((i + 1) * (fontSize + textBuffer)), width - (xMargin * 2));
        }
    }

    private String getString(int i, T item) {
        HashMap<String, String> infoMap = getInfoHashMap(item);
        ArrayList<String> infoKeys = getInfoKeys(item);
        return infoKeys.get(i) + ": " + infoMap.get(infoKeys.get(i));
    }

    private double determinHeight(T item) {
        int rows = getInfoKeys(item).size();
        return (fontSize * rows) + (textBuffer * (rows + 1));
    }

    private double determineWidth(T item) {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> keys = getInfoKeys(item);

        for (int i = 0; i < keys.size(); i++) {
            strings.add(getString(i, item));
        }

        double maxWidth = 0;
        for (String str : strings){
            double width = getWidth(str);
            if (width > maxWidth){
                maxWidth = width;
            }
        }

        return maxWidth;
    }

    public double getWidth(String string){
        Text text = new Text(string);
        text.setFont(font);
        return text.getLayoutBounds().getWidth();
    }
}
