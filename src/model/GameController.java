package model;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static int DEFAULT_TURN_NUMBER = 40;

    public int _turns_left;

    private Robot _myRobot; //kezelt robot;
    private Track _track; //kezelt pálya


    public GameController() {
        //TBD
        _turns_left = DEFAULT_TURN_NUMBER;

    }

    public void endGame() {

    }

    public void newTurn() {
        _turns_left -= 1;
        if (_turns_left == 0) {
            endGame();
        }
    }

}
