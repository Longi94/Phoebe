package model;

import model.basic.Position;

import java.util.Random;

/**
 * Created by bence on 2015.02.23..
 */
public class Pickup extends TrackObjectBase {

    private static Random random = new Random();

    /**
     * Konstruktor
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Pickup(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * Akkor hívódik meg, ha egy robot felszedi (ütközik vele)
     *
     * @param r a robot, ami rá ugrott
     */
    @Override
    public void collide(Robot r) {
        //Random.nextInt(n) is both more efficient and less biased than Math.random() * n
        if (random.nextInt(2) == 1) {
            r.addOil();
        } else {
            r.addPutty();
        }
        //kitörli magát a pályáról
        track.removeObject(this);
    }

    @Override
    public String toString() {
        return "Pickup{" + super.toString() + "}";
    }
}
