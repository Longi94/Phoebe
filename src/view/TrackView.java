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

    public static final Color TRACK_FILL_COLOR = new Color(100,100,100);

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

    public TrackView (Track t) {
        xOffset = 0;
        yOffset = -25;
        zoom = 4;
        track = t;
        trackObjectBaseViews = new ArrayList<TrackObjectBaseView>();
    }



    void centerActualPlayer(Robot robot) {
        //zoom változatlan, offset úgy módosul, hogy a robot középre kerüljön
    }

    private void drawField(Position inS, Position inE, Position outS, Position outE) {
        //FEEL FREE TO REFINE
        Polygon p = new Polygon();
        p.addPoint(inS.convertX(xOffset,zoom),inS.convertY(yOffset,zoom));
        p.addPoint(inE.convertX(xOffset,zoom),inE.convertY(yOffset,zoom));
        p.addPoint(outE.convertX(xOffset,zoom),outE.convertY(yOffset,zoom));
        p.addPoint(outS.convertX(xOffset,zoom),outS.convertY(yOffset,zoom));

        System.out.println("Poligon: ");
        System.out.println("X: " + inS.convertX(xOffset, zoom) + ", Y: " + inS.convertY(yOffset, zoom));
        System.out.println("X: " + inE.convertX(xOffset, zoom) + ", Y: " + inE.convertY(yOffset, zoom));
        System.out.println("X: " + outE.convertX(xOffset, zoom) + ", Y: " + outE.convertY(yOffset, zoom));
        System.out.println("X: " + outS.convertX(xOffset, zoom) + ", Y: " + outS.convertY(yOffset, zoom));

        graph.fillPolygon(p);

    }

    @Override
    protected void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        this.graph = graph;
        drawTrack();
    }

    public void drawTrack() {
        //pályahatárok kirajzolása
        graph.setColor(TRACK_FILL_COLOR);

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
