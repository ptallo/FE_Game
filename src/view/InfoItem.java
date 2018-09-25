package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.cursor.Cursor;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class InfoItem {

    private final int fontSize = 14;
    private final int textBuffer = 6;
    private final Font font = new Font("Monospaced", fontSize);

    public double showInfo(Point point, GraphicsContext gc, HashMap<String, String> infoMap) {
        return showInfo(point.getRealX(), point.getRealY(), gc, infoMap);
    }

    public double showInfo(double x, double y, GraphicsContext gc, HashMap<String, String> infoMap) {
        Rectangle infoRect = getInfoRectangle(x, y, infoMap);
        double xMargin = infoRect.getWidth() * 0.1;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(infoRect.getX(), infoRect.getY(), infoRect.getWidth(), infoRect.getHeight());

        gc.setLineWidth(3);
        gc.setStroke(Color.GRAY);
        gc.strokeRect(infoRect.getX(), infoRect.getY(), infoRect.getWidth(), infoRect.getHeight());

        gc.setFill(new Color(0, 0, 0, 1));
        gc.setFont(font);


        Set<String> keys = infoMap.keySet();
        int i = 0;
        for (String key : keys) {
            String infoText = key + ": " + infoMap.get(key);
            gc.fillText(infoText, infoRect.getX() + xMargin, infoRect.getY() + ((i + 1) * (fontSize + textBuffer)), infoRect.getWidth() - (xMargin * 2));
            i++;
        }

        return infoRect.getHeight();
    }

    private Rectangle getInfoRectangle(double x, double y, HashMap<String, String> infoMap) {
        return new Rectangle(x, y, determineWidth(infoMap), determineHeight(infoMap));
    }

    private double determineHeight(HashMap<String, String> infoMap) {
        return (fontSize * infoMap.size()) + (textBuffer * (infoMap.size() + 1));
    }

    private double determineWidth(HashMap<String, String> infoMap) {
        ArrayList<String> strings = new ArrayList<>();

        Set<String> keys = infoMap.keySet();
        for (String key : keys) {
            strings.add(key + ": " + infoMap.get(key));
        }

        double maxWidth = 0;
        for (String str : strings){
            Text text = new Text(str);
            text.setFont(font);
            double width = text.getLayoutBounds().getWidth();
            if (width > maxWidth){
                maxWidth = width;
            }
        }

        return maxWidth;
    }
}
