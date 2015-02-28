package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public class Pickup extends TrackObjectBase {

    public Pickup (Position pos) {

    }

    @Override
    public void collide(Robot r) {
        //sorsol és ad a robotnak egy akadályt
        //kitörli magát a pályáról
    }

}
