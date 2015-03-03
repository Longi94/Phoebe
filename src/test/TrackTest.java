package test;

import org.junit.Assert;
import model.Track;
import model.basic.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrackTest {

    @Test
    public void testOverLine() throws Exception {
        Position p1 = new Position (5,5);
        Position p2 = new Position (0,0);
        Position p3 = new Position (10,1);
        Position p4 = new Position (0,9);
        Position p5 = new Position (10,10);
        Assert.assertEquals(-1, Track.overLine(p2,p3,p1) * Track.overLine(p4,p5,p1));
    }
}