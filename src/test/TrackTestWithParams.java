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

@RunWith(Parameterized.class)
public class TrackTestWithParams {

    Track t;
    double param;

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
    }

    public TrackTestWithParams (double posY) {
        param = posY;
    }


    @Test
    public void testIsInTrackParams() {
        Position pos = new Position(0,param);
        if ( (param >= 7 && param < 9) || (param > 0 && param < 4.5) ) {
            Assert.assertTrue(t.isInTrack(pos));
        } else {
            Assert.assertFalse(t.isInTrack(pos));
        }
    }

    @Parameters
    public static List<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();


        for (double i = -1; i< 11; i = i + 0.5) {
            params.add(new Object[] {i});
        }
        return params;
    }

}