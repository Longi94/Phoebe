package view;

import model.GameController;

/**
 * A játék menü kinézete
 *
 * Ezt a nézetet tölti be a GameController a program közvetlen indítása után.
 * Itt lehet indítani a játékot és itt lehet beállítani az új játékhoz a játékosok számát
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class MenuView {

    /**
     * GameController referencia
     */
    GameController gameController;

    /**
     * MenuView kontroller
     *
     * Megkapja a gameControllert ami meghívta, hogy ha megvannak a beállítások, be tudja állítani
     * azt a GameeControllerbe.
     * Létrehozza az ablakot és kitölti a menü gombokkal, majd vár a felhasználói eseményre
     */
    public MenuView(GameController gameController) {
        this.gameController = gameController;
    }

}
