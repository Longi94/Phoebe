package view;

import javax.swing.*;
import java.awt.*;

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
     * Kiválasztott pálya neve (kiterjesztéssel együtt)
     */
    String selectedMap;

    public MenuView() {

        // Pálya választó megjelenítése
        initSelectTrackPanel();
        add(selectTrackPanel);

        // Játékosok panel megjelenítése
        initPlayersPanel();
        add(playersPanel);

        // Start panel megjelenítése
        initStartPanel();
        add(startPanel);
    }

    public void initSelectTrackPanel() {

        selectTrackPanel = new JPanel();

        String[] trackListArray = GameController.getAvailableTracks();

        if(trackListArray == null) {
            trackListArray = new String[1];
            trackListArray[0] = "No track file";
        }

        JComboBox<String> trackList= new JComboBox<String>(trackListArray);

        selectTrackPanel.add(trackList);

    }

    public void initPlayersPanel() {

        playersPanel = new JPanel();



    }

    public void initStartPanel() {

        startPanel = new JPanel(new BorderLayout());

        JButton startButton = new JButton("Start");
        startPanel.add(startButton,BorderLayout.CENTER);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Háttérkép rajzolása
        Image background = Toolkit.getDefaultToolkit().createImage("assets/img/menu_background.jpg");
        g.drawImage(background, 0, 0, null);
    }

}
