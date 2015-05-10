package view;

import model.Robot;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Jobb oldali ranglista kinézet (HUD)
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class HudView extends JPanel {

    /**
     * Játék kontroller
     */
    private GameController gameController;

    /**
     * Játszó robotok
     */
    private List<Robot> players;

    /**
     * Olaj lerakó gomb
     */
    private JToggleButton oilButton;

    /**
     * Ragacs lerakó gomb
     */
    private JToggleButton puttyButton;

    /**
     * Aktív robot
     */
    private Robot current;

    /**
     * Aktuális játékos név labelje
     */
    private JLabel currentPlayerLabel;

    /**
     * Játékos név labelek
     */
    private List<JLabel> playerNameLabels;

    /**
     * Játékosok megtett távjának labeljei
     */
    private List<JLabel> playerDistanceLabels;

    /**
     * Notificationokat megjelenítő rész
     */
    private HistoryPanel historyPanel;

    /**
     * Constructor
     *
     * @param players a játékosokat tartalmazó lista (le másolódik)
     */
    public HudView(List<Robot> players, GameController gameController) {

        // Játékosok és gamecontroller beállítása
        this.players = new ArrayList<Robot>(players);
        this.gameController = gameController;

        // Játékos nevek és megtett távok
        playerNameLabels = new ArrayList<JLabel>();
        playerDistanceLabels = new ArrayList<JLabel>();

        // GridBag layout
        setLayout(new GridBagLayout());

        // HUD elkülönítése
        setBackground(new Color(81,81,81));

        // Komponensek inicializálása
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

        // Játékosok rendezése
        Collections.sort(players);

        // Számoljunk..
        for (int i = 0; i < players.size(); i++) {
            playerNameLabels.get(i).setText(players.get(i).getName());
            playerDistanceLabels.get(i).setText("" + Math.round(players.get(i).getDistanceCompleted() * 100.0) / 100.0);
            playerNameLabels.get(i).setBorder(new LineBorder(players.get(i).getColor(), 2));
            playerDistanceLabels.get(i).setBorder(new LineBorder(players.get(i).getColor(), 2));
            currentPlayerLabel.setBorder(new LineBorder(players.get(i).getColor(), 2));
        }

        // Újrarajzolás
        invalidate();
    }

    /**
     * Soron lévő játékos beállítása és kiírása
     *
     * @param actualPlayer a soron lévő játékos
     */
    public void setCurrent(Robot actualPlayer) {
        current = actualPlayer;
        currentPlayerLabel.setText(current.getName() + " " + actualPlayer.getOilAmount() + "/" + actualPlayer.getPuttyAmount());
        currentPlayerLabel.setOpaque(true);
        currentPlayerLabel.setBorder(new EmptyBorder(5,5,5,5));
        currentPlayerLabel.setBackground(actualPlayer.getColor());
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
     * Elemek inicializálása
     */
    private void initComponents() {

        //Soron lévő játékos felirat
        JLabel currentLabel = new JLabel("Current Player");
        currentLabel.setOpaque(true);
        currentLabel.setBackground(new Color(40, 40, 46));
        currentLabel.setForeground(new Color(10, 181, 246));
        currentLabel.setBorder(new EmptyBorder(10,10,10,10));
        currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentLabel, new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        //Soron lévő játékos neve
        currentPlayerLabel = new JLabel("Unknown");
        add(currentPlayerLabel, new GridBagConstraints(0, 1, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        //Irnyító gombok hozzáadása
        oilButton = new JToggleButton("Oil");
        puttyButton = new JToggleButton("Putty");
        JButton forfeitButton = new JButton("Forfeit");
        add(oilButton, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        add(puttyButton, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
        add(forfeitButton, new GridBagConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

        // Olaj gomb lenyomását figyelő listener
        oilButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (current.getOilAmount() < 1) {
                    oilButton.setSelected(false);
                } else {
                    gameController.clickOil();
                }
            }
        });

        // Ragacsgomb lenyomásást figyelő listener
        puttyButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (current.getPuttyAmount() < 1) {
                    puttyButton.setSelected(false);
                } else {
                    gameController.clickPutty();
                }
            }
        });

        // Játék feladása
        forfeitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(MainWindow.getInstance(),"Are you sure you want to forfeit the game?","Forfeit",dialogButton);
                if(dialogResult == 0)
                    gameController.forfeitCurrentPlayer();
            }
        });

        //Allapot felirat
        JLabel statusLabel = new JLabel("Leaderboard");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(40, 40, 46));
        statusLabel.setForeground(new Color(10, 181, 246));
        statusLabel.setBorder(new EmptyBorder(10,10,10,10));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, new GridBagConstraints(0, 3, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        //Játékos status feliratok
        for (int i = 0; i < players.size(); i++) {
            Robot robot = players.get(i);

            // Játékos neve
            JLabel playerLabel = new JLabel(robot.getName());
            playerLabel.setOpaque(true);
            playerLabel.setBackground(robot.getColor());
            playerLabel.setBorder(new EmptyBorder(5,5,5,5));

            // Megtett táv
            JLabel playerStatusLabel = new JLabel("0.0");
            playerStatusLabel.setOpaque(true);
            playerStatusLabel.setBackground(robot.getColor());
            playerStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Hozzáad
            add(playerLabel, new GridBagConstraints(0, i + 4, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            add(playerStatusLabel, new GridBagConstraints(2, i + 4, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

            playerNameLabels.add(playerLabel);
            playerDistanceLabels.add(playerStatusLabel);
        }

        //History felirat
        JLabel historyLabel = new JLabel("History");
        historyLabel.setOpaque(true);
        historyLabel.setBackground(new Color(40, 40, 46));
        historyLabel.setForeground(new Color(10, 181, 246));
        historyLabel.setBorder(new EmptyBorder(10,10,10,10));
        historyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(historyLabel, new GridBagConstraints(0, players.size() + 5, 3, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        // A history panel
        historyPanel = new HistoryPanel();
        add(historyPanel, new GridBagConstraints(0, players.size() + 6, 3, 1, 1, 35 - 5 - players.size(), GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension((getParent().getWidth() - getParent().getHeight()) / 2, getParent().getHeight()));
        invalidate();
    }

    public void resetButtons() {
        oilButton.setSelected(false);
        puttyButton.setSelected(false);
    }

    /**
     * A history panel
     */
    private class HistoryPanel extends JPanel {

        private final DefaultListModel listModel;
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
