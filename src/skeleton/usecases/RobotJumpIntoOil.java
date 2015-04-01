package skeleton.usecases;

import model.Obstacle;
import model.Oil;
import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Robot olajba ugrik use-case
 *
 * @author Bence Czipó
 * @since 2015.03.18.
 */
public class RobotJumpIntoOil {

    public RobotJumpIntoOil() {

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
        Robot r = new Robot(new Position(0,0), t, "Marwin");

        t.addObject(r);

        // Olaj inicializálása
        Obstacle o = new Oil(new Position(1,0));

        // felhasználói döntés várása
        int i = -1;
        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Hany utkozes van hatra az akadaly eletebol?");
                String str = br.readLine();
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.out.println("Hibás számformátum");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while ( i < 1 );
        o.setHitsLeft(i);
        t.addObject(o);

        // Esemény lejátszása
        PhoebeLogger.enableLogging(true);
        PhoebeLogger.message("r", "jump", "dv");

        r.jump(new Velocity(0, 1)); // remélem ez a vízszintesen egyet jobbra

        PhoebeLogger.message("r", "jump", "dv");
        r.jump(new Velocity(0,0)); //úgyis disabled lesz (elvileg)

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }

}
