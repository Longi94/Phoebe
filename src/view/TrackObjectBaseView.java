package view;

import model.TrackObjectBase;

import java.awt.*;

/**
 * Pálya objektumok kinézete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public abstract class TrackObjectBaseView {

    //Referencia az objhektumra amit ki kell rajzolni.
    private TrackView tv;

    /**
     * Konstruktor
     *
     * @param tob az objektum amihez a view tartozik
     * @param tv  a pálya nézet amire ki kell rajzolni
     */
    public TrackObjectBaseView(TrackObjectBase tob, TrackView tv) {
        this.tv = tv;
        tob.setTobv(this);
    }

    /**
     * Az objektum eltávolítása a pályáról
     */
    public void removeFromTrack() {
        tv.removeItem(this);
    }

    /**
     * Objektum kirajzolása.
     *
     * @param graph   amire rajzol
     * @param xOffset x eltolás
     * @param yOffset y eltolás
     * @param zoom    nagyítás
     */
    public void draw(Graphics graph, double xOffset, double yOffset, double zoom) {

    }

}
