package view;

import model.Putty;

import java.awt.*;

/**
 * Ragacs kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class PuttyView extends TrackObjectBaseView {

    private Putty putty;

    public PuttyView(Putty putty, TrackView tv) {
        super(putty,tv);
        this.putty = putty;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(new Color(103, 140, 0));
        int radius = (int) (putty.getRadius() * zoom * 2);
        graph.fillOval(putty.getPos().convertX(xOffset, zoom) - radius/2, putty.getPos().convertY(yOffset, zoom) - radius/2,
                radius, radius);
        graph.setColor(Color.RED);
        graph.drawString("" + putty.getHitsLeft(), putty.getPos().convertX(xOffset,zoom),putty.getPos().convertY(yOffset,zoom));
    }

}
