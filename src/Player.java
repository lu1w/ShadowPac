import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Keys;

/**
 * Represents a player of the game.
 */
public class Player extends Character {

    /* Appearance of the pacman */
    private final static String PAC_FILE = "res/pac.png";
    private final static String PAC_OPEN_FILE = "res/pacOpen.png";
    private final static byte SWITCH_INTERVAL = 15;
    private byte numFrame = 1;


    /* Scores of the player */
    private final static String SCORE_MESSAGE = "SCORE ";
    private final static int SCORE_SIZE = 24;
    private final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", SCORE_SIZE);
    private final static int SCORE_X = 25;
    private final static int SCORE_Y = 25;
    private int score = 0;


    /* Lives of the player */
    private final static Image HEART = new Image("res/heart.png");
    private final static int HEART_X = 900;
    private final static int HEART_X_INTERVAL = 30;
    private final static int HEART_Y = 10;
    private final static byte MAX_LIVES = 3;
    private byte lives = MAX_LIVES;

    /* Movements of the player */
    private final static double PAC_NORMAL_SPEED = 3;
    private final static double PAC_FRENZY_SPEED = 4;
    private boolean frenzyTrigger = false;

    /* Rotation of the pacman */
    private final static DrawOptions FACE_RIGHT =
            new DrawOptions().setRotation(0);
    private final static DrawOptions FACE_DOWN =
            new DrawOptions().setRotation(0.5 * Math.PI);
    private final static DrawOptions FACE_LEFT =
            new DrawOptions().setRotation(Math.PI);
    private final static DrawOptions FACE_UP =
            new DrawOptions().setRotation(1.5 * Math.PI);
    private DrawOptions rotation = FACE_RIGHT;

    /**
     * Constructor.
     * @param x initial x-coordinate of the player
     * @param y initial y-coordinate of the player
     */
    public Player(double x, double y) {
        super(PAC_FILE, PAC_OPEN_FILE, PAC_NORMAL_SPEED, PAC_FRENZY_SPEED, x, y);
    }

    /**
     * Switch form of the player automatically every time interval.
     */
    @Override
    public void switchForm() {
        if (numFrame % SWITCH_INTERVAL == 0) {
            super.switchForm();
            numFrame = 1;
        } else {
            numFrame++;
        }
    }

    @Override
    public void setDirection(Keys key) {
        super.setDirection(key);

        /* Set the rotation of the pacman */
        if (key == Keys.RIGHT) {
            rotation = FACE_RIGHT;
        } else if (key == Keys.LEFT) {
            rotation = FACE_LEFT;
        } else if (key == Keys.DOWN) {
            rotation = FACE_DOWN;
        } else if (key == Keys.UP) {
            rotation = FACE_UP;
        }
    }

    @Override
    public void collidesEntity(Entity entity) {
        super.collidesEntity(entity);

        if (entity instanceof StationaryGhost) {
            deductLife();
            toInitialPosition();

        } else if (entity instanceof Feeding) {
            scoring(((Feeding) entity).POINTS);
            entity.disappear();
            if (entity instanceof Pellet) {
                /* If player collides with a pellet, trigger frenzy mode */
                frenzyTrigger = true;
            }
        }
    }

    @Override
    public void draw() {
        if (isVariation()) {
            VARIATIONAL_FORM.drawFromTopLeft(this.left(), this.top(), rotation);
        } else {
            FORM.drawFromTopLeft(this.left(), this.top(), rotation);
        }
    }

    /**
     * Getter method.
     * @return the current score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the score of the player.
     * @param points the number of points added to the current score
     */
    public void scoring(int points) {
        score += points;
    }

    /**
     * Deduct one life from the player.
     */
    public void deductLife() {
        lives -= 1;
        if (lives <= 0) {
            Level.lost();
        }
    }

    /**
     * Render the score of the player.
     */
    public void renderScore() {
        SCORE_FONT.drawString(SCORE_MESSAGE + score, SCORE_X, SCORE_Y);
    }

    /**
     * Render the remaining lives of the player.
     */
    public void renderLives() {
        for (int n = 0; n < lives; n++) {
            HEART.draw(HEART_X + n * HEART_X_INTERVAL, HEART_Y);
        }
    }

    /**
     * Getter method.
     * @return whether frenzy mode is triggered
     */
    public boolean triggerFrenzy() {
        return frenzyTrigger;
    }

    /**
     * Frenzy mode trigger no needed anymore.
     */
    public void triggeredFrenzy() {
        frenzyTrigger = false;
    }

    @Override
    public void toInitialPosition() {
        super.toInitialPosition();
        rotation = FACE_RIGHT;
    }


}
