package model;

import model.basic.Position;
import skeleton.PhoebeLogger;
import view.MainWindow;
import view.PickupView;
import view.TrackObjectBaseView;
import view.TrackView;

import java.util.Random;

/**
 * Pickupot megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Pickup extends TrackObjectBase {

    //A pickup típusa (random:-1, oil:0, putty:1 TODO: diagramba módosítás
    private int type = -1;

    /**
     * Véletlen esemény ahhoz, hogy ragacs, vagy olaj sorsolódjon
     */
    private static Random random = new Random();

    /**
     * Konstruktor két paraméterrel
     * <p/>
     * Megkap egy poziciót és a pálya referenciát is
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Pickup(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * Konstruktor egy paraméterrel
     * <p/>
     * Megkap egy poziciót
     *
     * @param pos az objektum pozíciója
     */
    public Pickup(Position pos) {
        super(pos);
    }

    @Override
    public TrackObjectBaseView createView(TrackView t) {
        return new PickupView(this,t);
    }

    /**
     * Akkor hívódik meg, ha egy robot felszedi (ütközik vele)
     *
     * @param r a robot, ami rá ugrott
     */
    @Override
    public void collide(Robot r) {

        switch (type) {
            case -1:
                if (random.nextInt(2) == 1) {
                    PhoebeLogger.message("r", "addOil");
                    MainWindow.getInstance().getController().getHudView().showNotification(r.getName() + " got oil from the pickup");
                    r.addOil();
                } else {
                    PhoebeLogger.message("r", "addPutty");
                    MainWindow.getInstance().getController().getHudView().showNotification(r.getName() + " got putty from the pickup");
                    r.addPutty();
                }
                break;
            case 0:
                PhoebeLogger.message("r", "addOil");
                r.addOil();
                break;
            case 1:
                PhoebeLogger.message("r", "addPutty");
                r.addPutty();
                break;
            default:
                throw new IllegalStateException("Ismeretlen pickup típus: " + type);
        }
        //kitörli magát a pályáról
        PhoebeLogger.message("track", "removeObject", "this");
        track.removeObject(this);
        PhoebeLogger.returnMessage();
    }

    /**
     * Pickup típusának beállítása
     *
     * @param type random:-1, oil:0, putty:1
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Pickup olvasható formában való kiiratásához
     *
     * @return szép pickup
     */
    @Override
    public String toString() {
        return "Pickup{" +
                    super.toString() + "," +
                    "type: " + type +
                "}";
    }
}
