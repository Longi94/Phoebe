package view;

import model.Robot;
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
    private Track track;

    private Graphics graph;

    private double xOffset, yOffset;

    private double zoom;

    /**
     * Pályán lévő elemek kinézetei
     */
    List<TrackObjectBaseView> trackObjectBaseViews;

    void centerActualPlayer(Robot robot) {
        //zoom változatlan, offset úgy módosul, hogy a robot középre kerüljön
    }

    public void drawTrack() {
        //pályahatárok kirajzolása
        updateItems();
    }

    public void updateItems() {
        for (TrackObjectBaseView tobw : trackObjectBaseViews) {
            tobw.draw(graph, xOffset, yOffset, zoom);
        }
    }

}
