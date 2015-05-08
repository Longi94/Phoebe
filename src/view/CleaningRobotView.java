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

    }

}
