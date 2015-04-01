package skeleton.usecases;

import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Robot kiugrik a pályáról usecase
 *
 * @author Beneden Fábián
 * @since 2015.03.19.
 */
public class RobotJumpOutOfTrack {
    public RobotJumpOutOfTrack(){
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

        //DUMMY NÉGYZET ALAKÚ PÁLYA

        Track t = new Track(in,out);

        // Robot inicializálása
        Robot r = new Robot(new Position(0,0), t, "R2D2");

        t.addObject(r);

        PhoebeLogger.enableLogging(true);

        PhoebeLogger.message("r", "jump", "new Velocity(Math.PI / 2, 1)");
        r.jump(new Velocity(0, 1));

        PhoebeLogger.message("r", "getPos");
        Position pos = r.getPos();
        PhoebeLogger.message("t", "isInTrack", "pos");
        boolean isR2D2InTrack = t.isInTrack(pos);

        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();
    }

}
