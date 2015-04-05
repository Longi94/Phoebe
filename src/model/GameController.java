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

    private int turnsLeft = -1;
    private int numberOfPlayers;
    private boolean deterministic = true;
    private Track track; //kezelt pálya
    private List<Robot> players; //játékosok
    private Robot winner = null;

    private List<Integer> playerOrder;
    private List<Integer> playerOrderSorted;

    private boolean roundStarted = false;
    private boolean gameStarted = false; //Van-e betöltve pálya, elértük-e már a játék végét
    private int currentPlayer = 0;

    /**
     * Konstruktor
     *
     * @param file a fájl amiből betöltjük az összes objektumot
     */
    public GameController(String file) {
        try {
            loadGameFromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
     * Fájlból betölti az össze objektumot
     *
     * @param file a fájl elérési útvonala
     * @throws IOException
     */
    private void loadGameFromFile(String file) throws IOException {
        //Fájlból olvasás
        BufferedReader br = new BufferedReader(new FileReader(file));

        //Ply körvonala
        List<Position> in = new ArrayList<Position>();
        List<Position> out = new ArrayList<Position>();

        //Robotokat tartalmazó lista
        players = new ArrayList<Robot>();

        //Játékosok sorrendjét meghatározó lista
        playerOrder = new ArrayList<Integer>();
        playerOrderSorted = new ArrayList<Integer>();

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
                    //A maxnál több játékost nem adunk hozzá
                    if (players.size() < MAX_PLAYER_NUMBER) {
                        //Új játékos
                        Robot player = new Robot(
                                new Position(Double.parseDouble(command[2]), Double.parseDouble(command[3])),
                                track, command[1]);

                        //Kezdő sebesség
                        if (command.length > 4) {
                            player.setVel(new Velocity(Math.toRadians(Double.parseDouble(command[5])),
                                    Double.parseDouble(command[4])));
                        }

                        //Olaj és ragacs hozzáadása
                        if (command.length > 6) {
                            player.setOilAmount(Integer.parseInt(command[6]));
                            player.setPuttyAmount(Integer.parseInt(command[7]));
                        }

                        players.add(player);
                        tempList.add(player);
                    }
                } else if (command[0].equals("pickup")) {
                    //Pickupok
                    Pickup pickup = new Pickup(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                    if (command.length > 3) {
                        if (command[3].equals("o")) {
                            pickup.setType(0);
                        } else if (command[3].equals("p")) {
                            pickup.setType(1);
                        }
                    }
                    tempList.add(pickup);
                } else if (command[0].equals("oil")) {
                    //Olajok
                    Oil oil = new Oil(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                    if (command.length > 3) {
                        oil.setHitsLeft(Integer.parseInt(command[3]));
                        oil.setRoundsLeft(Integer.parseInt(command[4]));
                    }
                    tempList.add(oil);
                } else if (command[0].equals("putty")) {
                    //Ragacsok
                    Putty putty = new Putty(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                    if (command.length > 3) {
                        putty.setHitsLeft(Integer.parseInt(command[3]));
                        putty.setRoundsLeft(Integer.parseInt(command[4]));
                    }
                    tempList.add(putty);
                } else if (command[0].equals("janitor")) {
                    //Takarítók
                    tempList.add(new CleaningRobot(new Position(Double.parseDouble(command[1]),
                            Double.parseDouble(command[2]))));
                } else if (command[0].equals("random")) {
                    //Random ki/be kapcsolása (utsó random command fog számítani)
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

        //Felépítjük a játékos sorrendet
        for (int i = 0; i < numberOfPlayers; i++) {
            playerOrder.add(i);
            playerOrderSorted.add(i);
        }

        //Ha nem adtak meg hártalévő kört
        if (turnsLeft == -1) {
            turnsLeft = DEFAULT_TURN_NUMBER;
        }
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
                PhoebeLogger.message("playerOrder", "remove", "new Integer(players.indexOf(robot))");
                playerOrder.remove(new Integer(players.indexOf(robot)));
                playerOrderSorted.remove(new Integer(players.indexOf(robot)));
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
        if (turnsLeft == 0) {
            PhoebeLogger.message("GameController", "endGame");
            endGame();
        } else {
            //Játékosok sorrendjéne összekeverése, persze ez nem túl optimális játék élmény szempontjából
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

        if (deterministic) {
            //Normál sorrend
            currentRobot = players.get(playerOrderSorted.get(currentPlayer));
        } else {
            //Random sorrend
            currentRobot = players.get(playerOrder.get(currentPlayer));
        }

        //Robot feladja a játékot
        currentRobot.forfeit();

        //Csökken a játékosok száma
        numberOfPlayers--;

        //Eltávolítása a játékos sorrendből
        playerOrder.remove(currentPlayer);
        playerOrderSorted.remove(currentPlayer);
    }

    /**
     * Report készítése a pályán található objektumokról
     *
     * @return a report
     */
    public String report() {
        if (track == null) {
            return "track is null";
        }

        return prettyPrintReport(track.toString());
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

        if (deterministic) {
            //Normál sorrend
            currentRobot = players.get(playerOrderSorted.get(currentPlayer));
        } else {
            //Random sorrend
            currentRobot = players.get(playerOrder.get(currentPlayer));
        }

        //Akadályok letétele ha szükséges
        if (oil) {
            PhoebeLogger.message("currentPlayer", "putOil");
            currentRobot.putOil();
        } else if (putty) {
            PhoebeLogger.message("currentPlayer", "putPutty");
            currentRobot.putPutty();
        }

        //A módosító sebesség vektor
        Velocity v = new Velocity();
        v.setAngle(Math.toRadians(angle)); //ne feledjük hogy radián kell
        v.setMagnitude(angle == -1 ? 0 : 1);

        //Robot léptetése
        PhoebeLogger.message("currentPlayer", "jump", "v");
        currentRobot.jump(v);

        //Kövi játékos, kör vége ha nincs több
        if (++currentPlayer == numberOfPlayers) {
            roundStarted = false;
        }
    }

    /**
     * Szépen megformázza a reportot JSON-hoz hasonlóan
     *
     * @param report a megformázandó string
     * @return formázott report
     */
    public static String prettyPrintReport(String report) {
        //Követjük a szinteket
        int indent = 0;

        //Ebbe építjük fel a stringet
        String prettyReport = "";

        for (int i = 0; i < report.length(); i++) {
            char c = report.charAt(i);
            switch (c) {
                //Új szint, egyel beljebbe kell írni, enter ésa  megfelelő számú space
                case '{':
                    indent++;
                    prettyReport += c + "\n";
                    for (int j = 0; j < indent; j++) {
                        prettyReport += "    ";
                    }
                    break;
                //Egy szintel feljebb lépés, új sort kezdünk
                case '}':
                    indent--;
                    prettyReport += "\n";
                    for (int j = 0; j < indent; j++) {
                        prettyReport += "    ";
                    }
                    prettyReport += c;
                    //Vesszőt nem rakjuk új sorba
                    if (i < report.length() - 1 && report.charAt(i + 1) != ',') {
                        if (report.charAt(i + 1) != '}') {
                            prettyReport += "\n";
                            for (int j = 0; j < indent; j++) {
                                prettyReport += "    ";
                            }
                        }
                    }
                    break;
                //Következő attribútum, új sore és megfelelő számú space hozzá adása
                case ',':
                    prettyReport += c + "\n";
                    for (int j = 0; j < indent; j++) {
                        prettyReport += "    ";
                    }
                    break;
                //Normál karakter
                default:
                    prettyReport += c;
            }
        }
        return prettyReport + "\n";
    }

    /**
     *
     * @return
     */
    public String status() {
        String status = "Status{";
        if (!gameStarted){
            status += "status:game not started,LastWinner{";
            if (winner != null) {
                status += winner.toString();
            }
            status += "}";
        } else {
            if (roundStarted) {
                Robot currentRobot;
                if (deterministic) {
                    //Normál sorrend
                    currentRobot = players.get(playerOrderSorted.get(currentPlayer));
                } else {
                    //Random sorrend
                    currentRobot = players.get(playerOrder.get(currentPlayer));
                }
                status += "status:game started,rounds:" + turnsLeft + ",";
                status += "CurrentPlayer{" +
                        currentRobot.toString() + "}";
            } else {
                status += "status:game started; round not started,rounds:" + turnsLeft;
            }
        }
        status += "}";
        return prettyPrintReport(status);
    }
}
