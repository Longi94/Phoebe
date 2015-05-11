package view;

import model.Robot;
import model.Track;
import model.TrackObjectBase;
import model.basic.Position;
import model.basic.Velocity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pálya kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class TrackView extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

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

    private boolean robotDragged = false;

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

        for (TrackObjectBase tob : t.getItems()) {
            trackObjectBaseViews.add(tob.createView(this));
        }

        //TODO VALAHOGY AZT IS MEG KELL OLDANI, HA A JÁTÉKOS NEM AKAR VÁLTOZTATNI A VEKTORON
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        this.gameController = gameController;
    }



    void centerActualPlayer(Robot robot) {
        //zoom változatlan, offset úgy módosul, hogy a robot középre kerüljön
        xOffset = robot.getPos().getX() - getWidth() / 2 /zoom;
        yOffset = robot.getPos().getY() - getHeight() / 2/zoom;
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

        graph.drawLine(in.convertX(xOffset, zoom), in.convertY(yOffset, zoom), out.convertX(xOffset, zoom), out.convertY(yOffset, zoom));
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

        Robot robot = gameController.getActualPlayer();
        Position tempPos = new Position(robot.getPos().getX(), robot.getPos().getY());
        Velocity tempVel = new Velocity(robot.getVel().getAngle(), robot.getVel().getMagnitude());

        //A módosító sebesség vektor
        Velocity modVelocity = new Velocity();

        if (robotDragged && mouseDragStart != null && mouseDragEnd!= null) {
            int deltaY = (int)mouseDragEnd.getY() - (int)mouseDragStart.getY();
            int deltaX = (int)mouseDragEnd.getX() - (int)mouseDragStart.getX();
            int degrees = (int) Math.toDegrees(Math.atan2(deltaY, deltaX));
            if (degrees < 0) {
                degrees += 360;
            }

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (!(gameController.isGameStarted() && distance > zoom * gameController.getActualPlayer().getRadius())) {
                graph.setColor(Color.RED);
            } else {

                modVelocity.setAngle(Math.toRadians(degrees)); //ne feledjük hogy radián kell
                modVelocity.setMagnitude(degrees == -1 ? 0 : 1);

                graph.setColor(Color.BLACK);
            }

            graph.drawLine((int) mouseDragStart.getX(), (int) mouseDragStart.getY(), (int) mouseDragEnd.getX(), (int) mouseDragEnd.getY());

            graph.drawString("" + degrees, (int) mouseDragStart.getX(), (int) mouseDragStart.getY());
        } else {
            modVelocity.setMagnitude(0);
        }

        if (robot.isEnabled()) {
            tempVel.add(modVelocity);
        }

        tempPos.move(tempVel);
        graph.setColor(robot.getColor().brighter());
        int radius = (int) (robot.getRadius() * zoom * 2);
        graph.fillOval(tempPos.convertX(xOffset, zoom) - radius / 2, tempPos.convertY(yOffset, zoom) - radius / 2,
                radius, radius);
    }


    public void addItem(TrackObjectBaseView tobv) {
        trackObjectBaseViews.add(tobv);
    }

    public void removeItem(TrackObjectBaseView tobv) {
        trackObjectBaseViews.remove(tobv);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (gameController.isGameStarted() && SwingUtilities.isRightMouseButton(e)) {
            gameController.jumpCurrentPlayer(-1);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Position p = new Position(e.getX(), e.getY());
            Position playerPos = new Position (gameController.getActualPlayer().getPos().convertX(xOffset,zoom),gameController.getActualPlayer().getPos().convertY(yOffset, zoom));
            robotDragged = p.getDistance(playerPos) <= gameController.getActualPlayer().getRadius() * zoom;
            if (mouseDragStart == null)
                mouseDragStart = new Position(e.getX(), e.getY());
            else {
                mouseDragStart.setX(e.getX());
                mouseDragStart.setY(e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {

            if (mouseDragStart != null && mouseDragEnd != null) {
                int deltaY = (int) mouseDragEnd.getY() - (int) mouseDragStart.getY();
                int deltaX = (int) mouseDragEnd.getX() - (int) mouseDragStart.getX();
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if (robotDragged) {
                    robotDragged = false;
                    if (gameController.isGameStarted() && distance > zoom * gameController.getActualPlayer().getRadius()) {
                        int degrees = (int) Math.toDegrees(Math.atan2(deltaY, deltaX));
                        if (degrees < 0) {
                            degrees += 360;
                        }
                        gameController.jumpCurrentPlayer(degrees);
                    }

                } else {
                    robotDragged = false;
                }
            }

            mouseDragStart = null;
            mouseDragEnd = null;
            repaint();
        }
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
        if (SwingUtilities.isLeftMouseButton(e)) {

            Position mouseDragTemp = mouseDragEnd;

            mouseDragEnd = new Position(e.getX(), e.getY());

            if (!robotDragged) {
                if (mouseDragTemp != null) {
                    int deltaY = (int) mouseDragEnd.getY() - (int) mouseDragTemp.getY();
                    int deltaX = (int) mouseDragEnd.getX() - (int) mouseDragTemp.getX();

                    xOffset -= deltaX / zoom;
                    yOffset -= deltaY / zoom;
                }
            }
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //Unused
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        double oldzoom = zoom;
        zoom *= Math.pow(1.15, -1*e.getWheelRotation());
        repaint();
        xOffset /= zoom / oldzoom;
        yOffset /= zoom / oldzoom;
        //if (gameController.isGameStarted())
            //centerActualPlayer(gameController.getActualPlayer());
    }

}
