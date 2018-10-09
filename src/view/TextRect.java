package view;

import javafx.geometry.HPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;
import util.Rectangle;

import java.util.ArrayList;

public class TextRect {

    private Font font;
    private double margin;
    private double spacing;

    public TextRect(Font font, double margin, double spacing) {
        this.font = font;
        this.margin = margin;
        this.spacing = spacing;
    }

    public double draw(GraphicsContext gc, double x, double y, double maxWidth, HPos hpos, ArrayList<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            gc.setFill(Color.BLACK);
            double yOrigin = y + font.getSize();
            gc.fillText(
                    strings.get(i),
                    x + margin * maxWidth,
                    yOrigin + i * font.getSize() + (i * spacing),
                    maxWidth - 2 * margin * maxWidth
            );
        }
        return getHeight(strings);
    }

    public double getHeight(ArrayList<String> strings) {
        double sizeByFont = strings.size() * font.getSize();
        double sizeBySpacing = strings.size() * spacing;
        return sizeByFont + sizeBySpacing;
    }
}
