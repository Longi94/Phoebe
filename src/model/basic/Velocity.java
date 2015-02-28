package model.basic;

/**
 * Created by bence on 2015.02.23..
 */

public class Velocity {

    /**
     * A sebességének nagysága
     */
    private double magnitude;
    /**
     * A sebességének iránya
     */
    private double angle;

    //ide kéne vagy getter setter meg lehet hogy két velocity összeadása is

    public void add(Velocity v) {

    }

    /**
     * Konstruktor
     */
    public Velocity() {
        this.angle = 0;
        this.magnitude = 0;
    }
}
