package model;

import model.basic.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
 */
public class Track {

    public List<TrackObjectBase> items;
    public List<Position> innerArc;
    public List<Position> outerArc;

    public Track() {
        //TODO ez nagyon nem ilyen lesz
        items = new ArrayList<TrackObjectBase>();
    }

    public boolean isInTrack(Position pos) {
        return true;
    }

    public boolean addObject(TrackObjectBase object) {
        return false;
    }

    public boolean removeObject(TrackObjectBase object) {
        return false;
    }


}
