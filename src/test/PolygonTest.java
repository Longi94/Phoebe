package test;

import model.Track;
import model.basic.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PolygonTest {

    private ArrayList<Position> arc;

    @Before
    public void setUp() throws Exception {
        arc = new ArrayList<Position>();
        arc.add(new Position(0,0));
        arc.add(new Position(1,0));
        arc.add(new Position(1,1));
        arc.add(new Position(0,1));
    }

    @Test
    public void testInsideOpenPolygon() throws Exception {
        assertFalse(Track.insidePolygon(arc, new Position(1, 1),true));
    }

    @Test
    public void testInsideClosedPolygon() throws Exception {
        assertTrue(Track.insidePolygon(arc, new Position(1, 1),false));
    }

    @Test
    public void testIsInLine() throws Exception {
        assertFalse(Track.isInLine(new Position(1.5,1.5), new Position(1,1), new Position(0,0)));
    }
}