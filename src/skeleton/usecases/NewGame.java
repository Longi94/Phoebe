package skeleton.usecases;

import model.Pickup;
import model.Robot;
import model.Track;
import model.basic.Position;
import skeleton.PhoebeLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Új játék indítása use-case
 *
 * @author Gergely Reményi
 * @since 2015.03.21.
 */
public class NewGame {

    public NewGame() {
        PhoebeLogger.enableLogging(false);
        PhoebeLogger.clear();

        PhoebeLogger.enableLogging(true);

        // Játékosok létrehozása
        PhoebeLogger.create("List<Robot>","players");
        List<Robot> players = new ArrayList<Robot>();
        PhoebeLogger.returnMessage();

        // Külső pályaív létrehozása
        PhoebeLogger.create("List<Position>","in");
        List<Position> in = new ArrayList<Position>();
        PhoebeLogger.returnMessage();

        // Belső pályaív létrehozása
        PhoebeLogger.create("List<Position>","out");
        List<Position> out = new ArrayList<Position>();
        PhoebeLogger.returnMessage();

        // Belső pályaív feltöltése
        PhoebeLogger.create("Position","pi1");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("in", "add", "pi1");
        in.add(new Position(1, 1));
        PhoebeLogger.returnMessage();
        PhoebeLogger.create("Position", "pi2");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("in", "add", "pi2");
        in.add(new Position(9, 1));
        PhoebeLogger.returnMessage();
        PhoebeLogger.create("Position", "pi3");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("in", "add", "pi3");
        in.add(new Position(9, 9));
        PhoebeLogger.returnMessage();
        PhoebeLogger.create("Position", "pi4");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("in", "add", "pi4");
        in.add(new Position(1, 9));
        PhoebeLogger.returnMessage();

        // Külső pályaív feltöltése
        PhoebeLogger.create("Position","po1");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("out", "add", "po1");
        out.add(new Position(-1, -1));
        PhoebeLogger.returnMessage();
        PhoebeLogger.create("Position", "po2");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("out", "add", "po2");
        out.add(new Position(11, -1));
        PhoebeLogger.returnMessage();
        PhoebeLogger.create("Position", "po3");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("out", "add", "po3");
        out.add(new Position(11, 11));
        PhoebeLogger.returnMessage();
        PhoebeLogger.create("Position", "po4");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("out", "add", "po4");
        out.add(new Position(-1, 11));
        PhoebeLogger.returnMessage();

        // Pálya létrehozása
        PhoebeLogger.create("Track","(in,out)track");
        Track track = new Track(in,out);
        PhoebeLogger.returnMessage();

        // Pickupok létrehozása
        PhoebeLogger.create("Pickup", "p1");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("track", "addObject", "p1");
        track.addObject(new Pickup(new Position(1, 0), track));
        PhoebeLogger.create("Pickup", "p2");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("track", "addObject", "p2");
        track.addObject(new Pickup(new Position(9, 0), track));

        // Robotok létrehozása és hozzáadása a pályához
        PhoebeLogger.create("Robot","r1");
        Robot r1 = new Robot(new Position(0,0),track,"Marwin");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("track", "addObject", "r1");
        track.addObject(r1);
        PhoebeLogger.message("players", "add", "r1");
        players.add(r1);
        PhoebeLogger.returnMessage();

        PhoebeLogger.create("Robot","r2");
        Robot r2 = new Robot(new Position(5,0),track,"R2D2");
        PhoebeLogger.returnMessage();
        PhoebeLogger.message("track", "addObject", "r2");
        track.addObject(r2);
        PhoebeLogger.message("players", "add", "r2");
        players.add(r2);
        PhoebeLogger.returnMessage();

        PhoebeLogger.clear();
        PhoebeLogger.enableLogging(false);
    }

}
