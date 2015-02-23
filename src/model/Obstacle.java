package model;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class Obstacle extends TrackObjectBase {
    private static int MAXIMUM_HITS = 3;
    private static int MAXIMUM_ROUNDS = 20;

    private double _life_left_hits;
    private double _life_left_rounds;


    public Obstacle () {
        _life_left_hits = MAXIMUM_HITS;
        _life_left_rounds = MAXIMUM_ROUNDS;
    }

    public void hit () {
        _life_left_hits -=1;
    }

    public boolean newRound() {
        _life_left_rounds -=1;
        return (_life_left_rounds > 0) && (_life_left_hits > 0);
    }

}
