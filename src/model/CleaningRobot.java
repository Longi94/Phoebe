package model;

import model.basic.Position;

/**
 * Created by bence on 2015.03.27..
 */
public class CleaningRobot extends TrackObjectBase {

    private static final int TURNS = 4;             //4 körig marad életben

    private int turnsLeft;

    public CleaningRobot(Position pos) {
        super(pos);
        turnsLeft = TURNS;
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
        turnsLeft--;
        if (turnsLeft == 0) {
            track.removeObject(this);           //azonnal kampec neki
        }
    }

}
