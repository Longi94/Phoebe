import model.GameController;

import java.io.*;

/**
 * Created by ThanhLong on 2015.03.04..
 */
public class Phoebe {

    /**
     * A játék belépő (epszilon ha..ha..) main függvénye
     * @param args program argumentumok
     */
    public static void main(String[] args) {
        //Be és kimenetek inicalizálása
        BufferedReader br;
        BufferedWriter bw = null;
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
                    //2 vagy több paraméter, fájl a ki és bemenet
                    br = new BufferedReader(new FileReader(args[0]));
                    bw = new BufferedWriter(new FileWriter(args[1]));
            }

            //controller inicalizálása
            GameController controller = new GameController();

            //TODO parancsok értelmezése
            boolean running = true;
            String input;
            String[] command;
            while (running && (input = br.readLine()) != null) {
                command = input.split(" ");
                if (command.length > 0) {
                    if (command[0].equals("loadtrack")) {
                        controller.loadGameFromFile(command[1]);
                    } else if (command[0].equals("newround")) {
                        controller.newTurn();
                    } else if (command[0].equals("jump")) {
                        controller.jumpCurrentPlayer(Integer.parseInt(command[1]), false, false);
                    } else if (command[0].equals("janitor")) {
                        controller.putJanitor(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    } else if (command[0].equals("forfeit")) {
                        controller.killCurrentPlayer();
                    } else if (command[0].equals("report")) {
                        bw.write(controller.report());
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