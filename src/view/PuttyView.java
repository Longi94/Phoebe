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
        graph.fillOval(putty.getPos().convertX(xOffset, zoom), putty.getPos().convertY(yOffset, zoom),
                (int) (putty.getRadius() * zoom), (int) (putty.getRadius() * zoom));
        graph.setColor(Color.RED);
        graph.drawString("" + putty.getHitsLeft(), putty.getPos().convertX(xOffset,zoom),putty.getPos().convertY(yOffset,zoom));
    }

}
