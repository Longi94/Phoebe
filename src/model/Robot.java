package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot extends TrackObjectBase{

    private Position _pos;
    private Velocity _vel;

    public Robot (Position pos) {
        _pos = pos;
        _vel = new Velocity();
    }

    public void jump(Velocity v) {
        //TBD
    }

    public boolean putOil() {
        return true;
    }

    public boolean putPutty() {
        return true;
    }

    public void forfit() {

    }


}
