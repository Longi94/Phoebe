package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot {


    private static double ROBOT_RADIUS = 0.5;

    private Position _pos;
    private Velocity _vel;

    public Robot (double x,double y) {
        _pos = new Position(x,y);
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
