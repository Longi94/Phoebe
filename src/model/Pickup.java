package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public class Pickup {
    private Obstacle _obs;
    private Position _pos;

    public Pickup (Position pos) {
        //random generálódik hogy mi legyen az obstacle
        _pos = pos;
    }
}
