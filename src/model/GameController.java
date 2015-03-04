package model;

import model.basic.Position;

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


    public GameController() {
        //TODO
        turnsLeft = DEFAULT_TURN_NUMBER;

    }

    public void initGame() {
        //TODO: Kell egy dummy pálya, elég lenne egy négyzet a tesztelése, de nem tudom mekkora legyen.
        track = new Track(null, null);

        //Robotok inicalizálása
        for (int i = 0; i < numberOfPlayers; i++){
            Robot r = new Robot();
            players.add(r);
            //TODO: kell kezdő pozíció
            track.addObject(r);
        }

        //TODO: Pickupok, random vagy megadott helyeken? nem emlékszem...
        for (int i = 0; i < 10/*dummy pickup szám*/; i++){
            track.addObject(new Pickup(new Position(/*TODO*/)));
        }
    }

    public void newTurn() {

        //Robot ugrásának elvégzése
        for (Robot robot : players) {
            Position position = robot.getPos();

            if (track.isInTrack(position)) {
                robot.jump(null /*TODO módosító velocity lekérdezése*/);
            } else {
                //TODO a robot lement a pályáról, kikell törölni a picsába
                track.removeObject(robot);

                players.remove(robot); //Nemtom fog e kelleni, egyenlőre itt hagyom.
            }
        }

        //newRound meghívása minden pályán lévő objektumnak
        for (TrackObjectBase item : track.getItems()) {
            item.newRound();
        }

        turnsLeft -= 1;
        if (turnsLeft == 0) {
            endGame();
        }
    }

    public void nextPlayer() {

    }


    public void endGame() {

    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
