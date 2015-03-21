package skeleton;

import skeleton.usecases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by bence on 2015.03.14..
 */
public class Skeleton {

    /**
     * Dummy main függvény
     */
    public static void main(String[] args) {
        boolean ever = true;
        int option = 1;
        for(;ever;) { //Hát ez nagyon rossz
            System.out.println("Válassz opciót!");
            System.out.println("1 : Robot ugrik");
            System.out.println("2 : Robot olajba ugrik");
            System.out.println("3 : Robot ragacsba ugrik");
            System.out.println("4 : Robot pickupra ugrik");
            System.out.println("5 : Robot kiugrik a pályáról");
            System.out.println("6 : Robot olajfoltot rak le");
            System.out.println("7 : Robot ragacsfoltot rak le");
            System.out.println("8 : Robot feladja a játékot");
            System.out.println("9 : Játék elindítása, elemek felrakása a pályára");
            System.out.println("10 : Robot egyszerre ugrik olajra és ragacsra");
            System.out.println("11 : Új körben felszívódik az olaj");
            System.out.println("12 : Új körben felszívódik a ragacs");
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
                case 1:
                    //TODO geri
                    break;
                case 2:
                    new RobotJumpIntoOil();
                    break;
                case 3:
                    new RobotJumpIntoPutty();
                    break;
                case 4:
                    new RobotJumpIntoPickup();
                    break;
                case 5:
                    new RobotJumpOutOfTrack();
                    break;
                case 6:
                    //TODO beni
                    break;
                case 7:
                    //TODO beni
                    break;
                case 8:
                    //TODO beni
                    break;
                case 9:
                    //TODO geri
                    break;
                case 10:
                    new RobotJumpIntoPuttyAndOil();
                    break;
                case 11:
                    //TODO geri
                    break;
                case 12:
                    //TODO geri
                    break;
                default:
                    System.out.println("Nincs ilyen számú use-case.\n");
                    break;
            }
        }
    }
}
