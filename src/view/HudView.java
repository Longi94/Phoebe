package view;

import model.Robot;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JToggleButton oilButton;
    private JToggleButton puttyButton;

    // TODO kipróbálni az egészet hogy hogy néz ki, jó-e egyáltalán
    public HudView() {
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

        invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension((getParent().getWidth() - getParent().getHeight()) / 2, getParent().getHeight()));
        invalidate();
    }
}
