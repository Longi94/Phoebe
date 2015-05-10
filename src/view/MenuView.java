package view;

import model.*;
import model.Robot;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Menü panel
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class MenuView extends JPanel implements ActionListener {

    public static final Color[] PLAYER_COLORS = {
            new Color(255, 153, 153),
            new Color(153, 255, 153),
            new Color(153, 255, 204),
            new Color(255, 255, 153),
            new Color(204, 153, 255),
            new Color(204, 204, 204)
    };

    /**
     * Pálya lista legördülő menü
     */
    private JComboBox trackList;

    /**
     * Körök száma spinner
     */
    private JSpinner roundsSpinner;

    /**
     * Játékosok neveinek beviteli mezői
     */
    private JTextField[] playerFields;

    /**
     * Menu view konstruktor
     */
    public MenuView() {
        initComponents();
    }

    /**
     * A menü kinézet komponensek inicializálása
     */
    private void initComponents() {

        /**
         * PÁLYABEÁLLÍTÁS INICIALIZÁLÁSA
         */

        // Label elem a pályabeállításokhoz
        JLabel trackLabel = new JLabel("Track");

        // Lista elem az elérhető pályákhoz
        trackList = new JComboBox();

        // Elérhető pályák beállítása a listához
        trackList= new JComboBox<String>(GameController.getAvailableTrackNames());

        // A label beállítása a listához
        trackLabel.setLabelFor(trackList);



        /**
         * KÖRÖK BEÁLLÍTÁSÁNAK INICIALIZÁLÁSA
         */

        // Label a körök beállításához
        JLabel roundsLabel = new JLabel("Rounds");

        // Spinner a körök számának beállításához
        roundsSpinner = new JSpinner();

        // Roundspinner inicializálása a minimum és  maximum és az aktuális körszámokkal
        roundsSpinner.setModel(new SpinnerNumberModel(MainWindow.DEFAULT_TURN_NUMBER, MainWindow.MINIMUM_TURN_NUMBER, MainWindow.MAXIMUM_TURN_NUMBER, 1));

        // Label beállítása a körbeállítás spinnerhez
        roundsLabel.setLabelFor(roundsSpinner);


        /**
         * JÁTÉKOSOK BEÁLLÍTÁSÁNAK INICIALIZÁLÁSA
         */

        // Bemeneti mezők inicializálása
        playerFields = new JTextField[MainWindow.MAX_PLAYER_NUMBER];

        // Mezőkhoz tartozó labelek
        JLabel playerLabels[] = new JLabel[MainWindow.MAX_PLAYER_NUMBER];

        // Mezők feltöltése
        for(int i=0; i < playerFields.length; i++) {
            playerLabels[i] = new JLabel("Player " + i);
            playerFields[i] = new JTextField("",15);
            playerFields[i].setBackground(PLAYER_COLORS[i]);
            playerFields[i].setHorizontalAlignment(JTextField.CENTER);
            playerFields[i].setToolTipText("");
        }


        /**
         * START GOMB INICIALIZÁLÁSA
         */

        // Start gomb inicializálása
        JButton startButton = new JButton("Start");

        // Start gomb megnomása esetén a saját action listener függvény fut le
        startButton.addActionListener(this);



        /**
         * NÉZET ÉS ELEMEK HOZZÁADÁSA A MENÜ NÉZETHEZ
         */

        // Kinézet
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));

        // Menü panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0,2));

        // Menü panel hozzáadása
        add(menuPanel,BorderLayout.NORTH);

        // Pályakiválasztás hozzáadása
        menuPanel.add(trackLabel);
        menuPanel.add(trackList);

        // Körök beállításának hozzáadása
        menuPanel.add(roundsLabel);
        menuPanel.add(roundsSpinner);

        // Játékosok hozzáadása
        for(int i = 0; i < MainWindow.MAX_PLAYER_NUMBER; i++) {
            menuPanel.add(playerLabels[i]);
            menuPanel.add(playerFields[i]);
        }

        // Start gomb hozzáadása
        add(startButton,BorderLayout.SOUTH);

    }

    /**
     * Start gomb megynomására van állítva ez az esemény lekezelő függvény
     *
     * @param e az esemény
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Kiválasztott pálya neve
        String selectedMapFile = String.valueOf("assets/maps/" + trackList.getSelectedItem() + ".map");

        // Kiválasztott körök száma
        int numberOfRounds = Integer.parseInt(String.valueOf(roundsSpinner.getValue()));

        // Ebbe számoljuk, hogy hány játékosnév lett beírva
        int numOfPlayers = 0;

        // Beírt robotneveket tartalmazó lista
        ArrayList<String> players = new ArrayList<String>();

        // Megnézzük, hogy hány játékos neve lett beírva és beletesszük őket egy robot listbe
        for(int i=0; i < MainWindow.MAX_PLAYER_NUMBER; i++) {
            if(playerFields[i].getText() != null && !playerFields[i].getText().equals("")) {
                players.add(playerFields[i].getText());
                numOfPlayers++;
            }
            else {
                players.add(null);
            }
        }

        // Ha nem lett megfelelő számú játékosnév beírva, akkor figyelmeztetés. Különben indul a játék
        if (numOfPlayers >= MainWindow.MIN_PLAYER_NUMBER &&  numOfPlayers <= MainWindow.MAX_PLAYER_NUMBER) {
            new GameController(selectedMapFile, players, numberOfRounds);
        }
        else {
            JOptionPane.showMessageDialog(MainWindow.getInstance(),
                    "You can start the game with minimum " + MainWindow.MIN_PLAYER_NUMBER + " players",
                    "Player number error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
