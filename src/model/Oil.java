package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

/**
 * Olajt megvalósító osztályt
 *
 * @author Bence Czipó
 * @since 2015.02.23
 */
public class Oil extends Obstacle {

    /**
     * Konstruktor egy paraméterrel
     *
     * Csak poziciót kap paraméterül
     *
     * @param pos   az olajfolt pozíciója
     */
    public Oil(Position pos) {
        super(pos);
    }

    /**
     * Konstruktor két paraméterrel
     *
     * Poziciót és a pályát is megkapja paraméterül
     *
     * @param pos   az olajfolt pozíciója
     * @param track a pálya, amire az olajfolt található
     */
    public Oil(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * A robot sebességváltoztatását tiltja le, robottal való ütközés esetén
     *
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megtartja a sebességet, és tiltja annak módosítását
        PhoebeLogger.message("r", "setEnabled", "false");
        r.setEnabled(false);

        PhoebeLogger.returnMessage();
    }

    /**
     * Olaj olvasható formában való kiiratása
     *
     * @return olaj szépen kiírva
     */
    @Override
    public String toString() {
        return "Oil{" + super.toString() + "}";
    }
}
