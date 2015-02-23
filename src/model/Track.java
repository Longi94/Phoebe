package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
 */
public class Track {
    public List<Obstacle> _obstacles;
    public List<Robot> _robots;
    public List<Pickup> _pickups;

    public Track() {
        _obstacles = new ArrayList<Obstacle>();
        _robots = new ArrayList<Robot>();
        _pickups = new ArrayList<Pickup>();
    }

    /*public boolean addObstacle(Obstacle obs) {
        return _obstacles.add(obs);
    }

    public boolean addRobot(Robot robot) {
        return _robots.add(robot);
    }

    public boolean addPickup(Pickup pickup) {
        return _pickups.add(pickup);
    }*/



}
