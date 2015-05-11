package view;

import model.Putty;

import java.awt.*;

/**
 * Ragacs kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class PuttyView extends TrackObjectBaseView {

    //Referencia az objhektumra amit ki kell rajzolni.
    private Putty putty;

    /**
     * Construktor
     * @param putty az objektum amihez a view tartozik
     * @param tv a pálya nézet amire ki kell rajzolni
     */
    public PuttyView(Putty putty, TrackView tv) {
        super(putty, tv);
        this.putty = putty;
    }

    /**
     * Ragacs kirajzolása.
     * @param graph amire rajzol
     * @param xOffset x eltolás
     * @param yOffset y eltolás
     * @param zoom nagyítás
     */
    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {
        graph.setColor(new Color(103, 140, 0));
        int radius = (int) (putty.getRadius() * zoom * 2);
        graph.fillOval(putty.getPos().convertX(xOffset, zoom) - radius / 2, putty.getPos().convertY(yOffset, zoom) - radius / 2,
                radius, radius);
        graph.setColor(Color.ORANGE);
        graph.drawString("" + putty.getHitsLeft(), putty.getPos().convertX(xOffset + 2*putty.getRadius(), zoom), putty.getPos().convertY(yOffset - putty.getRadius()/2, zoom));
    }

}
