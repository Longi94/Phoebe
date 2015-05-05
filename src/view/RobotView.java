package view;

import model.Robot;

import java.awt.*;

/**
 * Created by geri on 2015. 04. 25..
 */
public class RobotView extends TrackObjectBaseView {

    private Robot robot;
    private Color color; //a robotview-hoz tartozó szín

    public RobotView(Robot r) {
        robot = r;
        color = Color.CYAN;
    }

    public RobotView(Robot r, Color c) {
        robot = r;
        color = c;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(color);
        graph.fillOval(robot.getPos().convertX(xOffset,zoom),robot.getPos().convertY(yOffset,zoom),
                (int ) (robot.getRadius() * zoom), (int)(robot.getRadius() * zoom));
    }

}
