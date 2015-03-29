//package view;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Stage;
//
//import java.io.File;
//
///**
// * A játék menü kinézete
// *
// * Ezt a nézetet tölti be a GameController a program közvetlen indítása után.
// * Itt lehet indítani a játékot és itt lehet beállítani az új játékhoz a játékosok számát
// *
// * @author Reményi Gergely
// * @since 2015-02-28
// */
//public class MenuLayout extends StackPane implements EventHandler<ActionEvent> {
//
//    /**
//     * Játék indítása gomb
//     */
//    Button startGame;
//
//    /**
//     * Kilépés a játékból gomb
//     */
//    Button exitGame;
//
//    /**
//     * Szülő ablak elem
//     */
//    Stage parentStage;
//
//    /**
//     * Szülő ablak elem
//     */
//    Scene parentScene;
//
//    /**
//     * Konstruktor
//     *
//     * Ablak és gombok inicializálása
//     */
//    public MenuLayout(Stage parentStage) {
//        super();
//
//        File f = new File("assets/css/menu.css");
//        this.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
//
//        this.parentStage = parentStage;
//        parentScene = new Scene(this,960,600);
//
//        startGame = new Button("START");
//        startGame.setOnAction(this);
//        exitGame = new Button("EXIT");
//        exitGame.setOnAction(this);
//
//        VBox buttonContainer = new VBox();
//
//        buttonContainer.getChildren().add(startGame);
//        buttonContainer.getChildren().add(exitGame);
//
//        this.getChildren().add(buttonContainer);
//        this.setAlignment(Pos.CENTER);
//
//        // Play music
//        File sound = new File("assets/sound/menu_sound.mp3");
//        Media hit = new Media("file:///" + sound.getAbsolutePath().replace("\\", "/"));
//        MediaPlayer mediaPlayer = new MediaPlayer(hit);
//        mediaPlayer.play();
//
//
//    }
//
//    /**
//     * Szülő ablak referencia getter
//     *
//     * @return a szülő ablak referencia
//     */
//    public Scene getParentScene() {
//        return parentScene;
//    }
//
//
//    /**
//     * A gombok eseménykezelője
//     *
//     * @param event esemény objektum
//     */
//    @Override
//    public void handle(ActionEvent event) {
//        if(event.getSource() == startGame) {
//            GameLayout gameLayout = new GameLayout(parentStage);
//            parentStage.setScene(gameLayout.getParentScene());
//        }
//        if(event.getSource() == exitGame) {
//            Platform.exit();
//        }
//    }
//}
