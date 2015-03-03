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

    //=============================================================================================
    // KONSTRUKTOROK
    //=============================================================================================

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

    //=============================================================================================
    // GETTER-SETTER-EK
    //=============================================================================================

    /**
     *
     * @return pozíció x koordinátája
     */
    public double getX() {
        return posX;
    }

    /**
     * Beállítja a pozíció x koordinátáját
     * @param posX a beállítandó x koordináta
     */
    public void setX(double posX) {
        this.posX = posX;
    }

    /**
     *
     * @return pozíció y koordinátája
     */
    public double getY() {
        return posY;
    }

    /**
     * Beállítja a pozíció y koordinátáját
     * @param posY a beállítandó y koordináta
     */
    public void setY(double posY) {
        this.posY = posY;
    }

}

