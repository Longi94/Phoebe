package view;

import model.Oil;

/**
 * Egy lerakott olaj objektum kinézete
 *
 * A példányosodó olaj objektum létrehozza a saját nézetét, amelynek mintája ez az osztály
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class OilView {

    /**
     * Olaj modell referencia
     */
    private Oil oil;

    /**
     * OlajView konstruktor
     *
     * Beállítja az oldaj modellt a kinézethez
     *
     * @param oil
     */
    public OilView(Oil oil) {
        this.oil = oil;
    }

}
