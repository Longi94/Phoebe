package skeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by bence on 2015.03.14..
 */
public class Skeleton {

    /**
     * Dummy main függvény
     * @param args
     */
    public static void main_skeleton (String[] args) {
        boolean ever = true;
        int option = 1;
        for(;ever;) {
            System.out.println("Válassz opciót!");
            System.out.println("0 : kilépés");

            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                option = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                System.out.println("Valami nagyon nem jo ha ez kiirodik");
            } catch (NumberFormatException e) {
                System.out.println("Rossz formatum");
            }

            switch (option) {
                case 0:
                    ever = false;
                    break;
            }
        }
    }
}
