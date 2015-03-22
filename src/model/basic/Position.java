package model.basic;

import skeleton.PhoebeLogger;

/**
 * Pozíciókat megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Position {
    /**
     * X koordináta
     */
    private double posX;

    /**
     * Y koordináta
     */
    private double posY;

    /**
     * Paraméter nélküli konstuktor
     *
     * Inicializálja a pozíciót a (0,0) pontba
     */
    public Position() {
        this(0, 0);
    }

    /** Konstruktor
     *
     * A paraméterül kapott két double értéket állítja be a pozíciónak
     *
     * @param x x koordináta
     * @param y y koordináta
     */
    public Position(double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * @return pozíció x koordinátája
     */
    public double getX() {
        return posX;
    }

    /**
     * Beállítja a pozíció x koordinátáját
     *
     * @param posX a beállítandó x koordináta
     */
    public void setX(double posX) {
        this.posX = posX;
    }

    /**
     * @return pozíció y koordinátája
     */
    public double getY() {
        return posY;
    }

    /**
     * Beállítja a pozíció y koordinátáját
     *
     * @param posY a beállítandó y koordináta
     */
    public void setY(double posY) {
        this.posY = posY;
    }

    /**
     * E pont távolságát adja meg p-től
     *
     * @param p a pont amelytől távolságot kérünk
     *
     * @return a távolság p-től
     */
    public double getDistance(Position p) {
        return Math.sqrt(Math.pow(posX - p.getX(), 2) + Math.pow(posY - p.getY(), 2));
    }

    /**
     * Pozíció módosítása sebességvektor alapján TODO: lehet hogy nem ide kéne, mert csak a Robot használja
     *
     * @param v a sebességvektor
     */
    public void move(Velocity v) {
        posX += v.getVelocityX();
        posY += v.getVelocityY();
        PhoebeLogger.returnMessage();
    }

    /**
     * Pozició olvasható formában való megjelenítéséhez
     *
     * @return A pozíció olvasható formában
     */
    @Override
    public String toString() {
        return "posX=" + (double) Math.round(100 * posX) / 100 +
                ", posY=" + (double) Math.round(100 * posY) / 100 + " ";
    }
}

