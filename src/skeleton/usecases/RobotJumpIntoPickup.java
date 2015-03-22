package skeleton.usecases;

import model.Pickup;
import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

/**
 * Robot pickupba ugrik use-case
 *
 * @author Thanh Long Tran
 * @since 2015.03.20.
 */
public class RobotJumpIntoPickup {

    public RobotJumpIntoPickup() {

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
        Robot r = new Robot(new Position(0,0), t, "TARS");

        t.addObject(r);

        // Pickup létrehozása
        t.addObject(new Pickup(new Position(1,0),t));

        PhoebeLogger.enableLogging(true);

        // Esemény lejátszása
        PhoebeLogger.message("r", "jump", "v");
        r.jump(new Velocity(90, 1)); // remélem ez a vízszintesen egyet jobbra

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }
}