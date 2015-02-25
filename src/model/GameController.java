package model;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static int DEFAULT_TURN_NUMBER = 40;
    public static int TIME_TO_MOVE = 30; // in seconds

    public int turnsLeft;

    private Robot myRobot; //kezelt robot;
    private Track track; //kezelt pálya


    public GameController() {
        //TODO
        turnsLeft = DEFAULT_TURN_NUMBER;

    }

    public void initGame() {

    }

    public void reciveDataFromServer(String stream) {
        //feldolgozás
        newTurn();
    }

    public boolean placeOil() {
        return myRobot.putOil();
    }

    public boolean placePutty() {
        return myRobot.putPutty();
    }

    public void newTurn() {
        turnsLeft -= 1;
        if (turnsLeft == 0) {
            endGame();
        }
        //do stuff and wait
        String stream = new String();
        sendDataToServer(stream);
    }

    public void sendDataToServer(String stream) {

    }

    public void endGame() {

    }


}
