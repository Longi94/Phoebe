package view;

import model.Robot;
import model.Track;
import model.basic.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Pálya kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class TrackView extends JPanel implements MouseListener, MouseMotionListener {

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
        xOffset = -2.5;
        yOffset = -2.5;
        zoom = 40;
        track = t;
        trackObjectBaseViews = new ArrayList<TrackObjectBaseView>();

        addMouseListener(this);
        addMouseMotionListener(this);
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
