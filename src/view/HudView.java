package view;

import model.Robot;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Jobb oldali ranglista kinézet
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class HudView extends JPanel {

    private ArrayList<String> posts;

    private String current;

    public void refreshStandings() {

    }

    public void setCurrent(Robot actualPlayer) {
        current = actualPlayer.getName();
    }

    public void showNotification(String notif) {

    }



}
