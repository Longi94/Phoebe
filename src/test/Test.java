package test;

import java.io.FileNotFoundException;

/**
 * Tesztelés belépési pont
 *
 * @author Gergely Reményi
 * @since 2015.04.04.
 */
public class Test {

    /**
     * Main függvény
     *
     * @param args 3db-nak kell lennie, ezek a mappák/fájlok elérési útjai
     */
    public static void main(String[] args) {
        if(args.length == 3) {
            try {
                Tester tester = new Tester(args[0],args[1],args[2]);
                System.out.println(tester);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Nem megfelelő parameter szam!");
        }
    }

}
