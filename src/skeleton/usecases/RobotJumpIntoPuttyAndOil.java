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
 * Robot olajba és ragacsba ugrik egyszerre
 *
 *
 * @author Bence Czipó
 * @since 2015.03.18.
 */
public class RobotJumpIntoPuttyAndOil {

    public RobotJumpIntoPuttyAndOil() {

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

        // robot inicializálása
        Robot r = new Robot(new Position(0,0), t, "R2D2");
        t.addObject(r);

        //Olaj és ragacs inicializálása
        t.addObject(new Oil(new Position(0.81,0.6),t));
        t.addObject(new Putty(new Position(0.81,-0.6),t));
        //'cos same position is mainstream - de elvileg az is műkszik

        PhoebeLogger.enableLogging(true);

        // esemény lejátszára
        PhoebeLogger.message("r", "jump", "dv");

        r.jump(new Velocity(0, 1)); // remélem ez a vízszintesen egyet jobbra

        PhoebeLogger.message("r", "jump", "dv");

        r.jump(new Velocity(0.1, 1)); //úgyis megcsúszik

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }

}
