import bagel.Keys;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a ghost who can move either only horizontally or only vertically.
 */
public class GhostRandom extends MobileGhost {
    private final static String GHOST_FILE = "res/ghostGreen.png";
    private final static double SPEED = 4;

    /**
     * Constructor.
     * @param x initial x-coordinate of the ghost
     * @param y initial y-coordinate of the ghost
     */
    public GhostRandom(double x, double y) {
        super(GHOST_FILE, SPEED, x, y);
        if (ThreadLocalRandom.current().nextInt(2) == 1) {
            setDirection(Keys.RIGHT);
        } else {
            setDirection(Keys.DOWN);
        }
    }
}
