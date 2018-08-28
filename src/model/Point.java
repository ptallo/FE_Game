package model;

import java.util.ArrayList;

public class Point {

    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public boolean equals(Point point) {
        return point.getX().equals(x) && point.getY().equals(y);
    }

    public int getTravelDistance(Point point) {
        return Math.abs(point.getX() - x) + Math.abs(point.getY() - y);
    }

    public ArrayList<Point> getNeighbors() {
        ArrayList<Point> neighbors = new ArrayList<>();
        Point leftNeighbor = new Point(x - 1, y);
        neighbors.add(leftNeighbor);

        Point rightNeighbor = new Point(x + 1, y);
        neighbors.add(rightNeighbor);

        Point topNeighbor = new Point(x, y - 1);
        neighbors.add(topNeighbor);

        Point botNeighbor = new Point(x, y + 1);
        neighbors.add(botNeighbor);

        return neighbors;
    }

    public boolean inArray(ArrayList<Point> points) {
        for (Point point : points) {
            if (point.equals(this)) {
                return true;
            }
        }
        return false;
    }

    public Point clone() {
        return new Point(x, y);
    }
}
