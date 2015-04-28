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
    private JPanel buttonPanel;
    private JPanel statusPanel;
    private JPanel currentPlayerPanel;
    private JPanel postsPanel;

    private String current;

    public HudView() {
        initButtonPanel();
        add(buttonPanel);

        initStatusPanel();
        add(statusPanel);

        initCurrentPlayerPanel();
        add(currentPlayerPanel);

        initPostsPanel();
        add(postsPanel);

    }

    public void refreshStandings() {

    }

    public void setCurrent(Robot actualPlayer) {
        current = actualPlayer.getName();
    }

    public void showNotification(String notif) {

    }

    private void initButtonPanel(){

    }

    private void initStatusPanel(){

    }

    private void initCurrentPlayerPanel(){

    }

    private void initPostsPanel(){

    }

}
