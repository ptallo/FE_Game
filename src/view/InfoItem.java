package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.map.Map;
import util.Point;
import util.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class InfoItem {

    private double yMargin = 0.3;
    private double xMargin = 0.1;
    private Font font = new Font("Monospaced", 14);

    public void draw(GraphicsContext gc, double w, double h, HashMap<String, String> info) {
        ArrayList<String> items = getUniformList(info);
        double width = Collections.max(items.stream().map(this::getWidth).collect(Collectors.toList()));
        double marginWidth = width * xMargin;
        double height = font.getSize() * items.size();
        double marginHeight = height * yMargin;

        double distanceFromEdge = Map.Tile_Height;

        double xDrawPosition = distanceFromEdge - gc.getTransform().getTx();
        double yDrawPosition = h - distanceFromEdge - height - (marginHeight * 2) - gc.getTransform().getTy();

        gc.setFill(Color.BEIGE);
        gc.fillRect(
                xDrawPosition,
                yDrawPosition,
                width + (marginWidth * 2),
                height + (marginHeight * 2)
        );

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(
                xDrawPosition,
                yDrawPosition,
                width + (marginWidth * 2),
                height + (marginHeight * 2)
        );

        for (int i = 0; i < items.size(); i++) {
            gc.setFill(Color.BLACK);
            gc.setFont(font);
            gc.fillText(items.get(i), xDrawPosition + marginWidth, yDrawPosition + font.getSize() * (i + 1) + marginHeight);
        }
    }

    private ArrayList<String> getUniformList(HashMap<String, String> info) {
        double maxWidth = 0;
        for (String keyString : info.keySet()) {
            String fullString = keyString.concat(":").concat(info.get(keyString));
            double width = getWidth(fullString);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        final double width = maxWidth;
        return info.keySet().stream().map(key -> getString(info, key, width)).collect(Collectors.toCollection(ArrayList::new));
    }

    private String getString(HashMap<String, String> info, String key, double width) {
        String tempString = key.concat(": ").concat(info.get(key));
        while (getWidth(tempString) < width) {
            tempString = tempString.substring(0, key.length() + 1) +
                    " " +
                    tempString.substring(key.length() + 1);
        }
        return tempString;
    }

    private double getWidth(String string) {
        Text text = new Text(string);
        text.setFont(font);
        return text.getLayoutBounds().getWidth();
    }

    private double getHeight(Collection<? extends String> strings) {
        return font.getSize() * strings.size();
    }
}
