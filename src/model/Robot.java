package model;

import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot extends TrackObjectBase{
    private static final int START_OIL_AMOUNT = 1;
    private static final int START_PUTTY_AMOUNT = 1;
    private static int idCount = 0;

    private double distanceCompleted;

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

    public Robot () {
        oilAmount = START_OIL_AMOUNT;
        puttyAmount = START_PUTTY_AMOUNT;

        id = idCount;
        this.name = name;
        idCount += 1;

        vel = new Velocity();
    }

    public void jump(Velocity v) {
        //TODO
    }


    public void putOil() {
        oilAmount -= 1;
    }

    public void putPutty() {
        puttyAmount -= 1;
    }

    public void addOil() {}

    public void addPutty() {}

}
