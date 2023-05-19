import bagel.Keys;
import bagel.util.Point;

/**
 * Represent a mobile ghost.
 */
public abstract class MobileGhost extends Character {

    /* Ghost settings in Frenzy Mode */
    private final static String FRENZY_FILE = "res/ghostFrenzy.png";
    private final static double FRENZY_SPEED_REDUCTION = 0.5;

    /**
     * Constructor.
     * @param defaultFormFile default appearance of the ghost
     * @param speed           default speed of the ghost
     * @param x               initial x-coordinate of the ghost
     * @param y               initial y-coordinate of the ghost
     */
    public MobileGhost(String defaultFormFile, double speed,
                       double x, double y) {
        super(defaultFormFile, FRENZY_FILE,
                speed, speed - FRENZY_SPEED_REDUCTION, x, y);
    }

    /**
     * Change the direction of the ghost to
     * the opposite direction of the current direction.
     */
    public void switchDirection() {
        if (getDirection() == Keys.DOWN) {
            setDirection(Keys.UP);
        } else if (getDirection() == Keys.UP) {
            setDirection(Keys.DOWN);
        } else if (getDirection() == Keys.RIGHT) {
            setDirection(Keys.LEFT);
        } else if (getDirection() == Keys.LEFT) {
            setDirection(Keys.RIGHT);
        }
    }

    @Override
    public void draw() {
        if (existing()) {
            super.draw();
        }
    }

    @Override
    public void checkMobility(Point prevPosition) {
        /* If the move is invalid, switch the direction */
        if (!isMobile()) {
            switchDirection();
        }
        super.checkMobility(prevPosition);
    }

}
