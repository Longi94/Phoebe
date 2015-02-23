package model;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class Obstacle {
    private  static double OBSTACLE_RADIUS = 0.5;
    private static int MAXIMUM_HITS = 3;
    private static int MAXIMUM_ROUNDS = 20;

    private double _life_left_hits;
    private double _life_left_rounds;


    public Obstacle () {
        _life_left_hits = MAXIMUM_HITS;
        _life_left_rounds = MAXIMUM_ROUNDS;
    }

}
