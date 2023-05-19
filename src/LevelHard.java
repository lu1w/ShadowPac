import java.util.ArrayList;

/**
 * Harder level of the game;
 * enemies are mobile and contains frenzy mode.
 */
public class LevelHard extends Level {

    /* Store the elements within this level */
    private ArrayList<MobileGhost> mobileGhosts = new ArrayList<>();

    /* Frenzy Mode status */
    private boolean frenzyMode = false;
    private int frenzyModeCount = 0;
    private final static int FRENZY_TIME = 1000;
    private final static int FRENZY_GHOST_POINTS = 30;

    /* Winning condition */
    private final static int WINNING_SCORE = 800;

    /**
     * Constructor.
     * @param filename name of the file storing elements locations of the level
     */
    public LevelHard(String filename) {
        super(filename);
    }

    @Override
    public void readInfo(String type, double x, double y) {
        super.readInfo(type, x, y);
        switch (type) {
            case "Cherry":
                getEntities().add(new Cherry(x, y));
                break;
            case "Pellet":
                getEntities().add(new Pellet(x, y));
                break;
            case "GhostRed":
                mobileGhosts.add(new GhostHorizontal(x, y));
                break;
            case "GhostBlue":
                mobileGhosts.add(new GhostVertical(x, y));
                break;
            case "GhostGreen":
                mobileGhosts.add(new GhostRandom(x, y));
                break;
            case "GhostPink":
                mobileGhosts.add(new GhostAdvanced(x, y));
                break;
        }
    }

    @Override
    public void renderAll() {
        super.renderAll();
        for (MobileGhost ghost : mobileGhosts) {
            /* Perform the movements for all the mobile ghosts */
            ghost.moveAndUpdate(this);
        }
        new DrawElements<MobileGhost>().drawAll(mobileGhosts);

    }

    /**
     * Update the game mode (frenzy mode or normal mode).
     * @param player player of the game
     */
    public void updateGameMode(Player player) {
        if (player.triggerFrenzy()) {
            toFrenzy(player);
        }
        if (isFrenzy()) {
            frenzyModeCount += 1;
        }
        if (frenzyModeCount == FRENZY_TIME) {
            toNotFrenzy(player);
            frenzyModeCount = 0;
        }
    }

    @Override
    public void updateCollisions(Character character) {
        super.updateCollisions(character);
        if (character instanceof Player) {
            for (MobileGhost ghost : mobileGhosts) {
                if (character.intersects(ghost) && ghost.existing()) {
                    /* If the player collides with an existing ghost,
                    *  update corresponding to the current game mode */
                    if (frenzyMode) {
                        ((Player) character).scoring(FRENZY_GHOST_POINTS);
                        ghost.disappear();
                    } else {
                        ((Player) character).deductLife();
                        character.toInitialPosition();
                    }
                }
            }
        }
    }

    /**
     * Change the level to frenzy mode.
     * @param player player of the level
     */
    public void toFrenzy(Player player) {
        frenzyMode = true;
        player.toFrenzySpeed();
        for (MobileGhost ghost : mobileGhosts) {
            ghost.setVariation(true);
            ghost.toFrenzySpeed();
        }
        player.triggeredFrenzy();
    }

    /**
     * Change the level to normal (not frenzy) mode.
     * @param player
     */
    public void toNotFrenzy(Player player) {
        frenzyMode = false;
        player.toNormalSpeed();
        for (MobileGhost ghost : mobileGhosts) {
            ghost.setVariation(false);
            ghost.toNormalSpeed();
            if (!ghost.existing()) {
                ghost.toInitialPosition();
                ghost.appear();
            }
        }
    }

    @Override
    public boolean isFrenzy() {
        return frenzyMode;
    }

    @Override
    public void checkWin() {
        if (this.getPlayer().getScore() == WINNING_SCORE) {
            won();
        }
    }

}
