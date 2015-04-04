package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

/**
 * Ragacsot megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23
 */
public class Putty extends Obstacle {

    /**
     * Konstruktor egy paraméterrel
     * <p/>
     * Csak poziciót kap
     *
     * @param pos a ragacs pozíciója
     */
    public Putty(Position pos) {
        super(pos);
    }

    /**
     * Konstruktor két paraméterrel
     * <p/>
     * Megkap egy poziciót és a pályát is
     *
     * @param pos   a ragacs pozíciója
     * @param track a pálya, amin a ragacs található
     */
    public Putty(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * Megfelezi a rajta áthaladó robot sebességét
     *
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megfelezi a sebességet, de lehetőség van annak változtatására
        PhoebeLogger.message("r", "halveVelocity");
        r.halveVelocity();

        PhoebeLogger.returnMessage();
    }

    @Override
    protected int GET_MAXIMUM_HITS() {
        return 4;                       //négy robot ráugrása után a ragacs eltűnik a pályáról
    }

    /**
     * Ragacs olvasható formában való kiiratásához
     *
     * @return olvasható ragacs
     */
    @Override
    public String toString() {
        return "Putty{\n" +
                super.toString() +
                "}\n";
    }
}