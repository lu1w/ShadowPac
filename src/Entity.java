import bagel.Image;

public abstract class Entity extends Element {

    /**
     * Constructor.
     * @param formFile image filename of the appearance of the entity
     * @param x        x-coordinate of the entity in the game window
     * @param y        y-coordinate of the entity in the game window
     */
    public Entity(String formFile, double x, double y) {
        super(new Image(formFile), x, y);
    }
}
