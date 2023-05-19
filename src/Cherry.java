/**
 * Represents a cherry worth 20 points.
 */
public class Cherry extends Feeding {

    private final static String CHERRY_FILE = "res/cherry.png";
    private final static int POINTS = 20;

    /**
     * Constructor.
     * @param x x-coordinate of the cherry
     * @param y y-coordinate of the cherry
     */
    public Cherry(double x, double y) {
        super(CHERRY_FILE, POINTS, x, y);
    }
}
