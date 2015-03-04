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
        if ( (Math.random()% 2) == 1) {
            r.addOil();
        } else {
            r.addPutty();
        }
        //kitörli magát a pályáról
        track.removeObject(this);
    }

}
