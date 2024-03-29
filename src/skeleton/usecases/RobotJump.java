package skeleton.usecases;

import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Robot ugrás use-case
 *
 * @author Gergely Reményi
 * @since 2015.03.21.
 */
public class RobotJump {

    public RobotJump() {

        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();

        // Pálya inicializálása
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

        Track t = new Track(in,out);

        // Robot inicializálása
        Robot r = new Robot(new Position(0,0), t, "IronMan");

        t.addObject(r);

        // Robot ugrik esemény lejátszása
        PhoebeLogger.enableLogging(true);
        PhoebeLogger.message("r", "jump", "dv");

        r.jump(new Velocity(0, 1));

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }

}
