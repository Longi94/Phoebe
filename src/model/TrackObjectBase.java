package model;

import model.basic.Position;
import skeleton.PhoebeLogger;
import view.TrackObjectBaseView;

/**
 * Pályaelemek absztrakt ősosztálya
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public abstract class TrackObjectBase {

    private TrackObjectBaseView tobv;

    /**
     * Pályaelem default sugara
     */
    protected static double RADIUS = 0.4;

    /**
     * Pályaelem pozíciója
     */
    protected Position pos;

    /**
     * Pálya referencia
     */
    protected Track track;

    /**
     * Pályaelem sugara
     */
    protected double radius;

    /**
     * Konstruktor egy paraméterrel
     * <p/>
     * Csak a pozíciót kapja paraméterül
     *
     * @param pos az objektum pozíciója
     */
    public TrackObjectBase(Position pos) {
        this.pos = pos;
    }

    /**
     * Konstruktor két paraméterrel
     * <p/>
     * Egy pozíciót és a pályát is paraméterül kapja
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public TrackObjectBase(Position pos, Track track) {
        this.pos = pos;
        this.track = track;
    }

    /**
     * Getter a sugárhoz
     *
     * @return a sugár
     */
    public double getRadius() {
        return RADIUS;
    }

    /**
     * Megvizsgálja, ütközik-e az adott objektummal az objektum
     *
     * @param otherObject az objektum amivel vizsgáljuk hogy ütközik-e
     * @return true, ha ütköznek és false ha nem
     */
    public boolean hit(TrackObjectBase otherObject) {
        double distance = Math.sqrt(Math.pow(this.pos.getX() - otherObject.pos.getX(), 2) + Math.pow(this.pos.getY() - otherObject.pos.getY(), 2));
        Boolean b = distance <= RADIUS + otherObject.getRadius();
        PhoebeLogger.returnMessage(Boolean.toString(b));
        return b;
    }

    /**
     * @return a pálya, amin az objektum van
     */
    public Track getTrack() {
        return track;
    }

    /**
     * beállítja az objektum pályáját
     *
     * @param track a beállítandó pálya
     */
    public void setTrack(Track track) {
        this.track = track;
    }

    /**
     * Akkor hívódik meg,ha ütközik az objektummal egy robot
     *
     * @param r a robot, amivel ütközik
     */
    public void collide(Robot r) {
        PhoebeLogger.returnMessage();
    }

    /**
     * Akkor hívódik meg, ha ütközik az objektummal egy takarítórobot
     *
     * @param cr a takarítórobot
     */
    public void collide(CleaningRobot cr) {
    }

    /**
     * P pozíciótól a távolságot adja vissza, ha akadályról van szó, különben -1-et
     *
     * @param p
     * @return
     */
    public double obstacleDistance(Position p) {
        return -1;
    }

    /**
     * Új kör esetén meghívódó függvény
     */
    public void newRound() {

    }

    /**
     * Pozició getter függvénye
     *
     * @return a pályaelem pozíciója
     */
    public Position getPos() {
        PhoebeLogger.returnMessage("pos");
        return pos;
    }

    /**
     * A pályaelem kiiratásához olvasható formátum
     *
     * @return olvasható pályaelem
     */
    @Override
    public String toString() {
        return pos.toString();
    }
}
