package view.hover;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Rectangle;
import model.cursor.Cursor;
import model.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class HoverInfo<T> {

    private final int fontSize = 14;
    private final int textBuffer = 6;
    private final Font font = new Font("Monospaced", fontSize);
    private Cursor cursor;
    private boolean reflectable;

    abstract ArrayList<String> getInfoKeys(T item);
    abstract HashMap<String, String> getInfoHashMap(T item);

    public HoverInfo(Cursor cursor, boolean reflectable) {
        this.cursor = cursor;
        this.reflectable = reflectable;
    }

    public void showInfo(boolean left, boolean top, double w, double h, GraphicsContext gc, T item) {
        Rectangle infoRect = getInfoRectangle(left, top, w, h, gc, item);
        double xMargin = determineWidth(item) * 0.03;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(infoRect.getX(), infoRect.getY(), infoRect.getWidth(), infoRect.getHeight());

        gc.setLineWidth(3);
        gc.setStroke(Color.GRAY);
        gc.strokeRect(infoRect.getX(), infoRect.getY(), infoRect.getWidth(), infoRect.getHeight());

        gc.setFill(new Color(0, 0, 0, 1));
        gc.setFont(font);
        for (int i = 0; i < getInfoKeys(item).size(); i++) {
            String infoText = getString(i, item);
            gc.fillText(infoText, infoRect.getX() + xMargin, infoRect.getY() + ((i + 1) * (fontSize + textBuffer)), infoRect.getWidth() - (xMargin * 2));
        }
    }

    public Rectangle getInfoRectangle(boolean left, boolean top, double w, double h, GraphicsContext gc, T item) {
        double width = determineWidth(item);
        double height = determineHeight(item);
        double margin = 0.02;
        double leftX = w * margin - gc.getTransform().getTx();
        double rightX = w - width - (w * margin) - gc.getTransform().getTx();
        double topY = h * margin - gc.getTransform().getTy();
        double botY = h - height - (h * margin) - gc.getTransform().getTy();

        Rectangle infoRect = new Rectangle(left ? leftX : rightX, top ? topY : botY, width, height);

        Rectangle cursorRect = new Rectangle(
                cursor.getSelectionPoint().getRealX(),
                cursor.getSelectionPoint().getRealY(),
                Map.Tile_Width,
                Map.Tile_Height
        );

        if (cursorRect.collides(infoRect) && reflectable) {
            infoRect.setX(left ? rightX : leftX);
        }

        return infoRect;
    }

    private String getString(int i, T item) {
        HashMap<String, String> infoMap = getInfoHashMap(item);
        ArrayList<String> infoKeys = getInfoKeys(item);
        return infoKeys.get(i) + ": " + infoMap.get(infoKeys.get(i));
    }

    private double determineHeight(T item) {
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
