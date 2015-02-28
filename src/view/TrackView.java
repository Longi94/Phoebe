package view;

import model.Track;

/**
 * A pálya objektum kinézete
 *
 * A pálya objektum létrehozza a nézetét, amelynek mintája ez az osztály
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class TrackView {

    /**
     * Pálya modell referencia
     */
    private Track track;

    /**
     * TrackView konstruktor
     *
     * Beállítja a track modell referenciát
     *
     * @param track Pálya referencia
     */
    public TrackView(Track track) {
        this.track = track;
    }

}
