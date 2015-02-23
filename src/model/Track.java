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

}
