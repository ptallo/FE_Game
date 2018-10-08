package util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rectangle {

    private double x;
    private double y;
    private double width;
    private double height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean equals(Rectangle rectangle) {
        return x == rectangle.getX() &&
                y == rectangle.getY() &&
                width == rectangle.getWidth() &&
                height == rectangle.getHeight();
    }

    public boolean collides(Rectangle rectangle) {
        return x < rectangle.getWidth() + rectangle.getX() &&
                x + width > rectangle.getX() &&
                y < rectangle.getY() + rectangle.getHeight() &&
                y + height > rectangle.getY();
    }

    public void draw(GraphicsContext gc, Color color, boolean strokeRect) {
        if (strokeRect) {
            gc.setStroke(color);
            gc.strokeRect(x, y, width, height);
        } else {
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
        }
    }
}
