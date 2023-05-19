import java.util.ArrayList;

/**
 * Easier level of the game;
 * enemies are stationary.
 */
public class LevelEasy extends Level {
    private ArrayList<StationaryGhost> ghosts = new ArrayList<>();

    /**
     * Constructor.
     * @param filename name of the file storing elements locations of the level
     */
    public LevelEasy(String filename) {
        super(filename);
    }

    @Override
    public void readInfo(String type, double x, double y) {
        super.readInfo(type, x, y);
        switch (type) {
            case "Ghost":
                getEntities().add(new StationaryGhost(x, y));
                break;
        }
    }

    @Override
    public void checkWin() {
        if (getNumDots() <= 0) {
            won();
        }
    }

}
