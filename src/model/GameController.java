package model;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static int DEFAULT_TURN_NUMBER = 40;

    public int turnsLeft;

    private Robot myRobot; //kezelt robot;
    private Track track; //kezelt pálya


    public GameController() {
        //TBD
        turnsLeft = DEFAULT_TURN_NUMBER;

    }

    public void endGame() {

    }

    public void newTurn() {
        turnsLeft -= 1;
        if (turnsLeft == 0) {
            endGame();
        }
    }

}
