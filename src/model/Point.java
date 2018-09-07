package model;

import model.map.Map;
import model.map.MapTile;
import model.map.MapTileEnum;

import java.util.ArrayList;
import java.util.Collection;

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

    public void setX(Integer x) {
        this.x = x;
    }

    public double getRealX() {
        return x * Map.Tile_Width;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public double getRealY() {
        return y * Map.Tile_Height;
    }

    public boolean equals(Point point) {
        return point.getX().equals(x) && point.getY().equals(y);
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

    public boolean inCollection(Collection<Point> points) {
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
