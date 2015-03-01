package view;

import model.GameController;

/**
 * A játék kinézete
 *
 * Ez kapcsolódik a GameControllerhez és megjeleníti az ablakot, amin belül
 * megjelenik majd a többi view objektum.
 *
 * @author Reményi Gergely
 * @since 2015-02-28
 */
public class GameView {

    /**
     * GameController referencia
     */
    GameController gameController;

    /**
     * GameView kontroller
     *
     * Megkapja a a GameController objektumot és elmenti a tagváltozójába,
     * hogy tudja kivel kell kommunikálnia ha a view felől jön be változtatási kérelem
     *
     * @param gameController A GameController objektum
     */
    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

}