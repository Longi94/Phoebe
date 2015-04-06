package test;

import model.Track;
import model.basic.Position;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.spec.PSource;
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
        Position pos =  new Position(1, 1);
        assertFalse(pos.insidePolygon(arc, true));
    }

    @Test
    public void testInsideClosedPolygon() throws Exception {
        Position pos =  new Position(1, 1);
        assertTrue(pos.insidePolygon(arc, false));
    }

    @Test
    public void testIsInLine() throws Exception {
        Position pos =  new Position(1.5, 1.5);
        assertFalse(pos.isInLine(new Position(1, 1), new Position(0, 0)));
    }
    @Test
    public void testIntersection() {
        Position p1 = new Position(3,2);
        Position p2 = new Position(3,0);
        Position p3 = new Position(5,2);
        Position p4 = new Position(4,0);
        Position inter = Position.intersection(p1,p2,p3,p4);
        System.out.println(inter.getX() + "  " + inter.getY());
        assertEquals(1.833333333, inter.getX(), 0.01);
    }

}