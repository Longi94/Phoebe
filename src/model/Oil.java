package model;

/**
 * Created by bence on 2015.02.23..
 */
public class Oil extends Obstacle{


    /**
     * Az adott akadály milyen hatással van a robotra aki át haladrajta
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megtartja a sebességet, és tiltja annak módosítását
        r.canMove = false;
    }
}
