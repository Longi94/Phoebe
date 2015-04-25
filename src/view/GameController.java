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

    public static final int DEFAULT_TURN_NUMBER = 40;
    private static final int MAX_PLAYER_NUMBER = 6;

    private int turnsLeft = -1;
    private int numberOfPlayers;
    private Track track; //kezelt pálya
    private List<Robot> players; //játékosok
    private Robot winner = null;

    private List<Integer> playerOrder;

    private boolean roundStarted = false;
    private boolean gameStarted = false; //Van-e betöltve pálya, elértük-e már a játék végét
    private int currentPlayer = 0;

    /**
     * Konstruktor
     *
     * @param file a fájl amiből betöltjük az összes objektumot
     */
    public GameController(String file) {

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

        //Ha nem adtak meg hártalévő kört
        if (turnsLeft == -1) {
            turnsLeft = DEFAULT_TURN_NUMBER;
        }

        gameStarted = true;
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
        int i = 0;
        List<TrackObjectBase> items = track.getItems();
        while (i < items.size()) {
            TrackObjectBase item = items.get(i);
            item.newRound();
            if (i < items.size() && item == items.get(i)) i++;
        }//Szerintem szebb lenne, ha newRounddal visszatérnénk egy boollal és azalapján meghívnánk egy Iterator.removeot

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
    public void killCurrentPlayer() {
        if (!roundStarted) return;

        Robot currentRobot;

            //Random sorrend
            currentRobot = players.get(playerOrder.get(currentPlayer));

        //Robot feladja a játékot
        currentRobot.forfeit();

        //Csökken a játékosok száma
        numberOfPlayers--;

        //Eltávolítása a játékos sorrendből
        playerOrder.remove(currentPlayer);
    }


    /**
     * Soron lévő játékos léptetése
     *
     * @param angle a szög amerre a sebességév változtatni kívánja egységgel
     * @param oil   akar-e olajt lerakni
     * @param putty akar-e ragacsot lerakni
     */
    public void jumpCurrentPlayer(int angle, boolean oil, boolean putty) {

        if (!roundStarted) return;

        Robot currentRobot;


            //Random sorrend
            currentRobot = players.get(playerOrder.get(currentPlayer));

        //Akadályok letétele ha szükséges
        if (oil) {
           currentRobot.putOil();
        } else if (putty) {
            currentRobot.putPutty();
        }

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
