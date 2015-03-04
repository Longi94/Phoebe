package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public class Pickup extends TrackObjectBase {

    /**
     * Konstruktor
     * @param pos az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Pickup (Position pos,Track track) {
        super(pos,track);
    }

    /**
     * Akkor hívódik meg, ha egy robot felszedi (ütközik vele)
     * @param r a robot, ami rá ugrott
     */
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
