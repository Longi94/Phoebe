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

    public PuttyView(Putty putty) {
        this.putty = putty;
        putty.setTobv(this);
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {

    }

}
