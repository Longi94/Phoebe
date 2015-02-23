package model;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot {

    /**
     * Belső osztály a pozíciónak
     */
    private class Position {
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

    /**
     * Beső osztály a sebességnek
     */
    private class Velocity {

        /**
         * A sebességének nagysága
         */
        private double _radius;
        /**
         * A sebességének iránya
         */
        private double _angle;

        /**
         * Konstruktor
         */
        public Velocity() {
            this._angle = 0;
            this._radius = 0;
        }
    }

    private static double ROBOT_RADIUS = 0.5;

    private Position _pos;
    private Velocity _vel;

    public Robot (double x,double y) {
        _pos = new Position(x,y);
        _vel = new Velocity();
    }

    public void jump(Velocity v) {
        //TBD
    }

    public boolean putOil() {
        return true;
    }

    public boolean putPutty() {
        return true;
    }

    public void forfit() {

    }


}
