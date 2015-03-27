package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

/**
 * Akadályokat megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public abstract class Obstacle extends TrackObjectBase {

    /**
     * Ennyi ütközés után tűnik el egy akadály
     */
    private static final int MAXIMUM_HITS = 3;

    /**
     * Ennyi kör után tűnik el egy akadály
     */
    private static final int MAXIMUM_ROUNDS = 20;

    /**
     * Akadály sugara
     */
    protected static double RADIUS = 0.35;

    /**
     * Hátralevő ütközése száma az eltűnésig
     */
    private int hitsLeft;

    /**
     * Hátralevő körök szám az eltűnésig
     */
    private int roundsLeft;

    /**
     * Konstruktor egy paraméterrel
     * <p/>
     * Csak poziciót kap paraméterül
     *
     * @param pos az objektum pozíciója
     */
    public Obstacle(Position pos) {
        super(pos);
        hitsLeft = MAXIMUM_HITS;
        roundsLeft = MAXIMUM_ROUNDS;
    }

    /**
     * Konstruktor két paraméterrel
     * <p/>
     * Megkapja paraméterül a poziciót és pálya referenciát is
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Obstacle(Position pos, Track track) {
        super(pos, track);
        hitsLeft = MAXIMUM_HITS;
        roundsLeft = MAXIMUM_ROUNDS;
    }

    /**
     * Sugár getter függvénye
     *
     * @return az akadály sugara
     */
    public static double getRadius() {
        return RADIUS;
    }

    /**
     * Az adott akadály milyen hatással van a robotra aki át haladrajta
     *
     * @param r a robot, aki áthalad az akadályon
     */
    public abstract void takeEffect(Robot r);

    /**
     * Egy robot ugrik (ütközik) az akadályra (akadállyal)
     *
     * @param r a robot, amivel ütközik
     */
    @Override
    public void collide(Robot r) {
        hitsLeft -= 1;
        PhoebeLogger.message("Obstacle", "takeEffect", "r");
        takeEffect(r);

        if (hitsLeft == 0) {
            PhoebeLogger.message("Track", "removeObject", "this");
            track.removeObject(this);
        }
        PhoebeLogger.returnMessage();
    }

    /**
     * Egy takarítórobot ütközik az akadállyal (feltörli azt)
     *
     * @param cr a takarítórobot
     */
    @Override
    public void collide(CleaningRobot cr) {
        if (cr.getActuallyCleaning() == null) { //ha még nem takarít senkit
            cr.setActuallyCleaning(this);
        }
    }

    /**
     * Hátralevő ütközések az eltűnés előtt setter függvény
     *
     * @param hitsLeft Hátralevő ütközések
     */
    public void setHitsLeft(int hitsLeft) {
        this.hitsLeft = hitsLeft;
    }

    /**
     * Hátralevő körök az eltűnés előtt setter függvény
     *
     * @param roundsLeft
     */
    public void setRoundsLeft(int roundsLeft) {
        this.roundsLeft = roundsLeft;
    }

    /**
     * Új kör esetén lefutó függvény
     */
    @Override
    public void newRound() {
        roundsLeft -= 1;

        if (roundsLeft == 0) {
            PhoebeLogger.message("Track", "removeObject", "this");
            track.removeObject(this);
        }

        PhoebeLogger.returnMessage();
    }

    /**
     * Akadály kiírása olvasható formában
     *
     * @return Akadály szépen
     */
    @Override
    public String toString() {
        return super.toString() + "hitsLeft=" + hitsLeft +
                ", roundsLeft=" + roundsLeft;
    }
}
