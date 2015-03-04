package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public class Putty extends Obstacle {

    /**
     * Konstruktor
     * @param pos a ragacs pozíciója
     * @param track a pálya, amin a ragacs található
     */
    public Putty(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * Megfelezi a rajta áthaladó robot sebességét
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megfelezi a sebességet, de lehetőség van annak változtatására
        r.halveVelocity();
    }
}