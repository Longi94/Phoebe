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


    public boolean placeOil() {
        return myRobot.putOil();
    }

    public boolean placePutty() {
        return myRobot.putPutty();
    }

    public void move() {}

    public void finishTurn() {}

    public void waitInstructions() {}

    public void forfit() {}

}
