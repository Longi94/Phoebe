package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Pickupot megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Pickup extends TrackObjectBase {

    /**
     * Véletlen esemény ahhoz, hogy ragacs, vagy olaj sorsolódjon
     */
    private static Random random = new Random();

    /**
     * Konstruktor két paraméterrel
     *
     * Megkap egy poziciót és a pálya referenciát is
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Pickup(Position pos, Track track) {
        super(pos, track);
    }

    /**
     * Akkor hívódik meg, ha egy robot felszedi (ütközik vele)
     *
     * @param r a robot, ami rá ugrott
     */
    @Override
    public void collide(Robot r) {
        String pickupOption = "";

        do {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("o vagy p?");
                pickupOption = br.readLine();
            } catch (IOException e) {
                System.out.println("Valami nagyon nem jo ha ez kiirodik");
            }
        } while (!pickupOption.equals("o") && !pickupOption.equals("p"));

        //Random.nextInt(n) is both more efficient and less biased than Math.random() * n
        if (/*random.nextInt(2) == 1*/ pickupOption.equals("o")) {
            PhoebeLogger.message("r", "addOil");
            r.addOil();
        } else {
            PhoebeLogger.message("r", "addPutty");
            r.addPutty();
        }
        //kitörli magát a pályáról
        PhoebeLogger.message("track", "removeObject", "this");
        track.removeObject(this);

        PhoebeLogger.returnMessage();
    }

    /**
     * Pickup olvasható formában való kiiratásához
     *
     * @return szép pickup
     */
    @Override
    public String toString() {
        return "Pickup{" + super.toString() + "}";
    }
}
