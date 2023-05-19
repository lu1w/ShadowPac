import bagel.Keys;
import bagel.util.Point;
import bagel.Image;

/**
 * Represents a character who can move.
 */
public abstract class Character extends Element {

    /* Attributes relating to appearance changing */
    protected final Image VARIATIONAL_FORM;
    private boolean variation = false;

    /* Attributes relating to movements */
    private final double NORMAL_SPEED, FRENZY_SPEED;
    private double speed;
    private Keys direction;
    private boolean mobile = true;

    /**
     * Constructor.
     * @param defaultFormFile   filename of the default appearance image
     * @param variationFormFile filename of the variation appearance image
     * @param speed             normal mode speed of the character
     * @param frenzy_speed      frenzy mode speed of the character
     * @param x                 initial x-coordinator of the character
     * @param y                 initial y-coordinator of the character
     */
    public Character(String defaultFormFile, String variationFormFile,
                     double speed, double frenzy_speed, double x, double y) {
        super(new Image(defaultFormFile), x, y);
        VARIATIONAL_FORM = new Image(variationFormFile);
        NORMAL_SPEED = this.speed = speed;
        FRENZY_SPEED = frenzy_speed;
    }

    /**
     * Move the characters and update corresponding to the move.
     * @param lv current level of the game
     */
    public void moveAndUpdate(Level lv) {
        Point prevPosition = this.topLeft();
        double currX = this.left();
        double currY = this.top();

        /* Move the character based on the current direction and speed */
        if (direction == Keys.RIGHT) {
            currX += speed;
        } else if (direction == Keys.LEFT) {
            currX -= speed;
        } else if (direction == Keys.DOWN) {
            currY += speed;
        } else if (direction == Keys.UP) {
            currY -= speed;
        }
        moveTo(new Point(currX, currY));

        /* Respond to the collisions if any */
        lv.updateCollisions(this);
        checkMobility(prevPosition);
    }

    /**
     * Provide reactions of the character when colliding with an entity.
     * @param entity the entity that the character is colliding into
     */
    public void collidesEntity(Entity entity) {
        if (entity instanceof Wall) {
            mobile = false;
        }
    }

    /**
     * Avoid invalid movement of the character.
     * @param prevPosition the position to return to if collisions happen
     */
    public void checkMobility(Point prevPosition) {
        if (!mobile) {
            moveTo(prevPosition);
            mobile = true;
        }
    }

    /**
     * Getter method.
     * @return whether the character can move
     */
    public boolean isMobile() {
        return mobile;
    }

    /**
     * Switch the form of the character.
     */
    public void switchForm() {
        variation = !variation;
    }

    @Override
    public void draw() {
        if (variation) {
            VARIATIONAL_FORM.drawFromTopLeft(this.left(), this.top());
        } else {
            super.draw();
        }
    }

    /**
     * Setter method.
     * @param variation whether the character is in its variation form
     */
    public void setVariation(boolean variation) {
        this.variation = variation;
    }

    /**
     * Getter method.
     * @return whether the character is in its variation form
     */
    public boolean isVariation() {
        return variation;
    }

    /**
     * Setter method.
     * @param key the new direction of the character
     */
    public void setDirection(Keys key) {
        direction = key;
    }

    /**
     * Getter method.
     * @return the direction of the character
     */
    public Keys getDirection() {
        return direction;
    }

    /**
     * Set the position to the intial position.
     */
    public void toInitialPosition() {
        moveTo(new Point(X, Y));
    }

    /**
     * Set the speed for frenzy mode.
     */
    public void toFrenzySpeed() {
        speed = FRENZY_SPEED;
    }

    /**
     * Set the speed for normal mode.
     */
    public void toNormalSpeed() {
        speed = NORMAL_SPEED;
    }

}
