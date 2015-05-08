package view;

import model.Robot;

import java.awt.*;

/**
 * Created by geri on 2015. 04. 25..
 */
public class RobotView extends TrackObjectBaseView {

    private Robot robot;

    public RobotView(Robot r, TrackView tv) {
        super(r, tv);
        robot = r;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(robot.getColor());
        int radius = (int) (robot.getRadius() * zoom);
        graph.fillOval(robot.getPos().convertX(xOffset,zoom) - radius/2,robot.getPos().convertY(yOffset,zoom)- radius/2,
                radius, radius);
    }

}
