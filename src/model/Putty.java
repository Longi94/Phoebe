package model;

/**
 * Created by bence on 2015.02.23..
 */
public class Putty extends Obstacle {

    /**
     * Az adott akadály milyen hatással van a robotra aki át haladrajta
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megfelezi a sebességet, de lehetőség van annak változtatására
    }
}