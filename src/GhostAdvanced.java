import bagel.Keys;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a ghost who can move in all four directions.
 */
public class GhostAdvanced extends MobileGhost {
    private final static String GHOST_FILE = "res/ghostPink.png";
    private final static double SPEED = 3;

    /**
     * Constructor.
     * @param x initial x-coordinate of the ghost
     * @param y initial y-coordinate of the ghost
     */
    public GhostAdvanced(double x, double y) {
        super(GHOST_FILE, SPEED, x, y);
        setDirection(randomDirection());
    }

    /**
     * Set the ghost to a random direction.
     */
    @Override
    public void switchDirection() {
        setDirection(randomDirection());
    }

    /**
     * Generate a random direction.
     * @return a random direction out of the four directions
     */
    public static Keys randomDirection() {
        int randomInt = ThreadLocalRandom.current().nextInt(4);
        switch (randomInt) {
            case 0:
                return Keys.RIGHT;
            case 1:
                return Keys.LEFT;
            case 2:
                return Keys.DOWN;
            case 3:
                return Keys.UP;
        }
        return null;
    }

}
