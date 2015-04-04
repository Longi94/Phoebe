import model.GameController;

import java.io.*;

/**
 * Created by ThanhLong on 2015.03.04..
 */
public class Phoebe {

    /**
     * A játék belépő (epszilon ha..ha..) main függvénye
     *
     * @param args program argumentumok
     */
    public static void main(String[] args) {
        //Be és kimenetek inicalizálása
        BufferedReader br;
        BufferedWriter bw;
        try {
            switch (args.length) {
                case 0:
                    //Nincs paraméter, konzol a ki és bemenet
                    br = new BufferedReader(new InputStreamReader(System.in));
                    bw = new BufferedWriter(new OutputStreamWriter(System.out));
                    break;
                case 1:
                    //1 paraméter, konzol a kimenet, fájl a bemenet
                    br = new BufferedReader(new FileReader(args[0]));
                    bw = new BufferedWriter(new OutputStreamWriter(System.out));
                    break;
                default:
                    //Fájl létrehozása ha nem létezik
                    new File(args[1]).createNewFile();
                    //2 vagy több paraméter, fájl a ki és bemenet
                    br = new BufferedReader(new FileReader(args[0]));
                    bw = new BufferedWriter(new FileWriter(args[1]));
            }

            //controller inicalizálása
            GameController controller = null;

            //TODO parancsok értelmezése, parancs visszautasítás
            boolean running = true;
            String input;
            String[] command;
            while (running && (input = br.readLine()) != null) {
                command = input.split(" ");
                if (command.length > 0) {
                    if (command[0].equals("loadtrack")) {
                        controller = new GameController(command[1]);
                    } else if (controller != null && command[0].equals("newround")) {
                        if (controller.isGameStarted()) {
                            controller.newTurn();
                        }
                    } else if (controller != null && command[0].equals("jump")) {
                        if (controller.isRoundStarted()) {
                            if (command.length > 2) {
                                if (command[2].equals("o")) {
                                    controller.jumpCurrentPlayer(Integer.parseInt(command[1]), true, false);
                                } else if (command[2].equals("p")) {
                                    controller.jumpCurrentPlayer(Integer.parseInt(command[1]), false, true);
                                }
                            } else {
                                controller.jumpCurrentPlayer(Integer.parseInt(command[1]), false, false);
                            }
                        }
                    } else if (controller != null && command[0].equals("janitor")) {
                        if (controller.isGameStarted()) {
                            controller.putJanitor(Double.parseDouble(command[1]), Double.parseDouble(command[2]));
                        }
                    } else if (controller != null && command[0].equals("forfeit")) {
                        if (controller.isRoundStarted()) {
                            controller.killCurrentPlayer();
                        }
                    } else if (controller != null && command[0].equals("report")) {
                        bw.write(controller.report());
                        bw.flush();
                    } else if (command[0].equals("quit")) {
                        running = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //TODO nem talált fájl kezelése
            e.printStackTrace();
        } catch (IOException e) {
            //TODO do something...
            e.printStackTrace();
        }

    }

}