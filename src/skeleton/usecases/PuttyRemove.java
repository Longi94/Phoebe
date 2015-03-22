package skeleton.usecases;

import model.Obstacle;
import model.Oil;
import model.Putty;
import model.Track;
import model.basic.Position;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by geri on 2015. 03. 21..
 */
public class PuttyRemove {

    public PuttyRemove() {
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

        Track t = new Track(in,out);

        Obstacle p = new Putty(new Position(1,0));

        t.addObject(p);

        int i = -1;
        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Hany kor van hatra az akadaly eletebol?");
                String str = br.readLine();
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                System.out.println("Hibás számformátum");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while ( i < 1 );
        p.setRoundsLeft(i);

        PhoebeLogger.enableLogging(true);

        PhoebeLogger.message("p", "newRound", "");

        p.newRound();

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);
    }

}
