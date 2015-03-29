package view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.GameController;

/**
 * Játék nézet
 *
 * @author Gergely Reményi
 * @since 2015.03.24.
 */
public class GameLayout extends GridPane {

    /**
     * Szülő ablak
     */
    Stage parentStage;

    /**
     * Szülő ablak elem
     */
    Scene parentScene;

    /**
     * Játék vezérlő referencia
     */
    GameController controller;

    /**
     * Játék inicializálása
     *
     * @param parentStage
     */
    public GameLayout(Stage parentStage) {

        this.parentStage = parentStage;

        controller = new GameController();
        controller.setNumberOfPlayers(3);
        controller.initGame();

    }

    /**
     * Szülő ablak referencia getter
     *
     * @return a szülő ablak referencia
     */
    public Scene getParentScene() {
        return parentScene;
    }
}
