package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class TrackObjectBase {
    protected static final double RADIUS = 0.5;
    protected Position pos;
    protected Track track;

    public static double getRadius() {
        return RADIUS;
    }

    //TODO rosszul van paraméterezve szekvencia diagrammon
    public boolean hit(TrackObjectBase otherObject) {
        double distance = Math.sqrt(Math.pow(this.pos.getX() - otherObject.pos.getX(), 2) + Math.pow(this.pos.getY() - otherObject.pos.getY(), 2));

        return distance <= RADIUS + otherObject.getRadius(); //Szerintem semmi baj a 2*RADIUS-szal
    }


    public void collide (Robot r)  {

    }

    
    public void newRound() {}

    public Position getPos() {
        return pos;
    }
}
