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
        BufferedReader br = null;
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
        } catch (FileNotFoundException e) {
            //TODO nem talált fájl kezelése
            e.printStackTrace();
        } catch (IOException e) {
            //TODO do something...
            e.printStackTrace();
        }

    }

}