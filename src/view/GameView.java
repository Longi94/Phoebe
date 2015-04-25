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
    TrackView trackView;

    /**
     * Ranglista nézete
     */
    HudView hudView;

    public void newRound(Robot actualPlayer) {
        hudView.refreshStandings();
        trackView.centerActualPlayer(actualPlayer);
        trackView.drawTrack();
        trackView.updateItems();
    }

    public void notifyHud(String str) {
        hudView.showNotification(str);
    }

}
