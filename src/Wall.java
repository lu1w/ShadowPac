/**
 * Represents a wall in the game.
 */
public class Wall extends Entity {
    private final static String WALL_FILE = "res/wall.png";

    /**
     * Constructor.
     * @param x x-coordinate of the wall
     * @param y y-coordinate of the wall
     */
    public Wall(double x, double y) {
        super(WALL_FILE, x, y);
    }
}
