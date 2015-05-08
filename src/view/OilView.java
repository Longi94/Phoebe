package view;

import model.Oil;

import java.awt.*;

/**
 * Olaj kinézete
 */
public class OilView extends TrackObjectBaseView {

    private Oil oil;

    public OilView(Oil oil, TrackView tv) {
        super(oil, tv);
        this.oil = oil;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(new Color(0, 0, 0));


        int radius = (int) (oil.getRadius() * zoom);
        graph.fillOval(oil.getPos().convertX(xOffset, zoom) - radius/2, oil.getPos().convertY(yOffset, zoom) - radius/2,
                radius, radius);

        graph.setColor(Color.RED);
        graph.drawString("" + oil.getRoundsLeft(), oil.getPos().convertX(xOffset,zoom),oil.getPos().convertY(yOffset,zoom));
    }

}
