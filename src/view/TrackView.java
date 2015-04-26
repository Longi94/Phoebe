package view;

import model.Robot;
import model.Track;

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
    private List<TrackObjectBaseView> trackObjectBaseViews;

    void centerActualPlayer(Robot robot) {
        //zoom változatlan, offset úgy módosul, hogy a robot középre kerüljön
    }

    public void drawTrack() {
        //pályahatárok kirajzolása
        for (TrackObjectBaseView tobw : trackObjectBaseViews) {
            tobw.draw(graph, xOffset, yOffset, zoom);
        }

    }


    public void addItem(TrackObjectBaseView tobv) {
        trackObjectBaseViews.add(tobv);
    }

    public void removeItem(TrackObjectBaseView tobv) {
        trackObjectBaseViews.remove(tobv);
    }

}
