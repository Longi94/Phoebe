package skeleton.usecases;

import model.*;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        Obstacle o = new Putty(new Position(1,0));
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


        PhoebeLogger.enableLogging(true);

        PhoebeLogger.message("r", "jump", "dv");

        r.jump(new Velocity(Math.PI / 2, 1)); // remélem ez a vízszintesen egyet jobbra

        PhoebeLogger.message("r", "jump", "dv");
        r.jump(new Velocity(Math.PI/2-0.1,1)); //kanyarodjunk egy kicsit, just for fun

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);

    }

}
