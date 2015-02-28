package model;

/**
 * Created by bence on 2015.02.28..
 */
public class Player {

    private static int ID_COUNT = 0;

    private Robot myRobot;
    private int id;
    private String name;

    public Player() {
        id = ID_COUNT;
        this.name = name;
        ID_COUNT += 1;
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
