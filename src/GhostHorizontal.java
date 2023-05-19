import bagel.Keys;

/**
 * Represents a ghost who can move horizontally.
 */
public class GhostHorizontal extends MobileGhost {
    private final static String GHOST_FILE = "res/ghostBlue.png";
    private final static double SPEED = 1;

    /**
     * Constructor.
     * @param x initial x-coordinate of the ghost
     * @param y initial y-coordinate of the ghost
     */
    public GhostHorizontal(double x, double y) {
        super(GHOST_FILE, SPEED, x, y);
        setDirection(Keys.RIGHT);
    }

}
