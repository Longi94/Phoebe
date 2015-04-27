package view;

import model.Robot;

import javax.swing.*;

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

    public void newRound(Robot actualPlayer) {
        hudView.refreshStandings();
        hudView.setCurrent(actualPlayer);
        trackView.centerActualPlayer(actualPlayer);
        trackView.drawTrack();
    }

    public void redraw() {
        trackView.drawTrack();
    }

    public void notifyHud(String str) {
        hudView.showNotification(str);
    }

    public void addItem(TrackObjectBaseView tobv) {
        trackView.addItem(tobv);
    }

}
