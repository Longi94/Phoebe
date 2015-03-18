package skeleton.usecases;

import model.Oil;
import model.Robot;
import model.Track;
import model.basic.Position;

import java.util.ArrayList;

/**
 * Created by bence on 2015.03.18..
 */
public class RobotJumpIntoOil {

    public RobotJumpIntoOil() {

        ArrayList<Position> in = new ArrayList<Position>();
        ArrayList<Position> out = new ArrayList<Position>();

        Track t = new Track(in,out);

        t.addObject(new Robot(new Position(0,0), t, "Robot1"));
        t.addObject(new Oil(new Position(1,1),t));
    }

}
