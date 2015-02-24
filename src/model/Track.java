package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
 */
public class Track {

    public List<Obstacle> obstacles;
    public List<Robot> robots;
    public List<Pickup> pickups;


    public Track() {
        //TBD ez nagyon nem ilyen lesz
        obstacles = new ArrayList<Obstacle>();
        robots = new ArrayList<Robot>();
        pickups = new ArrayList<Pickup>();
    }


    public void refreshModel (List<Robot> robotsNewPos, List<Obstacle> obstaclesPlaced, List<Pickup> pickupsPicked) {
        //TBD
    }

}
