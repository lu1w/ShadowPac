import bagel.Keys;

/**
 * Represents a ghost who can move vertically.
 */
public class GhostVertical extends MobileGhost {
    private final static String GHOST_FILE = "res/ghostRed.png";
    private final static double SPEED = 2;

    /**
     * Constructor.
     * @param x initial x-coordinate of the ghost
     * @param y initial y-coordinate of the ghost
     */
    public GhostVertical(double x, double y) {
        super(GHOST_FILE, SPEED, x, y);
        setDirection(Keys.DOWN);
    }
}
