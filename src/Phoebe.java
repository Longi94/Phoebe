import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ThanhLong on 2015.03.04..
 */
public class Phoebe {
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
        //Játékosok számának bekérése
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int playerCount = 0;
        while (playerCount == 0){
            System.out.print("Jatekosok szama: ");
            try{
                playerCount = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                System.out.println("Valami nagyon nem jo ha ez kiirodik");
            } catch (NumberFormatException e) {
                System.out.println("Rossz formatum");
            }
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}