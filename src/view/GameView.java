package view;

import model.Robot;
import model.Track;

import javax.swing.*;
import java.awt.*;

/**
 * Játék kinézet
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class GameView extends JPanel {

    /**
     * Pálya kinézete
     */
    private TrackView trackView;

    /**
     * Státusz bár nézete
     */
    private HudView hudView;

    /**
     * Konstruktor.
     *
     * @param t              a pálya amin a játék folyik
     * @param gameController a game controller
     */
    public GameView(Track t, GameController gameController) {
        super();

        setLayout(new BorderLayout());

        trackView = new TrackView(t, gameController);
        add(trackView, BorderLayout.CENTER);
        invalidate();
        setVisible(true);
    }

    /**
     * Új kör esetén meghívandó függvény
     *
     * @param actualPlayer a soron lévő robot
     */
    public void newRound(Robot actualPlayer) {
        hudView.refreshStandings();
        hudView.setCurrent(actualPlayer);
        trackView.centerActualPlayer(actualPlayer);
        trackView.invalidate();
    }

    /**
     * Játék újra rajzolása
     */
    public void redraw() {
        trackView.repaint();
    }

    /**
     * Notification kiírása a hudra
     *
     * @param str a kiírandó notification
     */
    public void notifyHud(String str) {
        hudView.showNotification(str);
    }

    /**
     * Pálya elem hozzáadása a pálye nézethez.
     *
     * @param tobv a pálya elem a mit hozzákell adni.
     */
    public void addItem(TrackObjectBaseView tobv) {
        trackView.addItem(tobv);
    }

    /**
     * Pálya elem eltávolítása a pályáról.
     *
     * @param tobv a pályaelem amit el kell távolítani.
     */
    public void removeItem(TrackObjectBaseView tobv) {
        trackView.removeItem(tobv);
    }

    /**
     * HUD beállítása
     *
     * @param hudView a HUD
     */
    public void setHudView(HudView hudView) {
        this.hudView = hudView;
        add(hudView, BorderLayout.EAST);
        invalidate();
    }

    /**
     * Getter
     *
     * @return a pálya nézet
     */
    public TrackView getTrackView() {
        return trackView;
    }
}
