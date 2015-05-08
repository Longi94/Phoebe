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

    public static final Color TRACK_FILL_COLOR = new Color(150,150,150);
    public static final Color START_LINE_COLOR = new Color(255,255,255);

    /**
     * Pálya kinézete
     */
    private Track track;

    private Graphics graph;

    private double xOffset, yOffset;

    private double zoom;

    private Position mouseDragStart;
    private Position mouseDragEnd;

    private GameController gameController;

    /**
     * Pályán lévő elemek kinézetei
     */
    private List<TrackObjectBaseView> trackObjectBaseViews;

    public TrackView(Track t, GameController gameController) {
        xOffset = -2.5;
        yOffset = -2.5;
        zoom = 40;
        track = t;
        trackObjectBaseViews = new ArrayList<TrackObjectBaseView>();

        //TODO VALAHOGY AZT IS MEG KELL OLDANI, HA A JÁTÉKOS NEM AKAR VÁLTOZTATNI A VEKTORON
        addMouseListener(this);
        addMouseMotionListener(this);

        this.gameController = gameController;
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

    private void drawStartLine(Position in, Position out) {
        graph.setColor(START_LINE_COLOR);

        graph.drawLine(in.convertX(xOffset,zoom),in.convertY(yOffset,zoom),out.convertX(xOffset,zoom),out.convertY(xOffset,zoom));
        graph.setColor(TRACK_FILL_COLOR);
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

        drawStartLine(inArc.get(0), outArc.get(0));

        //pályaobjektumok kirajzolása
        for (TrackObjectBaseView tobw : trackObjectBaseViews) {
            tobw.draw(graph, xOffset, yOffset, zoom);
        }

        graph.setColor(Color.BLACK);
        if (mouseDragStart != null && mouseDragEnd!= null) {
            graph.drawLine((int) mouseDragStart.getX(), (int) mouseDragStart.getY(), (int) mouseDragEnd.getX(), (int) mouseDragEnd.getY());

            int deltaY = (int)mouseDragEnd.getY() - (int)mouseDragStart.getY();
            int deltaX = (int)mouseDragEnd.getX() - (int)mouseDragStart.getX();

            graph.drawString("" + (int) Math.toDegrees(Math.atan2(deltaY, deltaX)), (int) mouseDragStart.getX(), (int) mouseDragStart.getY());
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
        //Unused
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (mouseDragStart == null)
            mouseDragStart = new Position(e.getX(), e.getY());
        else {
            mouseDragStart.setX(e.getX());
            mouseDragStart.setY(e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (mouseDragStart != null && mouseDragEnd!= null) {
            int deltaY = (int) mouseDragEnd.getY() - (int) mouseDragStart.getY();
            int deltaX = (int) mouseDragEnd.getX() - (int) mouseDragStart.getX();

            gameController.jumpCurrentPlayer((int) Math.toDegrees(Math.atan2(deltaY, deltaX)));
        }

        mouseDragStart = null;
        mouseDragEnd = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Unused
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Unused
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseDragEnd == null)
            mouseDragEnd = new Position(e.getX(), e.getY());
        else {
            mouseDragEnd.setX(e.getX());
            mouseDragEnd.setY(e.getY());
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //Unused
    }
}
