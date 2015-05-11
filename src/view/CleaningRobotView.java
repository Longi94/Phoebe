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

    //Referencia az objhektumra amit ki kell rajzolni.
    private CleaningRobot cleaningRobot;

    /**
     * Construktor
     *
     * @param cleaningRobot az objektum amihez a view tartozik
     * @param tv            a pálya nézet amire ki kell rajzolni
     */
    public CleaningRobotView(CleaningRobot cleaningRobot, TrackView tv) {
        super(cleaningRobot, tv);
        this.cleaningRobot = cleaningRobot;
    }

    /**
     * Takarító robot kirajzolása.
     *
     * @param graph   amire rajzol
     * @param xOffset x eltolás
     * @param yOffset y eltolás
     * @param zoom    nagyítás
     */
    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(new Color(255, 255, 255));
        int radius = (int) (cleaningRobot.getRadius() * zoom * 2);
        graph.fillOval(cleaningRobot.getPos().convertX(xOffset, zoom) - radius / 2, cleaningRobot.getPos().convertY(yOffset, zoom) - radius / 2,
                radius, radius);

        graph.setColor(new Color(255, 255, 0));
        int size = (int) (cleaningRobot.getRadius() * 0.707 * zoom);

        int x = cleaningRobot.getPos().convertX(xOffset - 0.06, zoom) - radius / 2;
        int y = cleaningRobot.getPos().convertY(yOffset - 0.06, zoom) - radius / 2;
        if (cleaningRobot.getActuallyCleaning() != null) {
            graph.fillRect(x, y, 2 * size, 2 * size);
        } else {
            graph.drawRect(x, y, 2 * size, 2 * size);
        }
    }

}
