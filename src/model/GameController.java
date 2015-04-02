package model;

import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static final int DEFAULT_TURN_NUMBER = 40;
    private static final int MAX_PLAYER_NUMBER = 6;

    private boolean deterministic = true;

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Játékosok sorrendjét meghatározó lista
        //TODO protóban egyenlőre nem
        //playerOrder = new ArrayList<Integer>();
    }


    private void loadGameFromFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        List<Position> in = new ArrayList<Position>();
        List<Position> out = new ArrayList<Position>();
        players = new ArrayList<Robot>();

        //Azért hogy a hozzáadási sorrend megegyezzen a fájlban lévő sorrenddel
        List<TrackObjectBase> tempList = new ArrayList<TrackObjectBase>();

        String input;
        String command[];
        
        while ((input = br.readLine()) != null) {
            command = input.split(" ");
            if (command.length > 0) {
                if (command[0].equals("rounds")) {
                    //Hátra lévő körök
                    turnsLeft = Integer.parseInt(command[1]);
                } else if (command[0].equals("inner")) {
                    //Belső ív pont
                    in.add(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                } else if (command[0].equals("outer")) {
                    //Külső ív pont
                    out.add(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                } else if (command[0].equals("player")) {
                    //Új játékos
                    Robot player = new Robot(
                            new Position(Double.parseDouble(command[2]), Double.parseDouble(command[3])),
                            track, command[1]);
                    /*player.addOil(Integer.parseInt(command[6])); TODO olajok hozááadása, kezdő sebesség hozzáadása
                    player.addPutty(Integer.parseInt(command[6]));*/

                    players.add(player);
                    tempList.add(player);
                } else if (command[0].equals("pickup")) {
                    //Pickupok
                    //TODO típus megadása
                    tempList.add(new Pickup(
                            new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2]))));
                } else if (command[0].equals("oil")) {
                    //Olajok
                    //TODO élet megadása
                    tempList.add(new Oil(
                            new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2]))));
                } else if (command[0].equals("putty")) {
                    //Ragacsok
                    //TODO élet megadása
                    tempList.add(new Putty(
                            new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2]))));
                } else if (command[0].equals("janitor")) {
                    //Takarítók
                    tempList.add(new CleaningRobot(new Position(Double.parseDouble(command[1]),
                            Double.parseDouble(command[2]))));
                } else if (command[0].equals("random")) {
                    //Random kii/be kapcsolása (utsó random command fog számítani)
                    if (command[1].equals("on")) {
                        deterministic = false;
                    } else if (command[1].equals("off")) {
                        deterministic = true;
                    }
                }
            }
        }

        track = new Track(in, out);

        //Objektumok hozzácsapása a trackhez
        for (TrackObjectBase obj : tempList) {
            track.addObject(obj);
        }

        numberOfPlayers = players.size();

        //Feldobljuk és eltároljuk a robotokat
        for (int i = 0; i < numberOfPlayers; i++) {
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
            //currentPlayer.putOil();
        } else if (putty) {
            PhoebeLogger.message("currentPlayer", "putPutty");
            //currentPlayer.putPutty();
        }


        Velocity v = new Velocity();
        v.setAngle((double) angle / 180 * Math.PI); //ne feledjük hogy radián kell
        v.setMagnitude(angle == -1 ? 0 : 1);


        PhoebeLogger.message("currentPlayer", "jump", "v");
        //currentPlayer.jump(v);
    }
}
