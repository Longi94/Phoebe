package test;

import model.Obstacle;
import org.junit.Assert;
import model.Track;
import model.basic.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrackTest {

    Track t;
    Position p;

    @Before
    public void setUp() {
        List<Position> in = new ArrayList<Position>();
        List<Position> out = new ArrayList<Position>();
        in.add(new Position(2,2));
        in.add(new Position(6,3));
        in.add(new Position(6, 7));
        in.add(new Position(-2,7));

        out.add(new Position(0,0));
        out.add(new Position(7,1));
        out.add(new Position(7,9));
        out.add(new Position(-5,9));

        this.t = new Track(in,out);
        this.p = new Position(3.99,2.5);
    }


    @Test
    public void testIsInTrack() {
        Assert.assertFalse(this.t.isInTrack(this.p));
    }


}