package model.basic;

/**
 * Created by bence on 2015.02.23..
 */
public class Position {
    /**
     * x koordináta
     */
    private double posX;
    /**
     * y koordináta
     */
    private double posY;

    /**
     * Konstruktor
     * @param x x koordináta
     * @param y y koordináta
     */
    public Position (double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Paraméter nélküli konstuktor
     */
    public Position() {
        this(0,0);
    }
}

