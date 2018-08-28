package model;

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

    public boolean equals(Point point){
        return point.getX().equals(x) && point.getY().equals(y);
    }

    public int getTravelDistance(Point point) {
        return Math.abs(point.getX() - x) + Math.abs(point.getY() - y);
    }
}
