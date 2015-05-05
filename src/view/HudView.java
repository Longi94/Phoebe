package view;

import model.Robot;
import model.basic.Position;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Jobb oldali ranglista kinézet
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class HudView extends JPanel {
    private List<Robot> players;

    private String current;
    private JToggleButton oilButton;
    private JToggleButton puttyButton;
    private JLabel currentPlayerLabel;

    private HistoryPanel historyPanel;

    // TODO kipróbálni az egészet hogy hogy néz ki, jó-e egyáltalán
    public HudView(List<Robot> players) {

        this.players = players;

        players.add(new Robot(new Position(0, 0), null, "ASD1"));
        players.add(new Robot(new Position(0, 0), null, "ASD2"));
        players.add(new Robot(new Position(0, 0), null, "ASD3"));

        setLayout(new GridBagLayout());

        setBorder(new LineBorder(Color.BLACK, 3));

        initComponents();
    }

    public void refreshStandings() {

    }

    public void setCurrent(Robot actualPlayer) {
        current = actualPlayer.getName();
        currentPlayerLabel.setText(current);
        invalidate();
    }

    public void showNotification(String notif) {

    }

    private void initComponents() {
        oilButton = new JToggleButton("Oil");
        puttyButton = new JToggleButton("Putty");
        JButton forfeitButton = new JButton("Forfeit");

        add(oilButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(puttyButton, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(forfeitButton, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        forfeitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        JLabel statusLabel = new JLabel("Player status");
        statusLabel.setBorder(new LineBorder(Color.PINK, 2));
        add(statusLabel, new GridBagConstraints(0, 1, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        for (int i = 0; i < players.size(); i++) {
            Robot robot = players.get(i);
            JLabel playerLabel = new JLabel(robot.getName());
            JLabel playerStatusLabel = new JLabel("12.3");
            playerLabel.setBorder(new LineBorder(Color.PINK, 2));
            playerStatusLabel.setBorder(new LineBorder(Color.PINK, 2));
            add(playerLabel, new GridBagConstraints(0, i + 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            add(playerStatusLabel, new GridBagConstraints(2, i + 2, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        }

        JLabel currentLabel = new JLabel("Current Player");
        currentLabel.setBorder(new LineBorder(Color.PINK, 2));
        add(currentLabel, new GridBagConstraints(0, players.size() + 3, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        currentPlayerLabel = new JLabel("None");
        currentPlayerLabel.setBorder(new LineBorder(Color.PINK, 2));
        add(currentPlayerLabel, new GridBagConstraints(0, players.size() + 4, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        JLabel historyLabel = new JLabel("History");
        historyLabel.setBorder(new LineBorder(Color.PINK, 2));
        add(historyLabel, new GridBagConstraints(0, players.size() + 5, 2, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        JButton testButton = new JButton("Test Notifications");
        add(testButton, new GridBagConstraints(2, players.size() + 5, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        historyPanel = new HistoryPanel();
        historyPanel.setBorder(new LineBorder(Color.GREEN, 2));
        add(historyPanel, new GridBagConstraints(0, players.size() + 6, 3, 1, 1, 35 - 5 - players.size(), GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension((getParent().getWidth() - getParent().getHeight()) / 2, getParent().getHeight()));
        invalidate();
    }

    private class HistoryPanel extends JPanel {

        private int testI = 0;
        private List<String> posts;

        public void showNotification(String notif) {

        }

        public void testNotification() {

        }
    }
}
