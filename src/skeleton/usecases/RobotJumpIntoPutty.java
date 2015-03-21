package skeleton.usecases;

import model.Putty;
import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Created by bence on 2015.03.18..
 */
public class RobotJumpIntoPutty {

    public RobotJumpIntoPutty() {

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

        Robot r = new Robot(new Position(0,0), t, "WALL-E");

        t.addObject(r);
        t.addObject(new Putty(new Position(1,0),t));

        PhoebeLogger.enableLogging(true);

        PhoebeLogger.message("r", "jump", "dv");

        r.jump(new Velocity(Math.PI/2, 1)); // remélem ez a vízszintesen egyet jobbra
        r.jump(new Velocity(Math.PI/2-0.1,1)); //kanyarodjunk egy kicsit, just for fun

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }

}
