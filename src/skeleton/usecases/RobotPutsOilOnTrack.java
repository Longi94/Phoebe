package skeleton.usecases;

import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Olajat rak le a robot a pályára usecase
 *
 * @author Benedek Fábián
 * @since 2015.03.22
 */
public class RobotPutsOilOnTrack {
    public RobotPutsOilOnTrack(){
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
        Robot r = new Robot(new Position(0,0), t, "R2D2");

        t.addObject(r);

        PhoebeLogger.enableLogging(true);

        // Esemény lejátszása
        PhoebeLogger.message("r", "putOil");
        r.putOil();

        PhoebeLogger.message("r", "jump", "dV");
        r.jump(new Velocity(Math.PI / 2, 1));
        


        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();
    }

}
