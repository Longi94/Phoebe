package view;

import model.Pickup;

import java.awt.*;

/**
 * Pickup kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class PickupView extends TrackObjectBaseView{

    private Pickup pickup;

    public PickupView(Pickup pickup) {
        super(pickup);
        this.pickup = pickup;
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {

    }

}
