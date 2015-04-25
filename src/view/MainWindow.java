package view;

import model.GameController;

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
     * A különböző paneleket tartalmazó elem layoutja
     */
    private CardLayout cardLayout;

    /**
     * A különböző paneleket tartalmazó elem
     */
    private JPanel cardPanel;

    /**
     * Menü panel
     */
    private MenuPanel menuPanel;

    /**
     * Játék panel
     */
    private GamePanel gamePanel;

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

        // Paneleket tartalmazó elem inicializálása
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Menu panel inizializálása
        menuPanel = new MenuPanel();

        // Játék panel inicializálása
        gamePanel = new GamePanel();

        // Menü és játék panelek bepakolása az ablakba
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(gamePanel, "game");
        add(cardPanel);

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
