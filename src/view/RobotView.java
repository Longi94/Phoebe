package view;

import model.Robot;

import java.awt.*;

/**
 * Created by geri on 2015. 04. 25..
 */
public class RobotView extends TrackObjectBaseView {

    private Robot robot;

    public RobotView(Robot r) {
        super(r);
        robot = r;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(robot.getColor());
        graph.fillOval(robot.getPos().convertX(xOffset,zoom),robot.getPos().convertY(yOffset,zoom),
                (int ) (robot.getRadius() * zoom), (int)(robot.getRadius() * zoom));
    }

}
