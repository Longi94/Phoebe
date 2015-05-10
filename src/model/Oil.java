package model;

import model.basic.Position;
import skeleton.PhoebeLogger;
import view.OilView;
import view.TrackObjectBaseView;
import view.TrackView;

/**
 * Olajt megvalósító osztályt
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23
 */
public class Oil extends Obstacle {

    /**
     * Konstruktor egy paraméterrel
     * <p/>
     * Csak poziciót kap paraméterül
     *
     * @param pos az olajfolt pozíciója
     */
    public Oil(Position pos) {
        super(pos);
    }

    /**
     * Konstruktor két paraméterrel
     * <p/>
     * Poziciót és a pályát is megkapja paraméterül
     *
     * @param pos   az olajfolt pozíciója
     * @param track a pálya, amire az olajfolt található
     */
    public Oil(Position pos, Track track) {
        super(pos, track);
    }

    @Override
    public TrackObjectBaseView createView(TrackView t) {
        return new OilView(this,t);
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

    @Override
    protected int GET_MAXIMUM_ROUNDS() {
        return 20;                       //az olajfoltok 20 kört élnek
    }

    /**
     * Olaj olvasható formában való kiiratása
     *
     * @return olaj szépen kiírva
     */
    @Override
    public String toString() {
        return "Oil{" +
                    super.toString() +
                "}";
    }
}
