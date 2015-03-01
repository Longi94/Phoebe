package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class TrackObjectBase {
    protected static final double RADIUS = 0.5;
    protected Position pos;
    protected Track track;

    public boolean hit (Position pos) {
        // TODO
        return false;
    }

    public boolean hit (TrackObjectBase otherObject) {
        //TODO
        return false;
    }

    public void collide (Robot r)  {

    }

    public void newRound() {}

}
