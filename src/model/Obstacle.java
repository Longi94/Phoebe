package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

/**
 * Created by bence on 2015.02.23..
 */
public abstract class Obstacle extends TrackObjectBase {
    private static final int MAXIMUM_HITS = 3;
    private static final int MAXIMUM_ROUNDS = 20;
    protected static double RADIUS = 0.35;
    private int hitsLeft;
    private int roundsLeft;

    /**
     * Konstruktor
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Obstacle(Position pos, Track track) {
        super(pos, track);
        hitsLeft = MAXIMUM_HITS;
        roundsLeft = MAXIMUM_ROUNDS;
    }

    /**
     * Sajnos ez is kell ahhoz, hogy a radiust overrideoljuk
     *
     * @return az akadály sugara
     */
    public static double getRadius() {
        return RADIUS;
    }

    /**
     * Az adott akadály milyen hatással van a robotra aki át haladrajta
     *
     * @param r a robot, aki áthalad az akadályon
     */
    public abstract void takeEffect(Robot r);

    @Override
    public void collide(Robot r) {
        hitsLeft -= 1;
        PhoebeLogger.message("Obstacle", "takeEffect", "r");
        takeEffect(r);

        if (hitsLeft == 0) {
            PhoebeLogger.message("Track", "takeEffect", "r");
            track.removeObject(this);
        }
    }

    @Override
    public void newRound() {
        roundsLeft -= 1;

        if (roundsLeft == 0) {
            PhoebeLogger.message("Track", "removeObject", "this");
            track.removeObject(this);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "hitsLeft=" + hitsLeft +
                ", roundsLeft=" + roundsLeft;
    }
}
