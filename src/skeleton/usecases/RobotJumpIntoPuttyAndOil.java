package skeleton.usecases;

import model.Oil;
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
public class RobotJumpIntoPuttyAndOil {

    public RobotJumpIntoPuttyAndOil() {

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

        Robot r = new Robot(new Position(0,0), t, "R2D2");

        t.addObject(r);
        t.addObject(new Oil(new Position(1,0.1),t));
        t.addObject(new Putty(new Position(1,-0.1),t));
        //'cos same position is mainstream - de elvileg az is műkszik

        PhoebeLogger.enableLogging(true);

        r.jump(new Velocity(90, 1)); // remélem ez a vízszintesen egyet jobbra
        r.jump(new Velocity(90 - 0.1, 1)); //úgyis megcsúszik

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }

}
