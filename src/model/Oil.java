package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public class Oil extends Obstacle {

    /**
     * Konstruktor
     *
     * @param pos   az olajfolt pozíciója
     * @param track a pálya, amire az olajfolt található
     */
    public Oil(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * A robot sebességváltoztatását tiltja le
     *
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megtartja a sebességet, és tiltja annak módosítását
        r.setEnabled(false);
    }

    @Override
    public String toString() {
        return "Oil{" + super.toString() +"}";
    }
}
