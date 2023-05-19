import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * SWEN20003 Project 2, Semester 1, 2023
 * @author Yilu Wang
 * @version 1.0
 */
public class ShadowPac extends AbstractGame  {
    /* Information about the window display */
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    /* Information about the game message */
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String LV0_INTRO =
            "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final static String LV1_INTRO =
            "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE\nEAT THE PELLET TO ATTACK";
    private final static String LEVEL_DONE = "LEVEL COMPLETE!";
    private final static String WIN = "WELL DONE!";
    private final static String LOSE = "GAME OVER!";
    private final static int LEVEL_DONE_DURATION = 300;
    private int levelDoneDuration = 0;

    /* Set fonts for texts */
    private final static int HEADING_SIZE = 64;
    private final static int LV0_INTRO_SIZE = 24;
    private final static int LV1_INTRO_SIZE = 40;
    private final Font HEADING =
            new Font("res/FSO8BITR.TTF", HEADING_SIZE);
    private final Font LV0_INTRO_TEXT =
            new Font("res/FSO8BITR.TTF", LV0_INTRO_SIZE);
    private final Font LV1_INTRO_TEXT =
            new Font("res/FSO8BITR.TTF", LV1_INTRO_SIZE);

    /* Coordinates */
    private final static double TITLE_X = 260;
    private final static double TITLE_Y = 250;
    private final static double INTRO_X = TITLE_X + 60;
    private final static double INTRO_Y = TITLE_Y + 190 + LV0_INTRO_SIZE;
    private final static double LV_INTRO_X = 200;
    private final static double LV_INTRO_Y = 250 + 2 * LV1_INTRO_SIZE;

    /* Level information of the game */
    private final static String LEVEL0 = "res/level0.csv";
    private final static String LEVEL1 = "res/level1.csv";
    private LevelEasy lv0 = new LevelEasy(LEVEL0);
    private LevelHard lv1 = new LevelHard(LEVEL1);
    private final static String INFO_SEPARATOR = ",";

    /**
     * Constructor.
     */
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create elements of the game.
     */
    private void readCSV(Level lv) {
        try (BufferedReader br =
                     new BufferedReader(new FileReader(lv.getInfoFile()))) {
            String line;
            String[] lineInfo;
            String elementType;
            double x, y;

            while((line = br.readLine()) != null) {
                lineInfo = line.split(INFO_SEPARATOR);
                elementType = lineInfo[0];
                x = Double.parseDouble(lineInfo[1]);
                y = Double.parseDouble(lineInfo[2]);

                if (elementType.equals("Player")) {
                    lv.setPlayerAt(x, y);
                } else {
                    lv.readInfo(elementType, x, y);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * @param input input from the user
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        /* If the user starts a level, read the information of the level */
        if (input.wasPressed(Keys.SPACE)) {
            if (!lv0.isLvStarted()) {
                readCSV(lv0);
                lv0.started();
            } else if (lv0.isLvCompleted() && !lv1.isLvStarted()) {
                readCSV(lv1);
                lv1.started();
            }
        }

        /* If lost, render the losing message; otherwise the game continues */
        if (Level.isLost()) {
            renderHeadingAtCentre(LOSE);
        } else {
            renderGame(input);
        }
    }

    private void renderGame(Input input) {
        if (!lv0.isLvStarted()) {
            /* Render the title page before game starts */
            HEADING.drawString(GAME_TITLE,TITLE_X, TITLE_Y);
            LV0_INTRO_TEXT.drawString(LV0_INTRO, INTRO_X, INTRO_Y);

        } else if (!lv0.isLvWin()) {
            /* Render Level 0 */
            processLevel(input, lv0);

        } else if (!lv0.isLvCompleted()) {
            /* Render winning message of level 0 */
            if (levelDoneDuration < LEVEL_DONE_DURATION) {
                renderHeadingAtCentre(LEVEL_DONE);
                levelDoneDuration++;
            } else {
                lv0.completed();
            }

        } else if (!lv1.isLvStarted()) {
            LV1_INTRO_TEXT.drawString(LV1_INTRO, LV_INTRO_X, LV_INTRO_Y);

        } else if (!lv1.isLvWin()) {
            /* Render Level 1 */
            processLevel(input, lv1);

        } else {
            /* Render the winning message */
            renderHeadingAtCentre(WIN);
        }
    }

    private void processLevel(Input input, Level lv) {
        lv.renderAll();
        renderPlayerStatus(input, lv.getPlayer(), lv);
        lv.checkWin();

        /* Checking for winning condition without actually win the game */
        if (input.wasPressed(Keys.W)) {
            lv.won();
            lv.completed();
        }
    }

    private void renderPlayerStatus(Input input, Player player, Level lv) {
        /* Update */
        operatePlayer(input, player, lv);
        player.switchForm();

        /* Render */
        player.draw();
        player.renderLives();
        player.renderScore();
    }

    private void operatePlayer(Input input, Player player, Level lv) {
        Keys forwardDirection = getDirection(input);
        player.setDirection(forwardDirection);
        player.moveAndUpdate(lv);
        if (lv instanceof LevelHard) {
            ((LevelHard) lv).updateGameMode(player);
        }
    }

    /**
     * Get the direction if user has pressed down an arrow key.
     * @param input input from keyboard
     * @return      the arrow key pressed down
     */
    public static Keys getDirection(Input input) {
        if (input.isDown(Keys.RIGHT)) {
            return Keys.RIGHT;
        } else if (input.isDown(Keys.LEFT)) {
            return Keys.LEFT;
        } else if (input.isDown(Keys.UP)) {
            return Keys.UP;
        } else if (input.isDown(Keys.DOWN)) {
            return Keys.DOWN;
        }
        return null;
    }

    /**
     * Renders a message using HEADING font at the centre of the window.
     * @param message text message to be rendered
     */
    private void renderHeadingAtCentre(String message) {
        HEADING.drawString(message,
                (Window.getWidth() - HEADING.getWidth(message))/2.0,
                (Window.getHeight() - HEADING_SIZE)/2.0);
    }

}
