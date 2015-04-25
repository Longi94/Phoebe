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
     * Menü kinézet
     */
    private MenuView menuView;

    /**
     * Játék kinézet
     */
    private GameView gameView;

    /**
     * Grafikus program belépési pont
     *
     * @param args paraméterek
     */
    public static void main(String[] args) {

        // Új ablak létrehozása
        MainWindow window = new MainWindow();

        // Ablak megjelenítési beállításai
        window.displaySetUp();

        // Megjelenítés
        window.setVisible(true);
    }

    public MainWindow() {

        // Menu panel inizializálása
        menuView = new MenuView();

        // Menü nézet hozzáadása az ablakhoz
        add(menuView);

    }

    public void displaySetUp() {

        // Ablak címe
        setTitle("Phoebe by Passz");

        // Méret beállítások
        setSize(1024,768);

        // Kilépés x megnyomása esetén
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Átméretezés letiltása
        setResizable(false);

        // Ablak középre igazítása a képrenyőn
        setLocationRelativeTo(null);
    }

}
