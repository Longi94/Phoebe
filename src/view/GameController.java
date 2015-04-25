package view;

import model.Robot;
import model.Track;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * Grafikus játék controller
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class GameController {

    public static final int DEFAULT_TURN_NUMBER = 40;
    public static final int MAX_PLAYER_NUMBER = 6;

    private int turnsLeft = -1;
    private int numberOfPlayers;
    private Track track; //kezelt pálya
    private List<Robot> players; //játékosok
    private Robot winner = null;

    private boolean roundStarted = false;
    private boolean gameStarted = false; //Van-e betöltve pálya, elértük-e már a játék végét
    private int currentPlayer = 0;

    GamePanel gamePanel;

    public GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updateView() {
        gamePanel.updateGame();
    }

    public static String[] getAvailableTracks() {

        File trackDirectory = new File("assets/maps");

        // .map pályafájlok
        File[] trackFiles = trackDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".map");
            }
        });

        if(trackFiles.length > 0) {
            String[] trackList = new String[trackFiles.length];

            for (int i = 0; i < trackList.length; i++) {
                trackList[i] = trackFiles[i].toString();
            }

            return trackList;
        }

        else return null;
    }

}
