package view;

import model.Pickup;

import java.awt.*;

/**
 * Pickup kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class PickupView extends TrackObjectBaseView {

    private Pickup pickup;

    public PickupView(Pickup pickup, TrackView tv) {
        super(pickup, tv);
        this.pickup = pickup;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(Color.orange);


        int radius = (int) (pickup.getRadius() * zoom * 2);
        graph.fillOval(pickup.getPos().convertX(xOffset, zoom) - radius / 2, pickup.getPos().convertY(yOffset, zoom) - radius / 2,
                radius, radius);

        graph.setColor(Color.blue);
        graph.fillOval(pickup.getPos().convertX(xOffset, zoom) - radius / 4, pickup.getPos().convertY(yOffset, zoom) - radius / 4,
                radius/2, radius/2);
    }

}
