package model.basic;

import skeleton.PhoebeLogger;

/**
 * Sebességet megvalóstó osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Velocity {

    /**
     * SEGÍTSÉG
     *                az ott a szög
     *       |       /
     *       |  /   /
     *       | /   V
     * ______|/_______
     *       |
     *       |
     *       |
     *    x = r * cos (x)
     *    y = r * sin(x)
     *
     */

    /**
     * A sebességének nagysága
     */
    private double magnitude;

    /**
     * A sebesség iránya.A szöget a függőleges tengelytől (y tengely) óramutató járásával megegyezően számítjuk.
     */
    private double angle;

    /**
     * Paraméter nélküli konstruktor
     * <p/>
     * Inicializálja a sebességet 0 nagyságúra és 0 fokos szöggel
     */
    public Velocity() {
        this(0, 0);
    }

    /**
     * Kétparaméteres konstruktor
     * <p/>
     * Inicializálja a sebességet a paraméterül kapott két double értékkel
     *
     * @param angle     a szög
     * @param magnitude a nagyság
     */
    public Velocity(double angle, double magnitude) {
        this.angle = angle;
        this.magnitude = magnitude;
    }

    /**
     * Sebesség suggara polárkotdinátás számítás esetén
     *
     * @return a sugár
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Beállítja a sebesség polárkoordinátás sugarát
     *
     * @param magnitude a sugár
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Polárkoordinátás számítás esetén a sebesség szögét adja meg
     *
     * @return a szöge
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Polárkoordinátás számítás esetén a sebesség szögét állítja be
     *
     * @param angle a szög
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }


    /**
     * A sebességet adja vissza descartes-i koordináta rendszerben.
     *
     * @return a sebességvektor x koordinátája
     */
    public double getVelocityX() {
        return magnitude * Math.cos(angle);
    }

    /**
     * A sebességet adja vissza descartes-i koordináta rendszerben.
     *
     * @return a sebességvektor y koordinátája
     */
    public double getVelocityY() {
        return magnitude * Math.sin(angle);
    }

    /**
     * Descares-i koordináták alapján beállítja a sebességet
     *
     * @param x x koordinátája a sebességvektornak
     * @param y y koordinátája a sebességvektornak
     */
    public void setDescartes(double x, double y) {
        this.magnitude = Math.sqrt(x * x + y * y);
        //Ha az első félsíkban van, az atan  helyes eredményt ad, viszont ha a második félsíkban van, akkor
        if (x==0) {
            if (y > 0) {
                this.angle = Math.PI/2;
            } else {
                this.angle = -1* Math.PI/2;
            }
        } else if (x > 0) {
            this.angle = Math.atan(y /x);
        } else {
            this.angle =  Math.PI + Math.atan(y / x);
        }
        //this.angle = Math.atan2(y,x);   //nem is tudtam, hogy ilyen is van, ez sokkal nagyobb királyság
    }

    /**
     * Hozzáad a sebességhez egy másikat
     *
     * @param v a másik, hozzáadandó sebesség
     */
    public void add(Velocity v) {
        double tempX = v.getVelocityX() + this.getVelocityX();
        double tempY = v.getVelocityY() + this.getVelocityY();
        this.setDescartes(tempX, tempY);
        PhoebeLogger.returnMessage();
    }

    /**
     * Sebesség olvasható formában való megjelenítéséhez
     *
     * @return A sebesség olvasható formában
     */
    @Override
    public String toString() {
        return "Velocity{" +
                    "magnitude:" + (double) Math.round(100 * magnitude) / 100  + "," +
                    "angle:" + (double) Math.round(100 * 180 / Math.PI * angle) / 100 +
                "}"; //fokban
    }
}
