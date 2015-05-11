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


    public GameView(Track t, GameController gameController) {
        super();

        setLayout(new BorderLayout());

        trackView = new TrackView(t, gameController);
        add(trackView, BorderLayout.CENTER);
        invalidate();
        setVisible(true);
    }

    public void newRound(Robot actualPlayer) {
        hudView.refreshStandings();
        hudView.setCurrent(actualPlayer);
        trackView.centerActualPlayer(actualPlayer);
        trackView.invalidate();
    }

    public void redraw() {
        trackView.repaint();
    }

    public void notifyHud(String str) {
        hudView.showNotification(str);
    }

    public void addItem(TrackObjectBaseView tobv) {
        trackView.addItem(tobv);
    }

    public void removeItem(TrackObjectBaseView tobv) { trackView.removeItem(tobv); }

    public void setHudView(HudView hudView) {
        this.hudView = hudView;
        add(hudView, BorderLayout.EAST);
        invalidate();
    }

    public TrackView getTrackView() {
        return trackView;
    }
}
