package model;

import model.basic.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public GameController() {
        //TODO

    }

    /**
     * Betölt egy pályát
     */
    private void loadTrack(/*TODO ide jöhet valami filename, vagy vmi ami alapján megtalálja*/) {
        //Dummy pálya

        List<Position> in = new ArrayList<Position>();
        List<Position> out = new ArrayList<Position>();
        in.add(new Position(2, 2));
        in.add(new Position(6, 3));
        in.add(new Position(6, 7));
        in.add(new Position(-2, 7));

        out.add(new Position(0, 0));
        out.add(new Position(7, 1));
        out.add(new Position(7, 9));
        out.add(new Position(-5, 9));

        track = new Track(in, out);

        //Azért így csináltam meg, mert ha egy külső fájlból loadoljuk be a track-et akkor úgyse lesz benne a pálya referenciája, csak a pickupok pozíciója.

        List<Position> pickupPos = new ArrayList<Position>();
        pickupPos.add(new Position(1,1));

        while (pickupPos.size()>0) {
            track.addObject(new Pickup(pickupPos.remove(0),track));
        }

    }


    /**
     * Játék elindítása
     */
    public void initGame() {

        turnsLeft = DEFAULT_TURN_NUMBER;

        if (numberOfPlayers <= 0 && numberOfPlayers > MAX_PLAYER_NUMBER) {
            throw new IllegalArgumentException("Nem megengedett jatekos szam");
        }

        players = new ArrayList<Robot>();

        //Játékosok sorrendjét meghatározó lista
        playerOrder = new ArrayList<Integer>();

        //Csak hogy lássátok, működik a dolog
        System.out.println("RADIUS OF OBSTACLES=" + Obstacle.getRadius());

        System.out.println("RADIUS OF ROBOTS =" + Robot.getRadius());

        System.out.println("RADIUS OF PICKUPS =" + Pickup.getRadius());
        //

        loadTrack();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            //Robotok inicalizálása
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.print("" + (i + 1) + ". Jatekos neve: ");
                String name = br.readLine();
                Robot r = new Robot(new Position(0, 0), track, name);
                players.add(r);
                track.addObject(r);
                playerOrder.add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Kör befejezése, új kör indítása, ha van még hátra kör
     */
    public void newTurn() {

        //Robot ugrásának elvégzése
        for (Robot robot : players) {
            Position position = robot.getPos();

            if (track.isInTrack(position)) {
                robot.jump(null /*TODO módosító velocity lekérdezése*/);
            } else {
                //TODO a robot lement a pályáról, kikell törölni a picsába
                //TODO nem azt mondtuk, hogy akkor se törli le, ha lement?
                //TODO de lehet nemtudom
                track.removeObject(robot);

                players.remove(robot); //Nemtom fog e kelleni, egyenlőre itt hagyom.
                //kivesszük a legnagyobb elemet
                playerOrder.remove(new Integer(playerOrder.size() - 1));
            }
        }

        //newRound meghívása minden pályán lévő objektumnak
        for (TrackObjectBase item : track.getItems()) {
            item.newRound();
        }

        turnsLeft -= 1;
        if (turnsLeft == 0) {
            endGame();
        } else {
            //Játékosok sorrendjéne összekeverése, persze ez nem túl optimális játék élmény szempontjából
            Collections.shuffle(playerOrder);
        }
    }

    public void nextPlayer() {
        //TODO
    }


    public void endGame() {
        //TODO
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
