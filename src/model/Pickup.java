package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public class Pickup extends TrackObjectBase {

    private Obstacle _obs;

    public Pickup (Position pos) {
        //random generálódik hogy mi legyen az obstacle
        _pos = pos;
    }

    public Obstacle get_obs() {
        return _obs;
    }
}
