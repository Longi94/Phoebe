package view;

import model.Track;
import model.TrackObjectBase;

import java.awt.*;
import java.util.List;

/**
 * Pálya kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class TrackView {

    /**
     * Pálya kinézete
     */
    Track track;

    /**
     * Pályán lévő elemek kinézetei
     */
    List<TrackObjectBaseView> trackObjectBaseViews;

    public void drawTrack(Graphics graph) {
        //pályahatárok kirajzolása
    }

    public void updateItems(Graphics graph) {
        for (TrackObjectBaseView tobw : trackObjectBaseViews) {
            tobw.draw(graph);
        }
    }

}
