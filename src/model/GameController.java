package model;

import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.*;
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


    public void loadGameFromFile(String file) {
        //TODO
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

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

        Position p = new Position(10,10);

        TrackObjectBase robot = new Robot(p,track,"R2");
        TrackObjectBase pu = new Pickup(p,track);
        TrackObjectBase oil = new Oil(p,track);


        //Csak hogy lássátok, működik a dolog
        System.out.println("RADIUS OF OBSTACLES=" + oil.getRadius());

        System.out.println("RADIUS OF ROBOTS =" + robot.getRadius());

        System.out.println("RADIUS OF PICKUPS =" + pu.getRadius());
        //

        loadTrack();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            //Robotok inicalizálása
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.print("" + (i + 1) + ". Jatekos neve: ");
                String name = br.readLine();
                Robot r = new Robot(new Position(1, 1), track, name);

                PhoebeLogger.message("players", "add", "r");
                players.add(r);

                PhoebeLogger.message("track", "addObject", "r");
                track.addObject(r);

                PhoebeLogger.message("playerOrder", "add", "i");
                playerOrder.add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Jatek kezdese...");
        System.out.println("Parancs formatum: <szog> [-o] [-p]");
        System.out.println(" - <szog>: egy eges szam 0 és 359 kozott. -1 ha nem akar valtoztatni");

        PhoebeLogger.message("GameController", "getPlayerInputs");
        getPlayerInputs();

        PhoebeLogger.returnMessage();
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

    /**
     * Sorban bekéri a játékosoktól a parancsokat
     * TODO: egyáltalán nem teljes
     */
    public void getPlayerInputs() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int angle;

        for (int i : playerOrder) {
            Robot currentPlayer = players.get(i);
            if (currentPlayer.isEnabled()) {
                System.out.print(currentPlayer.getName() + "'s turn: ");
                angle = -2;
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
                } while ((angle < 0 || angle >= 360) && angle != -1);

                //TODO eléggé meghal -1-re...

                //TODO bekért command lekezelése (valszeg tárolni kell, ha végig akarunk iterálni

                //Akadály lerakása ha a játékos akarta
                if (command.length > 1 && command[1] != null) {
                    if (command[1].equals("-o")) {
                        PhoebeLogger.message("currentPlayer", "putOil");
                        currentPlayer.putOil();
                    } else if (command[1].equals("-p")) {
                        PhoebeLogger.message("currentPlayer", "putPutty");
                        currentPlayer.putPutty();
                    }
                }
            } else {
                angle = -1;
            }
            //szerintem csapathatja itt az ugrást, most azon már nem múlik...

            Velocity v = new Velocity();
            v.setAngle((double) angle / 180 * Math.PI); //ne feledjük hogy radián kell
            v.setMagnitude(angle == -1 ? 0 : 1);

            PhoebeLogger.message("currentPlayer", "jump", "v");
            currentPlayer.jump(v);

            System.out.println(track.toString());

        }

        PhoebeLogger.message("GameController", "newTurn");
        newTurn();

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

    }
}
