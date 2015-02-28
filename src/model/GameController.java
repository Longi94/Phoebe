package model;

import java.util.List;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static final int DEFAULT_TURN_NUMBER = 40;
    public static final int TIME_TO_MOVE = 30; // in seconds

    public int turnsLeft;

    private Track track; //kezelt pálya
    private List<Player> players; //játékosok


    public GameController() {
        //TODO
        turnsLeft = DEFAULT_TURN_NUMBER;

    }

    public void initGame() {

    }

    public void newTurn() {
        turnsLeft -= 1;
        if (turnsLeft == 0) {
            endGame();
        }
    }

    public void nextPlayer() {

    }


    public void endGame() {

    }


}
