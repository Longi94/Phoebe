package view;

import model.Pickup;

/**
 * Egy pickup objektum kinézete
 *
 * A példányosodó pickup objektum létrehozza a saját nézetét, amelynek mintája ez az osztály
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class PickupView {

    /**
     * Pickup modell referencia
     */
    private Pickup pickup;

    /**
     * PickupView konstruktor
     *
     * Beállítja a pickup modellt
     *
     * @param pickup A pickup modell referencia
     */
    public PickupView(Pickup pickup) {
        this.pickup = pickup;
    }

}
