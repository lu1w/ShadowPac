/**
 * Represents a dot worth 10 points.
 */
public class Dot extends Feeding {
    private final static String DOT_FILE = "res/dot.png";
    private final static int POINTS = 10;

    /**
     * Constructor.
     * @param x x-coordinate of the dot
     * @param y y-coordinate of the dot
     */
    public Dot(double x, double y) {
        super(DOT_FILE, POINTS, x, y);
    }

}
