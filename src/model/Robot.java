package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot extends TrackObjectBase {
    private static final int START_OIL_AMOUNT = 1;
    private static final int START_PUTTY_AMOUNT = 1;
    private static int idCount = 0;

    private double distanceCompleted = 0;

    private Velocity vel;
    private int oilAmount; //rendelkezésre álló olajfoltok mennyisége
    private int puttyAmount; //rendelkezésre álló ragacsfoltok mennyisége

    private int id;
    private String name;

    /**
     * Erre két okból lesz szükség. Egyrészt azért, hogy miután lépett, akkor utána ne tudjon vele még egyet lépni a játékos.
     * Másrészt, hogyha olajfolton áll éppen akkor ne tudjon módosítani a sebességén
     * Alapból minden kör elején true, hogyha olajfolton áll éppen akkor false.
     * Miután lépett, akkor újra false lesz, amíg egy újabb kör nem indul
     */
    private boolean enabled;

    /**
     * Konstruktor
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     */
    public Robot(Position pos, Track track, String name) {
        super(pos, track);

        oilAmount = START_OIL_AMOUNT;
        puttyAmount = START_PUTTY_AMOUNT;

        id = idCount;
        this.name = name;
        idCount += 1;

        vel = new Velocity();
    }

    /**
     * Robot ugratása.
     *
     * @param v módosító sebességvektor
     */
    public void jump(Velocity v) {
        //Vektor hozzáadása a robot sebességvektoráhaz.
        if (v != null) {
            vel.add(v);
        }
        //Kezdő pozíció elmentése
        Position oldPos = new Position(pos.getX(), pos.getY());

        //Robot mozgatása új pozícióba
        pos.move(vel);

        //Megtett táv növelése
        distanceCompleted += pos.getDistance(oldPos);

        //TODO .ConcurrentModificationException - dob
        for (TrackObjectBase item : track.getItems()) {
            //Magával ne ütközzön
            if (item != this && hit(item)) {
                item.collide(this);
            }
        }

        enabled = false;
    }

    /**
     * Olaj lerakása
     */
    public void putOil() {
        if (oilAmount > 0) {
            oilAmount -= 1;
            track.addObject(new Oil(pos, track));
        } else {
            throw new IllegalStateException("Elfogyott az olaj");
        }
    }

    /**
     * Ragacs lerakása
     */
    public void putPutty() {
        if (puttyAmount > 0) {
            puttyAmount -= 1;
            track.addObject(new Putty(pos, track));
        } else {
            throw new IllegalStateException("Elfogyott az ragacs");
        }
    }

    /**
     * Olaj hozzáadása a készlethez
     */
    public void addOil() {
        oilAmount++;
    }

    /**
     * Ragacs hozzáadása a készlethez
     */
    public void addPutty() {
        puttyAmount++;
    }

    /**
     * A sebesség megfelezése
     */
    public void halveVelocity() {
        //Sebesség megfelezése
        vel.setMagnitude(vel.getMagnitude() / 2.0);
    }

    /**
     * Robot letiltása vagy engedélyezése
     *
     * @param enabled robot állapota
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }
}
