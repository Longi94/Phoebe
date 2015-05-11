package view;

import javax.swing.*;
import java.awt.*;

/**
 * A játék ablak kerete
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class MainWindow extends JFrame {

    /**
     * Program szintű konstansok.
     */
    public static final int DEFAULT_TURN_NUMBER = 20;
    public static final int MINIMUM_TURN_NUMBER = 5;
    public static final int MAXIMUM_TURN_NUMBER = 40;
    public static final int MIN_PLAYER_NUMBER = 2;
    public static final int MAX_PLAYER_NUMBER = 6;

    /**
     * Ablak példány.
     */
    public static MainWindow instance = null;

    /**
     * Konstruktor.
     */
    protected MainWindow() {
        super();
        // Menü nézet hozzáadása az ablakhoz
        add(new MenuView());
        pack();
    }

    /**
     * Példány lekérése
     *
     * @return ablak példány
     */
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    /**
     * Menü kinézet
     */
    private MenuView menuView;

    /**
     * Játék kinézet
     */
    private view.GameController controller;

    /**
     * Controller lekérése.
     *
     * @return a controller
     */
    public GameController getController() {
        return controller;
    }

    /**
     * Controller beállítása.
     *
     * @param controller a controller
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Grafikus program belépési pont
     *
     * @param args paraméterek
     */
    public static void main(String[] args) {

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                // Új ablak létrehozása
                MainWindow window = MainWindow.getInstance();

                // Ablak megjelenítési beállításai
                window.displaySetUp();

                // Megjelenítés
                window.setVisible(true);
            }
        });

    }

    /**
     * Az ablak beállítása.
     */
    public void displaySetUp() {

        // Ablak címe
        setTitle("Phoebe by Passz");

        // Kilépés x megnyomása esetén
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Átméretezés letiltása
        setResizable(false);

        // Ablak középre igazítása a képrenyőn
        setLocationRelativeTo(null);
    }

}
