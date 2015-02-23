package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class TrackObjectBase {
    protected static double RADIUS = 0.5;
    protected Position _pos;

    public boolean hit (Position pos) {
        // TBD
        return false;
    }

    public boolean hit (TrackObjectBase otherObject) {
        //TBD
        return false;
    }

}
