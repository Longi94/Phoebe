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
    private List<String> posts;
    private List<Robot> players;

    private String current;
    private JToggleButton oilButton;
    private JToggleButton puttyButton;

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
        add(statusLabel, new GridBagConstraints(0, 1, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension((getParent().getWidth() - getParent().getHeight()) / 2, getParent().getHeight()));
        invalidate();
    }
}
