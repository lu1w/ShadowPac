import bagel.Image;
import bagel.util.Rectangle;

/**
 * Represents the smallest unit of elements appearing in the game.
 */
public abstract class Element extends Rectangle {

    /**
     * The default appearance of the element.
     */
    protected final Image FORM;

    /**
     * The initial position of the element.
     */
    protected final double X, Y;

    private boolean exist = true;

    /**
     * Constructor.
     * @param form appearance
     * @param x    initial x-coordinate
     * @param y    initial y-coordinate
     */
    public Element(Image form, double x, double y) {
        super(x, y, form.getWidth(), form.getHeight());
        FORM = form;
        X = x;
        Y = y;
    }

    /**
     * Render the element.
     */
    public void draw() {
        FORM.drawFromTopLeft(this.left(), this.top());
    }

    /**
     * Set the element as not exist in the game window.
     */
    public void disappear() {
        exist = false;
    }

    /**
     * Set the element as exist in the game window.
     */
    public void appear() {
        exist = true;
    }

    /**
     * Getter method.
     * @return whether the element exists in the game window
     */
    public boolean existing() {
        return exist;
    }

}
