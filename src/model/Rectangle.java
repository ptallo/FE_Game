package model;

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
