package skeleton.usecases;

import model.Pickup;
import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Created by ThanhLong on 2015.03.20..
 */
public class RobotJumpIntoPickup {

    public RobotJumpIntoPickup() {

        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();

        ArrayList<Position> in = new ArrayList<Position>();
        ArrayList<Position> out = new ArrayList<Position>();

        out.add(new Position(-1, -1));
        out.add(new Position(11, -1));
        out.add(new Position(11, 11));
        out.add(new Position(-1, 11));

        in.add(new Position(1,1));
        in.add(new Position(9, 1));
        in.add(new Position(9,9));
        in.add(new Position(1,9));

        //DUMMY NÉGYZET ALAKÚ PÁLYA

        Track t = new Track(in,out);

        Robot r = new Robot(new Position(0,0), t, "TARS");

        t.addObject(r);
        t.addObject(new Pickup(new Position(1,0),t));

        PhoebeLogger.enableLogging(true);

        r.jump(new Velocity(90, 1)); // remélem ez a vízszintesen egyet jobbra
        r.jump(new Velocity(0,0)); //úgyis disabled lesz (elvileg)

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }
}