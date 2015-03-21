package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class TrackObjectBase {
    protected static double RADIUS = 0.5;
    protected Position pos;
    protected Track track;
    protected double radius;

    /**
     * Konstruktor
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public TrackObjectBase(Position pos, Track track) {
        this.pos = pos;
        this.track = track;
    }

    /**
     * Konstruktor pálya nélkül
     *
     * @param pos az objektum pozíciója
     */
    public TrackObjectBase(Position pos) {
        this.pos = pos;
    }


    /**
     * Getter a sugárhoz
     *
     * @return a sugár
     */
    public static double getRadius() {
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
     * Új kör esetén meghívódó függvény
     */
    public void newRound() {

    }

    public Position getPos() {
        PhoebeLogger.returnMessage("pos");
        return pos;
    }

    @Override
    public String toString() {
        return "pos=" + pos;
    }
}
