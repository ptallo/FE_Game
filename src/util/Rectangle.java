package util;

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
}
