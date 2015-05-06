package view;

import model.Robot;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Jobb oldali ranglista kinézet
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class HudView extends JPanel {
    private List<Robot> players;
    private List<JLabel> playerNameLabels;
    private List<JLabel> playerDistanceLabels;

    private String current;
    private JToggleButton oilButton;
    private JToggleButton puttyButton;
    private JLabel currentPlayerLabel;

    private HistoryPanel historyPanel;

    /**
     * Constructor
     *
     * @param players a játékosokat tartalmazó lista (le másolódik)
     */
    public HudView(List<Robot> players) {

        this.players = new ArrayList<Robot>(players);
        playerNameLabels = new ArrayList<JLabel>();
        playerDistanceLabels = new ArrayList<JLabel>();

        setLayout(new GridBagLayout());

        setBorder(new LineBorder(Color.BLACK, 3));

        initComponents();
    }

    public boolean isOilButtonDown() {
        return oilButton.isSelected();
    }

    public boolean isPuttyButtonDown() {
        return puttyButton.isSelected();
    }

    /**
     * Állás frissítése
     */
    public void refreshStandings() {

        Collections.sort(players);

        for (int i = 0; i < players.size(); i++) {
            playerNameLabels.get(i).setText(players.get(i).getName());
            playerDistanceLabels.get(i).setText("" + Math.round(players.get(i).getDistanceCompleted() * 100.0) / 100.0);
            currentPlayerLabel.setBorder(new LineBorder(players.get(i).getColor(), 2));
        }

        invalidate();
    }

    /**
     * Soron lévő játékos beállítása és kiírása
     *
     * @param actualPlayer a soron lévő játékos
     */
    public void setCurrent(Robot actualPlayer) {
        current = actualPlayer.getName();
        currentPlayerLabel.setText(current);
        currentPlayerLabel.setBorder(new LineBorder(actualPlayer.getColor(), 2));
        invalidate();
    }

    /**
     * Notification kiírása a history részbe
     *
     * @param notif a kiírando notification
     */
    public void showNotification(String notif) {
        if (historyPanel != null) {
            historyPanel.showNotification(notif);
        }
    }

    /**
     * A panel felépítése
     */
    private void initComponents() {
        //Irnyító gombok hozzáadása
        oilButton = new JToggleButton("Oil");
        puttyButton = new JToggleButton("Putty");
        JButton forfeitButton = new JButton("Forfeit");

        add(oilButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(puttyButton, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        add(forfeitButton, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        forfeitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO játék feladásának jováhagyása
            }
        });

        //Allapot felirat
        JLabel statusLabel = new JLabel("Player status");
        statusLabel.setBorder(new LineBorder(Color.BLACK, 2));
        add(statusLabel, new GridBagConstraints(0, 1, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        //Játékos status feliratok
        for (int i = 0; i < players.size(); i++) {
            Robot robot = players.get(i);
            JLabel playerLabel = new JLabel(robot.getName());
            JLabel playerStatusLabel = new JLabel("12.3");
            playerLabel.setBorder(new LineBorder(robot.getColor(), 2));
            playerStatusLabel.setBorder(new LineBorder(robot.getColor(), 2));
            add(playerLabel, new GridBagConstraints(0, i + 2, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            add(playerStatusLabel, new GridBagConstraints(2, i + 2, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

            playerNameLabels.add(playerLabel);
            playerDistanceLabels.add(playerStatusLabel);
        }

        //Soron lévő játékos felirat
        JLabel currentLabel = new JLabel("Current Player");
        currentLabel.setBorder(new LineBorder(Color.BLACK, 2));
        add(currentLabel, new GridBagConstraints(0, players.size() + 3, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        //Soron lévő játékos neve
        currentPlayerLabel = new JLabel("None");
        add(currentPlayerLabel, new GridBagConstraints(0, players.size() + 4, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        //History felirat
        JLabel historyLabel = new JLabel("History");
        historyLabel.setBorder(new LineBorder(Color.BLACK, 2));
        add(historyLabel, new GridBagConstraints(0, players.size() + 5, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        //A history panel
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

    /**
     * A history panel
     */
    private class HistoryPanel extends JPanel {

        private final DefaultListModel listModel;
        private int testI = 0;
        private JList<Object> list;
        private JScrollPane scrollPane;

        /**
         * Constructor
         */
        public HistoryPanel() {

            setLayout(new GridLayout());

            //Lista inicializálása
            listModel = new DefaultListModel();
            list = new JList<Object>(listModel);

            //Lehessen görgetni
            scrollPane = new JScrollPane(list);

            //Ez azért kell mert ez a szar átméreteződik amikor új elemet adok hozzá, így nem változik a mérete
            setPreferredSize(getSize());

            add(scrollPane, BorderLayout.CENTER);
            invalidate();
        }

        /**
         * Notification hozzáadása a listához
         *
         * @param notif a kiírandó notification
         */
        public void showNotification(String notif) {
            listModel.add(0, notif);
        }
    }
}
