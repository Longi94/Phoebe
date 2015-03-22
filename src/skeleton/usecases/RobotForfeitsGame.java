package skeleton.usecases;

import model.Robot;
import model.Track;
import model.basic.Position;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Robot feladja a játékot
 *
 * @author Benedek Fábián
 * @since 2015.03.22.
 */
public class RobotForfeitsGame {
    public RobotForfeitsGame(){
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

        // A robot feladja a játékot, eltávolítás a pályáról
        PhoebeLogger.message("r", "forfeit");
        r.forfeit();

        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();
    }

}
