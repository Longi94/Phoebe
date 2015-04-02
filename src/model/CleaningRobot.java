package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.03.27..
 */
public class CleaningRobot extends TrackObjectBase {

    private static final int CLEAN_TURNS = 2;             //2 körig takarítja a foltot
    private double angle;                               // a haladás iránya radiánban
    private Obstacle actuallyCleaning;                   // az akadály, amit éppen takarít
    private int cleanTurnsLeft;

    public CleaningRobot(Position pos) {
        super(pos);
        actuallyCleaning = null;                        //nem takarít semmit
        angle = 0;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Obstacle getActuallyCleaning() {
        return actuallyCleaning;
    }

    public void setActuallyCleaning(Obstacle actuallyCleaning) {
        cleanTurnsLeft = CLEAN_TURNS;                               //nyilván most kezdte takarítani, eltart még egy darabig
        this.actuallyCleaning = actuallyCleaning;
    }

    @Override
    public void collide(Robot r) {
        track.addObject(new Oil(this.getPos()));                   //ahol eddig volt, ott olaj lesz
        actuallyCleaning.setUnderCleaning(false);                   //többé már nem takarítják
        track.removeObject(this);
    }

    @Override
    public void collide(CleaningRobot cr) {
        cr.setAngle(cr.getAngle() + Math.PI / 2);
    }

    public double targetClosestObstacle() {
        Position closest = track.getClosestObstaclePos(this.pos);
        if (closest == null) {
            return 0;       //TODO ezt azért rohadtul jobban is specifikálhatták volna. Javaslom, hogy ilyenkor zűnjön meg a francba...
        }
        double angle = Math.atan2(closest.getX() - pos.getX(), closest.getY() - pos.getY());
        //TODO kiszámítja milyen irányba esik a legközelebbi akadály (belevéve, hogy nem mehet ki a pályáról)
        //jelenleg átvág mindenen hogy a leggyorsabban odajusson, ami egyszerre megmagyarázható (szervizutakon megy) és übergáz...
        return angle;
    }

    private void step() {
        pos.move(new Velocity(1, angle));            // mozog egyet abba az irányba, amibe beállt
        track.cleaningRobotJumped(this);
    }

    @Override
    public void newRound() {
        if (actuallyCleaning != null) {
            if (--cleanTurnsLeft == 0) {
                track.removeObject(actuallyCleaning);
                actuallyCleaning = null;
                targetClosestObstacle();
            }
        } else {
            step();                             //azért lép előbb, mint hogy irányt vált, mert csak így oldható meg hogy ütközéskor arréb menjen
            //TODO mivanha elhagyja a pályát
            targetClosestObstacle();
        }
    }

}