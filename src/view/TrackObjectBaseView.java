package view;

import java.awt.*;

/**
 * Pálya objektumok kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public abstract class TrackObjectBaseView {

    private TrackView tv;

    public void removeFromTrack() {
        tv.removeItem(this);
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {

    }

}
