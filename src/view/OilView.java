package view;

import model.Oil;

import java.awt.*;

/**
 * Olaj kinézete
 */
public class OilView extends TrackObjectBaseView {

    //Referencia az objhektumra amit ki kell rajzolni.
    private Oil oil;

    /**
     * Konstruktor
     *
     * @param oil az objektum amihez a view tartozik
     * @param tv  a pálya nézet amire ki kell rajzolni
     */
    public OilView(Oil oil, TrackView tv) {
        super(oil, tv);
        this.oil = oil;
    }

    /**
     * Olaj kirajzolása.
     *
     * @param graph   amire rajzol
     * @param xOffset x eltolás
     * @param yOffset y eltolás
     * @param zoom    nagyítás
     */
    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(new Color(0, 0, 0));


        int radius = (int) (oil.getRadius() * zoom * 2);
        graph.fillOval(oil.getPos().convertX(xOffset, zoom) - radius / 2, oil.getPos().convertY(yOffset, zoom) - radius / 2,
                radius, radius);

        graph.setColor(Color.RED);
        graph.drawString("" + oil.getRoundsLeft(), oil.getPos().convertX(xOffset + oil.getRadius(), zoom), oil.getPos().convertY(yOffset + oil.getRadius(), zoom));
    }

}
