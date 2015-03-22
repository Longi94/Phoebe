package skeleton.usecases;

import model.Robot;
import model.Track;
import model.basic.Position;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Created by Benedek on 2015.03.22..
 */
public class RobotsPutsOilOnTrack {
    public RobotsPutsOilOnTrack(){
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

        PhoebeLogger.enableLogging(true);


        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();
    }

}
