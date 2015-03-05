package model;

import model.basic.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bence on 2015.02.24..
 */
public class GameController {
    public static final int DEFAULT_TURN_NUMBER = 40;

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
        turnsLeft = DEFAULT_TURN_NUMBER;

    }

    /**
     * Játék elindítása
     */
    public void initGame() {

        if (numberOfPlayers <= 0 && numberOfPlayers >= 7) {//TODO max játékos szám mennyi?
            throw new IllegalArgumentException("Nem megengedett jatekos szam");
        }

        //Játékosok sorrendjét meghatározó lista
        playerOrder = new ArrayList<Integer>();

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

        //Robotok inicalizálása
        for (int i = 0; i < numberOfPlayers; i++) {
            Robot r = new Robot(new Position(0, 0), track, "R2D" + i);
            players.add(r);
            track.addObject(r);
            playerOrder.add(i);
        }

        System.out.println(Obstacle.getRadius());

        //TODO: Pickupok előre megadott helyeken
        for (int i = 0; i < 10/*dummy pickup szám*/; i++) {
            track.addObject(new Pickup(new Position(1, 1), track));
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
