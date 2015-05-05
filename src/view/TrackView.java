package view;

import model.Robot;
import model.Track;
import model.basic.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pálya kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class TrackView extends JPanel {

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

    private void drawArc(ArrayList<Position> arc) {
        for (int i =  0; i<arc.size();i++) {
            Position ap = arc.get(i);
            Position np = arc.get((i + 1) % arc.size());
            graph.drawLine(ap.convertX(xOffset,zoom),ap.convertY(yOffset,zoom),
                    np.convertX(xOffset,zoom), np.convertY(yOffset,zoom));
        }
    }

    public void drawTrack() {

        graph.drawLine(1,2,3,4);
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
