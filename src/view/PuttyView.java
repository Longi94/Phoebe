package view;

import model.Putty;

/**
 * Egy lerakott ragacs objektum kinézete
 *
 * A példányosodó ragacs objektum létrehozza a saját nézetét, amelynek mintája ez az osztály
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class PuttyView {

    /**
     * Putty modell referencia
     */
    private Putty putty;

    /**
     * PuttyView konstruktor
     *
     * Beállítja a putty modell referenciát
     *
     * @param putty Putty modell referencia
     */
    public PuttyView(Putty putty) {
        this.putty = putty;
    }

}
