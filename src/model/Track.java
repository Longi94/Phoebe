package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
 */
public class Track {
    public static int DEFAULT_TURN_NUMBER = 40;

    public List<Obstacle> _obstacles;
    public List<Robot> _robots;
    public List<Pickup> _pickups;
    public int _turns_left;

    public Track() {
        //TBD ez nagyon nem ilyen lesz
        _obstacles = new ArrayList<Obstacle>();
        _robots = new ArrayList<Robot>();
        _pickups = new ArrayList<Pickup>();
        _turns_left = DEFAULT_TURN_NUMBER;
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

    public void newTurn (List<Robot> robotsNewPos, List<Obstacle> obstaclesPlaced) {
        //TBD
    }

}
