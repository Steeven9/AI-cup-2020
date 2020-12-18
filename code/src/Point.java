/**
 * A class to model a 2D point with an index to identify it.
 * @author Stefano Taillefert
 */
public final class Point {
    public int index;
    public double x;
    public double y;

    public Point(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public static double getDistance(Point a, Point b) {
        return Math.round(Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)));
    }
}
