package model;

import model.basic.Position;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class Obstacle extends TrackObjectBase {
    private static final int MAXIMUM_HITS = 3;
    private static final int MAXIMUM_ROUNDS = 20;

    private int hitsLeft;
    private int roundsLeft;

    /**
     * Konstruktor
     * @param pos az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Obstacle (Position pos, Track track) {
        super(pos,track);
        hitsLeft = MAXIMUM_HITS;
        roundsLeft = MAXIMUM_ROUNDS;
    }


    /**
     * Az adott akadály milyen hatással van a robotra aki át haladrajta
     * @param r a robot, aki áthalad az akadályon
     */
    public abstract void takeEffect(Robot r);

    @Override
    public void collide(Robot r) {
        hitsLeft -=1;
        takeEffect(r);
    }

    @Override
    public void newRound() {
        roundsLeft -=1;
    }

}
