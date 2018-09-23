package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.Point;

import java.util.Collection;
import java.util.List;

public class ActionInfoItem {

    private final int fontSize = 14;
    private final double xMargin = 0.3;
    private final int textBuffer = 6;
    private final Font font = new Font("Monospaced", fontSize);

    private Boolean drawItem = false;
    private int optionIndex = 0;
    private List<? extends String> options;

    public void draw(GraphicsContext gc, Point point) {
        if (options == null) {
            throw new RuntimeException("No options available, please supply options before calling this method");
        }

        draw(gc, point, options);
    }

    public void draw(GraphicsContext gc, Point point, List<? extends String> options) {
        if (drawItem) {
            boolean optionsEqual = true;
            if (this.options != null && options != null) {
                for (int i = 0; i < options.size(); i++) {
                    if (!options.get(i).equalsIgnoreCase(this.options.get(i))) {
                        optionsEqual = false;
                    }
                }
            }

            if (!optionsEqual) {
                optionIndex = 0;
            }
            this.options = options;

            double width = getWidth(options);
            double height = getHeight(options);
            double realX = point.getRealX();
            double realY = point.getRealY();
            double rectWidth = width + (width * xMargin * 2);

            gc.setFill(Color.BEIGE);
            gc.fillRect(
                    realX,
                    realY,
                    rectWidth,
                    height + (textBuffer * options.size())
            );

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3);
            gc.strokeRect(
                    realX,
                    realY,
                    rectWidth,
                    height + (textBuffer * options.size())
            );

            gc.setFill(Color.BLACK);
            for (int i = 0; i < options.size(); i++) {
                gc.fillText(
                        options.get(i),
                        realX + (width * xMargin),
                        realY + (fontSize * (i + 1) + textBuffer * i)
                );
            }

            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(1);
            gc.strokeRect(
                    realX + gc.getLineWidth(),
                    realY + (optionIndex != 0 ? optionIndex * (fontSize + textBuffer) : gc.getLineWidth()),
                    rectWidth - (2 * gc.getLineWidth()),
                    fontSize + textBuffer - (optionIndex == 0 ? gc.getLineWidth() : 0)
            );
        }
    }

    private double getWidth(Collection<? extends String> strings) {
        double maxWidth = 0;
        for (String str : strings) {
            Text text = new Text(str);
            text.setFont(font);
            double width = text.getLayoutBounds().getWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    private double getHeight(Collection<? extends String> strings) {
        return fontSize * strings.size();
    }

    public void changeOption(int optionChange) {
        int newOption = optionIndex + optionChange;
        if (newOption < 0) {
            newOption = 0;
        } else if (newOption > this.options.size() - 1) {
            newOption = this.options.size() - 1;
        }
        optionIndex = newOption;
    }

    public Boolean getDrawItem() {
        return drawItem;
    }

    public void setDrawItem(Boolean drawItem) {
        if (drawItem) {
            this.optionIndex = 0;
        }
        this.drawItem = drawItem;
    }
}
