package view;

import model.*;
import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Grafikus játék controller
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class GameController {

    private int turnsLeft = -1;
    private int numberOfPlayers;
    private Track track; //kezelt pálya
    private List<Robot> players; //játékosok
    private Robot winner = null;

    private List<Integer> playerOrder;

    private boolean roundStarted = false;
    private boolean gameStarted = false; //Van-e betöltve pálya, elértük-e már a játék végét
    private int currentPlayer = 0;

    private GameView gw;

    /**
     * Konstruktor
     *
     * @param file a fájl amiből betöltjük az összes objektumot
     */
    public GameController(String file, ArrayList<String> names, int rounds) {

        turnsLeft = rounds;

        //TODO players és playerOrder feltöltése names alapján új robotokkal

        gw = new GameView();

        Robot.resetIds();

        track = new Track(file);

        //Robotokat tartalmazó lista
        players = new ArrayList<Robot>();

        //Játékosok sorrendjét meghatározó lista
        playerOrder = new ArrayList<Integer>();

        numberOfPlayers = players.size();

        //Felépítjük a játékos sorrendet
        for (int i = 0; i < numberOfPlayers; i++) {
            playerOrder.add(i);
        }

        gameStarted = true;
    }

    public Robot getActualPlayer() {
        return players.get(playerOrder.get(currentPlayer));
    }

    /**
     * Getter a roundStarted-nek
     *
     * @return el kezdődött-e a kör
     */
    public boolean isRoundStarted() {
        return roundStarted;
    }

    /**
     * Getter a gameStarted-nek
     *
     * @return elkezdődött-e a játék
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Kör befejezése, új kör indítása, ha van még hátra kör
     */
    public void newTurn() {
        if (!gameStarted) return;

        roundStarted = true;
        currentPlayer = 0;

        //Robotot kiszedjük, ha kiugrott
        for (Robot robot : players) {
            if (!track.isInTrack(robot.getPos())) {
                playerOrder.remove(new Integer(players.indexOf(robot)));
            }
        }

        //newRound meghívása minden pályán lévő objektumnak
        track.newRound();

        turnsLeft -= 1;
        if (players.size() != 0 && (turnsLeft == 0 || playerOrder.size() == 0)) {
            endGame();
        } else {
            Collections.shuffle(playerOrder);
        }

        PhoebeLogger.returnMessage();
    }

    /**
     * Játék befejezése
     */
    public void endGame() {
        if (!gameStarted) return;
        //TODO
        gameStarted = false;

        //Nyertes meghatározása
        for (Robot r : players) {
            if (winner == null || winner.getDistanceCompleted() < r.getDistanceCompleted()) {
                winner = r;
            }
        }

        PhoebeLogger.returnMessage();
    }

    /**
     * Új takarító robot elhelyezése a pályán
     *
     * @param x x koordináta
     * @param y y koordináta
     */
    public void putJanitor(double x, double y) {
        if (!gameStarted) return;

        track.addObject(new CleaningRobot(new Position(x, y)));
    }

    /**
     * Soron lévő jűtékos kiejtése
     */
    public void forfeitCurrentPlayer() {
        if (!roundStarted) return;

        Robot currentRobot = getActualPlayer();

        //Robot feladja a játékot
        currentRobot.forfeit();

        //Csökken a játékosok száma
        numberOfPlayers--;

        //Eltávolítása a játékos sorrendből
        playerOrder.remove(currentPlayer);
    }

    public void clickOil(/*senkinemtudjamilyenparaméterekkelkellfeliratkozni*/) {
        //lerak egy oilt oda ahol az aktuális játékos van
    }

    public void clickPutty(/*senkinemtudjamilyenparaméterekkelkellfeliratkozni*/) {
        //lerak egy puttyot oda ahol az aktuális játékos van
    }

    /**
     * Soron lévő játékos léptetése
     *
     * @param angle a szög amerre a sebességév változtatni kívánja egységgel
     */
    public void jumpCurrentPlayer(int angle) {

        if (!roundStarted) return;

        Robot currentRobot = getActualPlayer();


        //A módosító sebesség vektor
        Velocity v = new Velocity();
        v.setAngle(Math.toRadians(angle)); //ne feledjük hogy radián kell
        v.setMagnitude(angle == -1 ? 0 : 1);

        //Robot léptetése
        currentRobot.jump(v);

        //Kövi játékos, kör vége ha nincs több
        if (++currentPlayer == numberOfPlayers) {
            roundStarted = false;
        }
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
