package view;

import model.Robot;
import model.basic.Position;

import java.awt.*;

/**
 * Robot objektumok kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class RobotView extends TrackObjectBaseView {

    //Referencia az objhektumra amit ki kell rajzolni.
    private Robot robot;

    /**
     * Konstruktor
     *
     * @param r  az objektum amihez a view tartozik
     * @param tv a pálya nézet amire ki kell rajzolni
     */
    public RobotView(Robot r, TrackView tv) {
        super(r, tv);
        robot = r;
    }

    /**
     * Robot kirajzolása.
     *
     * @param graph   amire rajzol
     * @param xOffset x eltolás
     * @param yOffset y eltolás
     * @param zoom    nagyítás
     */
    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(robot.getColor());
        int radius = (int) (robot.getRadius() * zoom * 2);
        graph.fillOval(robot.getPos().convertX(xOffset, zoom) - radius / 2, robot.getPos().convertY(yOffset, zoom) - radius / 2,
                radius, radius);

        Position start = robot.getPos();

        for (Position pos : robot.getPreviousPath()) {
            graph.drawLine(start.convertX(xOffset, zoom), start.convertY(yOffset, zoom), pos.convertX(xOffset, zoom), pos.convertY(yOffset, zoom));
            start = pos;
        }
    }

}
