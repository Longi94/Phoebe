package model.basic;

import skeleton.PhoebeLogger;

/**
 * Created by bence on 2015.02.23..
 */

public class Velocity {

    /**
     * ULTIMATE HELPER ÁBRA
     *        x <- a bezárt szög
     *        |
     *       ||
     *       |v /
     *       | /
     * ______|/_______
     *       |
     *       |
     *       |
     *    y = r * cos (x)
     *    x = r * sin(x)
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

    //=============================================================================================
    // KONSTRUKTOROK
    //=============================================================================================

    /**
     * Paraméter nélküli konstruktor
     */

    public Velocity() {
        this.angle = 0;
        this.magnitude = 0;
    }

    /**
     * Kétparaméteres konstruktor
     *
     * @param angle     a szög
     * @param magnitude a nagyság
     */
    public Velocity(double angle, double magnitude) {
        this.angle = angle;
        this.magnitude = magnitude;
    }

    //=============================================================================================
    // GETTER-SETTER-EK
    //=============================================================================================

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
        return magnitude * Math.sin(angle);
    }

    /**
     * A sebességet adja vissza descartes-i koordináta rendszerben.
     *
     * @return a sebességvektor y koordinátája
     */
    public double getVelocityY() {
        return magnitude * Math.cos(angle);
    }

    /**
     * Descares-i koordináták alapján beállítja a sebességet
     *
     * @param x x koordinátája a sebességvektornak
     * @param y y koordinátája a sebességvektornak
     */
    public void setDescartes(double x, double y) {
        this.magnitude = Math.sqrt(x * x + y * y);
        //Ha az első félsíkban van, az acos  helyes eredményt ad, viszont ha a második félsíkban van, akkor
        //cos(x) = cos(2PI-x) miatt nem a megfelelő eredményt kapjuk
        if (x > 0) {
            this.angle = Math.acos(y / this.magnitude);
        } else {
            this.angle = 2 * Math.PI - Math.acos(y / this.magnitude);
        }
    }

    //=============================================================================================
    // EGYÉB FÜGGVÉNYEK
    //=============================================================================================

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

    @Override
    public String toString() {
        return "magnitude=" + (double) Math.round(100 * magnitude) / 100 +
                ", angle=" + (double) Math.round(100 * 180 / Math.PI * angle) / 100 + //fokban
                ' ';
    }
}
