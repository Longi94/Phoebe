package model;

import model.basic.Position;
import model.basic.Velocity;

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
        pickupPos.add(new Position(1, 1));

        while (pickupPos.size() > 0) {
            track.addObject(new Pickup(pickupPos.remove(0), track));
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
                Robot r = new Robot(new Position(1, 1), track, name);
                players.add(r);
                track.addObject(r);
                playerOrder.add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Jatek kezdese...");
        System.out.println("Parancs formatum: <szog> [-o] [-p]");
        System.out.println(" - <szog>: egy eges szam 0 és 359 kozott. -1 ha nem akar valtoztatni");

        getPlayerInputs();
    }

    /**
     * Kör befejezése, új kör indítása, ha van még hátra kör
     */
    public void newTurn() {

        //Robotot kiszedjük, ha kiugrott
        for (Robot robot : players) {
            if (!track.isInTrack(robot.getPos())) {
                playerOrder.remove(new Integer(players.indexOf(robot)));
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
            getPlayerInputs();
        }
    }

    /**
     * Sorban bekéri a játékosoktól a parancsokat
     * TODO: egyáltalán nem teljes
     */
    public void getPlayerInputs() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i : playerOrder) {
            Robot currentPlayer = players.get(i);
            System.out.print(currentPlayer.getName() + "'s turn: ");
            int angle = -1;
            String[] command = {};
            //Játékos parancsának bekérése
            do {
                try {
                    command = br.readLine().split(" ");
                    angle = Integer.parseInt(command[0]);
                } catch (IOException e) {
                    System.out.println("Valami nagyon nem jo ha ez kiirodik");
                } catch (NumberFormatException e) {
                    System.out.println("Rossz formatum");
                }
            } while (angle < 0 || angle >= 360);
            //TODO eléggé meghal -1-re...

            //TODO bekért command lekezelése (valszeg tárolni kell, ha végig akarunk iterálni

            //Akadály lerakása ha a játékos akarta
            if (command.length > 1 && command[1] != null) {
                if (command[1].equals("-o")) {
                    currentPlayer.putOil();
                } else if (command[1].equals("-p")) {
                    currentPlayer.putPutty();
                }
            }
            //szerintem csapathatja itt az ugrást, most azon már nem múlik...

            Velocity v = new Velocity();
            v.setAngle(angle);
            v.setMagnitude(angle == -1 ? 0 : 1);
            currentPlayer.jump(v);

            System.out.println(track.toString());

        }

        newTurn();
    }


    public void endGame() {
        //TODO
    }

    /**
     * @param numberOfPlayers a kívánt játékosok száma
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
