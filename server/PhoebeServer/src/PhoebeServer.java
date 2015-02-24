import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhoebeServer {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public static void main(String[] args) {

        //Server elinditasa
        PhoebeServerInstance instance = new PhoebeServerInstance(Integer.parseInt(args[0]));
        instance.start();

        boolean running = true;

        //Reader a konzolos parancsok beolvasasara
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] command;

        while (running){
            try {
                if (br.ready()) {
                    line = br.readLine();
                    command = line.split(" ");

                    if (command[0].equals("announce")) { //announce <uzenet>
                        instance.announce(command[1]);
                    } else if (command[0].equals("send")) { //send <id> <uzenet>
                        instance.send(Integer.parseInt(command[1]), command[2]);
                    } else if (command[0].equals("drop")) { //drop <id>
                        instance.drop(Integer.parseInt(command[1]));
                    } else if (command[0].equals("stop")) { //stop, szerver leallitasa
                        running = false;
                    } else if (command[0].equals("list")) { //list, kliensek kilistazasa
                        instance.listClients();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Osszes szal leallitasa
        System.exit(0);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}