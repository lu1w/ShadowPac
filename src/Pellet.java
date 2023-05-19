/**
 * Represent a pellet.
 */
public class Pellet extends Feeding {
    private final static String PELLET_FILE = "res/pellet.png";

    /**
     * Constructor.
     * @param x x-coordinate of the pellet
     * @param y y-coordinate of the pellet
     */
    public Pellet(double x, double y) {
        super(PELLET_FILE, x, y);
    }
}
