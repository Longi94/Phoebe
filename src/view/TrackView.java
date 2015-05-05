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

    private void drawField(Position inS, Position inE, Position outS, Position outE) {
        Polygon p = new Polygon();
        p.addPoint(inS.convertX(xOffset,zoom),inS.convertY(yOffset,zoom));
        p.addPoint(inE.convertX(xOffset,zoom),inE.convertY(yOffset,zoom));
        p.addPoint(outE.convertX(xOffset,zoom),outE.convertY(yOffset,zoom));
        p.addPoint(outS.convertX(xOffset,zoom),outS.convertY(yOffset,zoom));

        graph.fillPolygon(p);

    }

    public void drawTrack() {
        //pályahatárok kirajzolása
        List<Position> inArc = track.getInnerArc();
        List<Position> outArc = track.getOuterArc();
        for (int i =  0; i<inArc.size();i++) {
            Position inS = inArc.get(i);
            Position inE = inArc.get((i + 1) % inArc.size());
            Position outS = outArc.get(i);
            Position outE = outArc.get((i + 1) % inArc.size());
            drawField(inS,inE,outS,outE);
        }


        //pályaobjektumok kirajzolása
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
