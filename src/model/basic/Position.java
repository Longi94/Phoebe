package model.basic;

/**
 * Created by bence on 2015.02.23..
 */
public class Position {
    /**
     * x koordináta
     */
    private double _posX;
    /**
     * y koordináta
     */
    private double _posY;

    /**
     * Konstruktor
     * @param x x koordináta
     * @param y y koordináta
     */
    public Position (double x, double y) {
        this._posX = x;
        this._posY = y;
    }

    /**
     * Paraméter nélküli konstuktor
     */
    public Position() {
        this(0,0);
    }
}

