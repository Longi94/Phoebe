package model;

import model.basic.Position;

/**
 * Created by bence on 2015.03.27..
 */
public class CleaningRobot extends TrackObjectBase {

    private static final int CLEAN_TURNS = 2;             //2 körig takarítja a foltot

    private Obstacle actuallyCleaning;                   // az akadály, amit éppen takarít

    private int cleanTurnsLeft;

    public int getCleanTurnsLeft() {
        return cleanTurnsLeft;
    }

    public Obstacle getActuallyCleaning() {
        return actuallyCleaning;
    }

    public void setActuallyCleaning(Obstacle actuallyCleaning) {
        this.actuallyCleaning = actuallyCleaning;
    }

    public void setCleanTurnsLeft(int cleanTurnsLeft) {
        this.cleanTurnsLeft = cleanTurnsLeft;
    }

    public CleaningRobot(Position pos) {
        super(pos);
        actuallyCleaning = null;                        //nem takarít semmit
    }

    @Override
    public void collide(Robot r) {
        //TODO a kis robot megsemmisül, helyette olajfolt keletkezik
    }

    @Override
    public void collide(CleaningRobot cr) {
        //TODO a cleaningRobot irányt vált
    }

    @Override
    public void newRound(){
        if (--cleanTurnsLeft == 0) {
            track.removeObject(actuallyCleaning);
            actuallyCleaning = null;
        }
    }

}
