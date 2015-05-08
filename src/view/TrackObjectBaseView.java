package view;

import model.Track;
import model.TrackObjectBase;

import java.awt.*;

/**
 * Pálya objektumok kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public abstract class TrackObjectBaseView {

    private TrackView tv;

    public TrackObjectBaseView(TrackObjectBase tob, TrackView tv) {
        this.tv = tv;
        tob.setTobv(this);
    }

    public void removeFromTrack() {
        tv.removeItem(this);
    }

    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {

    }

}
