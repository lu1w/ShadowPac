import java.util.ArrayList;

/**
 * Represents a level of the game.
 */
public abstract class Level {

    /* Settings of the level */
    private final String INFO_FILE;
    private ArrayList<Entity> entities = new ArrayList<>();
    private int numDots = 0;

    /* Status of the level */
    private boolean lvStarted = false;
    private boolean lvWin = false;
    private boolean lvCompleted = false;
    private static boolean alive = true;

    /* Player of the level */
    private Player player;

    /**
     * Constructor.
     * @param filename name of the file storing elements locations of the level
     */
    public Level(String filename) {
        INFO_FILE = filename;
    }

    /**
     * Store the position of an element of a certain type.
     * @param type the type of the element
     * @param x    the top-left x-coordinate of the element
     * @param y    the top-left y-coordinate of the element
     */
    public void readInfo(String type, double x, double y) {
        switch (type) {
            case "Dot":
                entities.add(new Dot(x, y));
                numDots++;
                break;
            case "Wall":
                entities.add(new Wall(x, y));
                break;
        }
    }

    /**
     * Render all entities within this level.
     */
    public void renderAll () {
        new DrawElements<Entity>().drawAll(entities);
    }

    /**
     * Check for collisions between a character and entities within the level.
     * @param character the character which to check for collisions
     */
    public void updateCollisions(Character character) {
        for (Entity entity : entities) {
            if (character.intersects(entity) && entity.existing()) {
                character.collidesEntity((entity));
                if (character instanceof Player && entity instanceof Dot) {
                    numDots -= 1;
                }
            }
        }
    }

    /**
     * Set level as started.
     */
    public void started() {
        lvStarted = true;
    }

    /**
     * Set level as won.
     */
    public void won() {
        lvWin = true;
    }

    /**
     * Set level as completed.
     */
    public void completed() {
        lvCompleted = true;
    }

    /**
     * Set the level to be lost (which also means the game is lost).
     */
    public static void lost() {
        alive = false;
    }

    /**
     * Setter method of player.
     * @param x x-coordinator of the player
     * @param y y-coordinator of the player
     */
    public void setPlayerAt(double x, double y) {
        player = new Player(x, y);
    }

    /**
     * Getter method.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter method.
     * @return the number of dots that has not been eaten
     */
    public int getNumDots() {
        return numDots;
    }

    /**
     * Getter method.
     * @return the filename of the information file storing locations of all elements
     */
    public String getInfoFile() {
        return INFO_FILE;
    }

    /**
     * Getter method.
     * @return the entities of this level
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Getter method.
     * @return whether the level has been started
     */
    public boolean isLvStarted() {
        return lvStarted;
    }

    /**
     * Getter method.
     * @return whether the level has been won
     */
    public boolean isLvWin() {
        return lvWin;
    }

    /**
     * Getter method.
     * @return whether the level has been completed
     */
    public boolean isLvCompleted() {
        return lvCompleted;
    }

    /**
     * Getter method.
     * @return whether the game is lost
     */
    public static boolean isLost() {
        return !alive;
    }

    /**
     * Getter method.
     * Override this method to return other values when needed.
     * @return whether the game is in Frenzy Mode
     */
    public boolean isFrenzy() {
        return false;
    }

    /**
     * Check if the winning condition of the level is achieved.
     */
    public abstract void checkWin();

}
