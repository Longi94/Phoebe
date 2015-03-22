package skeleton.usecases;

import model.Pickup;
import model.Robot;
import model.Track;
import model.basic.Position;
import skeleton.PhoebeLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 03. 21..
 */
public class NewGame {

    public NewGame() {
        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();

        PhoebeLogger.enableLogging(true);

        PhoebeLogger.create("List<Robot>","players");
        List<Robot> players = new ArrayList<Robot>();

        PhoebeLogger.create("List<Position>","in");
        List<Position> in = new ArrayList<Position>();

        PhoebeLogger.create("List<Position>","out");
        List<Position> out = new ArrayList<Position>();

        PhoebeLogger.create("Position","pi1");
        PhoebeLogger.message("in","add","pi1");
        in.add(new Position(1,1));
        PhoebeLogger.create("Position","pi2");
        PhoebeLogger.message("in","add","pi2");
        in.add(new Position(9, 1));
        PhoebeLogger.create("Position","pi3");
        PhoebeLogger.message("in","add","pi3");
        in.add(new Position(9,9));
        PhoebeLogger.create("Position","pi4");
        PhoebeLogger.message("in","add","pi4");
        in.add(new Position(1,9));

        PhoebeLogger.create("Position","po1");
        PhoebeLogger.message("out","add","po1");
        out.add(new Position(-1, -1));
        PhoebeLogger.create("Position","po2");
        PhoebeLogger.message("out","add","po2");
        out.add(new Position(11, -1));
        PhoebeLogger.create("Position","po3");
        PhoebeLogger.message("out","add","po3");
        out.add(new Position(11, 11));
        PhoebeLogger.create("Position","po4");
        PhoebeLogger.message("out","add","po4");
        out.add(new Position(-1, 11));

        PhoebeLogger.create("Track","(in,out)track");
        Track track = new Track(in,out);

        PhoebeLogger.create("Pickup","p1");
        track.addObject(new Pickup(new Position(1,0),track));
        PhoebeLogger.create("Pickup","p2");
        track.addObject(new Pickup(new Position(9,0),track));

        PhoebeLogger.create("Robot","r1");
        Robot r1 = new Robot(new Position(0,0),track,"Marwin");
        track.addObject(r1);
        PhoebeLogger.message("players","add","r1");
        players.add(r1);

        PhoebeLogger.create("Robot","r2");
        Robot r2 = new Robot(new Position(5,0),track,"R2D2");
        track.addObject(r2);
        PhoebeLogger.message("players","add","r2");
        players.add(r2);

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);
    }

}
