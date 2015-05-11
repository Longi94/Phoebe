package view;

import model.CleaningRobot;

import java.awt.*;

/**
 * Takarító robot kinézet
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class CleaningRobotView extends TrackObjectBaseView {

    private CleaningRobot cleaningRobot;

    public CleaningRobotView(CleaningRobot cleaningRobot, TrackView tv) {
        super(cleaningRobot,tv);
        this.cleaningRobot = cleaningRobot;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(new Color(0, 0, 0));
        int radius = (int) (cleaningRobot.getRadius() * zoom);
        graph.fillOval(cleaningRobot.getPos().convertX(xOffset, zoom) - radius / 2, cleaningRobot.getPos().convertY(yOffset, zoom) - radius / 2,
                radius, radius);

        graph.setColor(new Color(255,255,255));
        int size = (int) (cleaningRobot.getRadius() * 0.8 * zoom);

        if(cleaningRobot.getActuallyCleaning() != null) {
            graph.fillRect(cleaningRobot.getPos().convertX(xOffset, zoom) - radius / 2, cleaningRobot.getPos().convertY(yOffset, zoom) - radius / 2, size, size);
        }
        else {
            graph.drawRect(cleaningRobot.getPos().convertX(xOffset, zoom) - radius / 2, cleaningRobot.getPos().convertY(yOffset, zoom) - radius / 2, size, size);
        }
    }

}
