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

    public static final int DEFAULT_TURN_NUMBER = 40;
    private static final int MAX_PLAYER_NUMBER = 6;

    /**
     * Menü kinézet
     */
    private MenuView menuView;

    /**
     * Játék kinézet
     */
    private view.GameController controller;

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
                MainWindow window = new MainWindow();

                // Ablak megjelenítési beállításai
                window.displaySetUp();

                // Megjelenítés
                window.setVisible(true);
            }
        });

    }

    public MainWindow() {

        // Menü nézet hozzáadása az ablakhoz
        add(new MenuView());
        pack();
    }

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
