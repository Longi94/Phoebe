package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot extends TrackObjectBase{
    private static int ID_COUNT = 0;
    private int _id;

    private Velocity _vel;

    public Robot (Position pos) {
        _id = ID_COUNT;
        ID_COUNT += 1;
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
