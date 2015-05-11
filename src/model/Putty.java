package model;

import model.basic.Position;
import skeleton.PhoebeLogger;
import view.MainWindow;
import view.PuttyView;
import view.TrackObjectBaseView;
import view.TrackView;

/**
 * Ragacsot megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23
 */
public class Putty extends Obstacle {

    /**
     * Konstruktor egy paraméterrel
     * <p/>
     * Csak poziciót kap
     *
     * @param pos a ragacs pozíciója
     */
    public Putty(Position pos) {
        super(pos);
    }

    /**
     * Konstruktor két paraméterrel
     * <p/>
     * Megkap egy poziciót és a pályát is
     *
     * @param pos   a ragacs pozíciója
     * @param track a pálya, amin a ragacs található
     */
    public Putty(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * Megfelezi a rajta áthaladó robot sebességét
     *
     * @param r a robot, aki áthalad az akadályon
     */
    @Override
    public void takeEffect(Robot r) {
        //megfelezi a sebességet, de lehetőség van annak változtatására
        PhoebeLogger.message("r", "halveVelocity");
        r.halveVelocity();

        MainWindow.getInstance().getController().getGameView().notifyHud(r.getName() + " stepped into a putty stain.");
        MainWindow.getInstance().getController().getGameView().notifyHud(r.getName() + " got his velocity halved");


        PhoebeLogger.returnMessage();
    }

    @Override
    protected int GET_MAXIMUM_HITS() {
        return 4;                       //négy robot ráugrása után a ragacs eltűnik a pályáról
    }

    @Override
    public TrackObjectBaseView createView(TrackView t) {
        return new PuttyView(this,t);
    }

    /**
     * Ragacs olvasható formában való kiiratásához
     *
     * @return olvasható ragacs
     */
    @Override
    public String toString() {
        return "Putty{" +
                super.toString() +
                "}";
    }
}