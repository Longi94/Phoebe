package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Menü panel
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class MenuView extends JPanel {

    /**
     * Pályaválasztó menürész
     */
    private JPanel selectTrackPanel;

    /**
     * Játékos felsoroló menü rész
     */
    private JPanel playersPanel;

    /**
     * Játék indítása menürész
     */
    private JPanel startPanel;

    /**
     * A körök számát kiválasztó menürész
     */
    private JPanel roundsPanel;

    /**
     * Háttérkép
     */
    private BufferedImage background;

    /**
     * Kiválasztott pálya neve (kiterjesztéssel együtt)
     */
    String selectedMap;

    public MenuView() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // háttérkép beolvasása
        try {
            background = ImageIO.read(new File("assets/img/menu_background_new.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pálya választó megjelenítése
        initSelectTrackPanel();
        add(selectTrackPanel);

        initRoundsPanel();
        add(roundsPanel);

        // Játékosok panel megjelenítése
        initPlayersPanel();
        add(playersPanel);

        // Start panel megjelenítése
        initStartPanel();
        add(startPanel);
    }


    public void initSelectTrackPanel() {

        selectTrackPanel = new JPanel();
        selectTrackPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        selectTrackPanel.setOpaque(false);
        selectTrackPanel.setLayout(new FlowLayout());

        String[] trackListArray = GameController.getAvailableTracks();

        if(trackListArray == null) {
            trackListArray = new String[1];
            trackListArray[0] = "No track file";
        }


        JComboBox<String> trackList= new JComboBox<String>(trackListArray);

        JLabel trackLabel = new JLabel("Select track:");
        selectTrackPanel.add(trackLabel);
        selectTrackPanel.add(trackList);


    }

    private void initRoundsPanel() {
        roundsPanel = new JPanel();
        roundsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        roundsPanel.setOpaque(false);

        JLabel roundsLabel = new JLabel("Rounds:");
        JSpinner roundsSpinner = new JSpinner();
        SpinnerNumberModel model = new SpinnerNumberModel(20, 5, 40, 1);
        roundsSpinner.setModel(model);

        roundsPanel.add(roundsLabel);
        roundsPanel.add(roundsSpinner);
    }


    public void initPlayersPanel() {

        playersPanel = new JPanel();
        playersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        playersPanel.setOpaque(false);


    }

    public void initStartPanel() {

        startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        startPanel.setOpaque(false);

        JButton startButton = new JButton("Start");
        startPanel.add(startButton);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Háttérkép rajzolása
        g.drawImage(background, 0, 0, null);
    }

}
