package skeleton.usecases;

import model.Oil;
import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;

import java.util.ArrayList;

/**
 * Created by bence on 2015.03.18..
 */
public class RobotJumpIntoOil {

    public RobotJumpIntoOil() {

        ArrayList<Position> in = new ArrayList<Position>();
        ArrayList<Position> out = new ArrayList<Position>();

        Track t = new Track(in,out);

        Robot r = new Robot(new Position(0,0), t, "Robot1");

        t.addObject(r);
        t.addObject(new Oil(new Position(1,1),t));

        r.jump(new Velocity());

    }

}
