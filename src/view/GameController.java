package view;

import model.CleaningRobot;
import model.Robot;
import model.Track;
import model.basic.Position;
import model.basic.Velocity;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Grafikus játék controller
 *
 * @author Gergely Reményi
 * @since 2015.04.25.
 */
public class GameController {

    /**
     * Hátralévő körök száma
     */
    private int turnsLeft = -1;

    /**
     * Pálya
     */
    private Track track;

    /**
     * Játékosok
     */
    private List<Robot> players;

    /**
     * Játékban lévő játékosok száma
     */
    private int numberOfPlayers;

    /**
     * Játékban lévő játékosok listája
     */
    private List<Integer> playerOrder;

    /**
     * Győztes robot
     */
    private Robot winner = null;

    /**
     * Kör elkezdödött-e
     */
    private boolean roundStarted = false;

    /**
     * Megy-e a játék (be van-e töltve pálya és elértük-e már a játék végét)
     */
    private boolean gameStarted = false;

    /**
     * Akutális játékos sorszáma
     */
    private int currentPlayer = 0;

    /**
     * Az aktuális játékos fog olajat rakni
     */
    private boolean willPutOil = false;

    /**
     * Az aktuális játékos fog ragacsot rakni
     */
    private boolean willPutPutty = false;

    /**
     * Referencia az ablakra (frissítés miatt)
     */
    private MainWindow mainWindow;

    /**
     * Referencia a játéknézetre
     */
    private GameView gameView;

    /**
     * Referencia a HUD-ra
     */
    private HudView hudView;

    private Random generator = new Random();
    private int nextCleaner;

    /**
     * Konstruktor
     *
     * @param file a fájl amiből betöltjük az összes objektumot
     */
    public GameController(String file, ArrayList<String> names, int rounds) {

        track = new Track(file);

        gameView = new GameView(track, this);

        // Azért kell +1 mert egy újkör hívással kezdünk!
        turnsLeft = rounds+1;

        Robot.resetIds();

        //Robotokat tartalmazó lista
        players = new ArrayList<Robot>();

        //Játékosok sorrendjét meghatározó lista
        playerOrder = new ArrayList<Integer>();

        int realPlayers = 0;
        int placedPlayers = 0;

        for (int i = 1; i<= names.size();i++) {
            if (names.get(i-1) != null)
            realPlayers++;
        }

        // Robotok felállítása a startvonalra és hozzáadásuk a listához
        for (int i = 1; i <= names.size(); i++) {
            if (names.get(i - 1) != null) {
                placedPlayers++;
                Position position = new Position();
                position.setX((placedPlayers * track.getInnerArc().get(0).getX() + (realPlayers - placedPlayers + 1) * track.getOuterArc().get(0).getX()) / (realPlayers + 1));
                position.setY((placedPlayers * track.getInnerArc().get(0).getY() + (realPlayers - placedPlayers + 1) * track.getOuterArc().get(0).getY()) / (realPlayers + 1));
                Robot r = new Robot(position, track, names.get(i - 1), MenuView.PLAYER_COLORS[i - 1]);
                track.addObject(r);
                //Hozzáadjuk a játékosokhoz a robotot és új RobotView-t a pályához
                gameView.addItem(new RobotView(r, gameView.getTrackView()));
                players.add(r);
            }
        }

        numberOfPlayers = players.size();

        //Felépítjük a játékos sorrendet
        for (int i = 0; i < numberOfPlayers; i++) {
            playerOrder.add(i);
        }

        gameStarted = true;

        hudView = new HudView(players, this);
        gameView.setHudView(hudView);

        mainWindow = MainWindow.getInstance();

        mainWindow.setController(this);

        mainWindow.getContentPane().removeAll();

        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setSize(new Dimension(1280, 720));
        mainWindow.setVisible(true);    // FIRST visible = true
        mainWindow.setResizable(false); // THEN  resizable = false

        gameView.setVisible(true);
        mainWindow.add(gameView);

        mainWindow.revalidate();

        hudView.showNotification("Game Started!");

        //takarító robotok megjelenési gyakorisága 2-5 kör
        nextCleaner = generator.nextInt(4) + 2;

        newTurn();
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
                Integer dead = players.indexOf(robot);
                if (playerOrder.contains(dead)) {
                    hudView.showNotification(robot.getName() + " died!");
                    playerOrder.remove(dead);
                }
            }
        }

        //newRound meghívása minden pályán lévő objektumnak
        track.newRound();

        turnsLeft -= 1;
        
        hudView.refreshRoundLeft(turnsLeft);

        if (players.size() != 0 && (turnsLeft == 0 || playerOrder.size() == 0)) {
            endGame();
        } else {
            //TODO ehelyett kéne vmi jobb
            Collections.shuffle(playerOrder);

            gameView.newRound(players.get(playerOrder.get(0)));
        }

        if (nextCleaner == 0) {

            Double trackWidthMax = null;
            Double trackWidthMin = null;
            Double trackHeightMax = null;
            Double trackHeightMin = null;
            for (Position pos : track.getOuterArc()) {
                if (trackWidthMax == null || pos.getX() > trackWidthMax) {
                    trackWidthMax = pos.getX();
                }
                if (trackWidthMin == null || pos.getX() < trackWidthMin) {
                    trackWidthMin = pos.getX();
                }
                if (trackHeightMax == null || pos.getY() > trackHeightMax) {
                    trackHeightMax = pos.getY();
                }
                if (trackHeightMin == null || pos.getY() < trackHeightMin) {
                    trackHeightMin = pos.getY();
                }
            }

            if (trackWidthMax == null) {
                throw new IllegalStateException();
            }

            putJanitor(
                    generator.nextInt((int) (trackWidthMax-trackWidthMin)) + trackWidthMin,
                    generator.nextInt((int) (trackHeightMax-trackHeightMin)) + trackHeightMin);

            //takarító robotok megjelenési gyakorisága 2-5 kör
            nextCleaner = generator.nextInt(4) + 2;
        } else {
            nextCleaner--;
        }
    }

    /**
     * Játék befejezése
     */
    public void endGame() {
        if (!gameStarted) return;
        //TODO
        gameStarted = false;

        if (playerOrder.size() == 0) {
            hudView.showNotification("Game ended! Everyone died!");
        } else {
            hudView.showNotification("Game ended!");
        }

        //Nyertes meghatározása
        for (Robot r : players) {
            if (winner == null || winner.getDistanceCompleted() < r.getDistanceCompleted()) {
                winner = r;
            }
        }

        hudView.showNotification("Winner: " + winner.getName());

        JOptionPane.showMessageDialog(MainWindow.getInstance(),
                "Game has been ended and the winner is " + winner.getName() + ". Play a new one! ",
                "Game ended",
                JOptionPane.INFORMATION_MESSAGE);

        mainWindow.remove(gameView);
        mainWindow.add(new MenuView());
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.revalidate();

    }

    /**
     * Új takarító robot elhelyezése a pályán
     *
     * @param x x koordináta
     * @param y y koordináta
     */
    public void putJanitor(double x, double y) {
        if (!gameStarted) return;

        CleaningRobot cr = new CleaningRobot(new Position(x, y));
        track.addObject(cr);
        gameView.addItem(new CleaningRobotView(cr, gameView.getTrackView()));
    }

    /**
     * Soron lévő játékos kiejtése
     */
    public void forfeitCurrentPlayer() {
        if (!roundStarted) return;

        Robot currentRobot = getActualPlayer();

        //Robot feladja a játékot
        currentRobot.forfeit();

        //Eltávolítása a játékos sorrendből
        playerOrder.remove(currentPlayer);

        //Csökken a játékosok száma
        numberOfPlayers--;

        // Ha elfogytak a játékosok vége a mókának
        if(playerOrder.size() == 0) {
            endGame();
        }
        //Kövi játékos, kör vége ha nincs több
        else if (++currentPlayer >= playerOrder.size()) {
            roundStarted = false;
            newTurn();
        } else {
            gameView.newRound(players.get(playerOrder.get(currentPlayer)));
        }

        gameView.redraw();
    }

    /**
     * Olajra klikkelés esetén
     */
    public void clickOil() {
        willPutOil = !willPutOil;
    }

    /**
     * Ragacsra klikkelés esetén
     */
    public void clickPutty() {
        willPutPutty = !willPutPutty;
    }

    /**
     * Soron lévő játékos léptetése
     *
     * @param angle a szög amerre a sebességév változtatni kívánja egységgel
     */
    public void jumpCurrentPlayer(int angle) {

        if (!roundStarted) return;

        Robot currentRobot = getActualPlayer();

        if (willPutOil) {
            gameView.addItem(new OilView(currentRobot.putOil(),gameView.getTrackView()));
            willPutOil = false;

            hudView.showNotification(currentRobot.getName() + " placed an oil stain at " + + Math.round(currentRobot.getPos().getX() * 100.0) / 100.0 + ", "
                    + Math.round(currentRobot.getPos().getY() * 100.0) / 100.0);
            hudView.showNotification("Has " + currentRobot.getOilAmount() + " more remaining.");
        }
        if (willPutPutty) {
            gameView.addItem(new PuttyView(currentRobot.putPutty(),gameView.getTrackView()));
            willPutPutty = false;

            hudView.showNotification(currentRobot.getName() + " placed a putty stain at " + + Math.round(currentRobot.getPos().getX() * 100.0) / 100.0 + ", "
                    + Math.round(currentRobot.getPos().getY() * 100.0) / 100.0);
            hudView.showNotification("Has " + currentRobot.getPuttyAmount() + " more remaining.");
        }

        //A módosító sebesség vektor
        Velocity v = new Velocity();
        v.setAngle(Math.toRadians(angle)); //ne feledjük hogy radián kell
        v.setMagnitude(angle == -1 ? 0 : 1);

        //Robot léptetése
        currentRobot.jump(v);

        hudView.showNotification("" + currentRobot.getName() + " jumped to "
                + Math.round(currentRobot.getPos().getX() * 100.0) / 100.0 + ", "
                + Math.round(currentRobot.getPos().getY() * 100.0) / 100.0);
        hudView.resetButtons();
        willPutOil = willPutPutty = false;


        //Kövi játékos, kör vége ha nincs több
        if (++currentPlayer >= playerOrder.size()) {
            roundStarted = false;
            newTurn();
        } else {
            gameView.newRound(players.get(playerOrder.get(currentPlayer)));
        }
    }


    /**
     * Elérhető pályanevek lekérdezése
     *
     * @return elérhető pályanevek listája
     */
    public static String[] getAvailableTrackNames() {

        File trackDirectory = new File("assets/maps");

        // .map pályafájlok
        File[] trackFiles = trackDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".map");
            }
        });

        if (trackFiles.length > 0) {
            String[] trackList = new String[trackFiles.length];

            for (int i = 0; i < trackList.length; i++) {
                trackList[i] = trackFiles[i].getName().substring(0, trackFiles[i].getName().length() - 4);
            }

            return trackList;
        } else return null;
    }


    /**
     * Aktuálisan lépő robot elkérése
     *
     * @return Akív robot
     */
    public Robot getActualPlayer() {
        return players.get(playerOrder.get(currentPlayer));
    }

    /**
     * HUD view elkérése
     *
     * @return HUD view referencia
     */
    public HudView getHudView() {
        return hudView;
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

    public List<Integer> getPlayerOrder() {
        return playerOrder;
    }

    public boolean isPlayerAlive(Robot r) {
        return playerOrder.contains(new Integer(players.indexOf(r)));
    }
}
