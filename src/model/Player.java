package model;

/**
 * Created by bence on 2015.02.28..
 */
public class Player {

    private static int idCount = 0;

    private Robot myRobot;
    private int id;
    private String name;

    public Player() {
        id = idCount;
        this.name = name;
        idCount += 1;
    }


    public void placeOil() {
        myRobot.putOil();
    }

    public void placePutty() {
        myRobot.putPutty();
    }

    public void move() {}

    public void finishTurn() {}

    public void waitInstructions() {}

    public void forfeit() {}

}
