package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot extends TrackObjectBase{
    private static int ID_COUNT = 0;
    private static int START_OIL_AMMOUNT = 1;
    private static int START_PUTTY_AMMOUNT = 1;
    private int id;

    private Velocity vel;
    public int oilAmmount; //rendelkezésre álló olajfoltok mennyisége
    public int puttyAmmount; //rendelkezésre álló ragacsfoltok mennyisége


    public Robot (Position pos) {
        oilAmmount = START_OIL_AMMOUNT;
        puttyAmmount = START_PUTTY_AMMOUNT;
        id = ID_COUNT;
        ID_COUNT += 1;
        pos = pos;
        vel = new Velocity();
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
        //TBD
    }


}
