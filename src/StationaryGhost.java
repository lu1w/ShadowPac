/**
 * Represents a stationary ghost in the game.
 */
public class StationaryGhost extends Entity {
    private final static String GHOST_FILE = "res/ghostRed.png";

    /**
     * Constructor.
     * @param x x-coordinate of the ghost
     * @param y y-coordinate of the ghost
     */
    public StationaryGhost(double x, double y) {
        super(GHOST_FILE, x, y);
    }
}
