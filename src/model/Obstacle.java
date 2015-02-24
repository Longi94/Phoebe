package model;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class Obstacle extends TrackObjectBase {
    private static int MAXIMUM_HITS = 3;
    private static int MAXIMUM_ROUNDS = 20;

    private double life_left_hits;
    private double life_left_rounds;


    public Obstacle () {
        life_left_hits = MAXIMUM_HITS;
        life_left_rounds = MAXIMUM_ROUNDS;
    }

    public void hit () {
        life_left_hits -=1;
    }

    public boolean newRound() {
        life_left_rounds -=1;
        return (life_left_rounds > 0) && (life_left_hits > 0);
    }

}
