/**
 * Represents the feeding to the player (that can be eaten by the player).
 */
public abstract class Feeding extends Entity {

    /**
     * The number of points that the feeding is worth.
     */
    public final int POINTS;

    /**
     * Constructor.
     * @param formFile image filename of the appearance of the entity
     * @param points   the number of points that the feeding is worth
     * @param x        x-coordinate of the feeding in the game window
     * @param y        y-coordinate of the feeding in the game window
     */
    public Feeding(String formFile, int points, double x, double y) {
        super(formFile, x, y);
        POINTS = points;
    }

    /**
     * Constructor of a feeding that worth no points.
     * @param formFile image filename of the appearance of the entity
     * @param x        x-coordinate of the feeding in the game window
     * @param y        y-coordinate of the feeding in the game window
     */
    public Feeding(String formFile, double x, double y) {
        super(formFile, x, y);
        POINTS = 0;
    }

    @Override
    public void draw() {
        if (existing()) {
            super.draw();
        }
    }

}
