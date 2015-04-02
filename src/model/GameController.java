package model;

import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static final int DEFAULT_TURN_NUMBER = 40;
    private static final int MAX_PLAYER_NUMBER = 6;

    public int turnsLeft;

    private Track track; //kezelt pálya
    private List<Robot> players; //játékosok
    private int numberOfPlayers;

    private List<Integer> playerOrder;

    /**
     * Konstruktor
     */
    public GameController(String file) {
        try {
            loadGameFromFile(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        turnsLeft = DEFAULT_TURN_NUMBER;
        numberOfPlayers = players.size();

        //Játékosok sorrendjét meghatározó lista
        //TODO protóban egyenlőre nem
        //playerOrder = new ArrayList<Integer>();
    }


    public void loadGameFromFile(String file) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<Position> in = new ArrayList<Position>();
        List<Position> out = new ArrayList<Position>();

        players = new ArrayList<Robot>();

        track = new Track(in, out);

        //Feldobljuk és eltároljuk a robotokat
        for (int i = 0; i < players.size(); i++) {
            Robot r = players.get(i);
            PhoebeLogger.message("players", "add", "r");
            players.add(r);
            PhoebeLogger.message("track", "addObject", "r");
            track.addObject(r);
            PhoebeLogger.message("playerOrder", "add", "i");
            playerOrder.add(i);
        }
    }

    /**
     * Kör befejezése, új kör indítása, ha van még hátra kör
     */
    public void newTurn() {

        //Robotot kiszedjük, ha kiugrott
        for (Robot robot : players) {
            if (!track.isInTrack(robot.getPos())) {
                PhoebeLogger.message("playerOrder", "remove", "new Integer(players.indexOf(robot))");
                playerOrder.remove(new Integer(players.indexOf(robot)));
            }
        }

        //newRound meghívása minden pályán lévő objektumnak
        for (TrackObjectBase item : track.getItems()) {
            PhoebeLogger.message("item", "newRound");
            item.newRound();
        }

        turnsLeft -= 1;
        if (turnsLeft == 0) {
            PhoebeLogger.message("GameController", "endGame");
            endGame();
        } else {
            //Játékosok sorrendjéne összekeverése, persze ez nem túl optimális játék élmény szempontjából
            Collections.shuffle(playerOrder);
            PhoebeLogger.message("GameController", "getPlayerInputs");
            getPlayerInputs();
        }

        PhoebeLogger.returnMessage();
    }

    public void endGame() {
        //TODO

        PhoebeLogger.returnMessage();
    }

    public void putJanitor(int i, int parseInt) {

    }

    public void killCurrentPlayer() {

    }

    public String report() {
        return null;
    }

    public void jumpCurrentPlayer(int angle, boolean oil, boolean putty) {
        if (oil) {
            PhoebeLogger.message("currentPlayer", "putOil");
            currentPlayer.putOil();
        } else if (putty) {
            PhoebeLogger.message("currentPlayer", "putPutty");
            currentPlayer.putPutty();
        }


        Velocity v = new Velocity();
        v.setAngle((double) angle / 180 * Math.PI); //ne feledjük hogy radián kell
        v.setMagnitude(angle == -1 ? 0 : 1);


        PhoebeLogger.message("currentPlayer", "jump", "v");
        currentPlayer.jump(v);
    }
}
