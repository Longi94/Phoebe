package view;

import model.Robot;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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

    // TODO kipróbálni az egészet hogy hogy néz ki, jó-e egyáltalán
    public HudView() {
        setLayout(new GridLayout(0, 1));

        setBorder(new LineBorder(Color.BLACK, 3));

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
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(new LineBorder(Color.PINK, 3));
    }

    private void initStatusPanel(){
        statusPanel = new JPanel();
        statusPanel.setBorder(new LineBorder(Color.PINK, 3));
    }

    private void initCurrentPlayerPanel(){
        currentPlayerPanel = new JPanel();
        currentPlayerPanel.setBorder(new LineBorder(Color.PINK, 3));
    }

    private void initPostsPanel(){
        postsPanel = new JPanel();
        postsPanel.setBorder(new LineBorder(Color.PINK, 3));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension((getParent().getWidth() - getParent().getHeight()) / 2, getParent().getHeight()));
        invalidate();
    }
}
