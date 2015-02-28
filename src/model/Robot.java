package model;

import model.basic.Position;
import model.basic.Velocity;

/**
 * Created by bence on 2015.02.23..
 */
public class Robot extends TrackObjectBase{
    private static final int START_OIL_AMOUNT = 1;
    private static final int START_PUTTY_AMOUNT = 1;
    private double distanceCompleted;

    private Velocity vel;
    public int oilAmount; //rendelkezésre álló olajfoltok mennyisége
    public int puttyAmount; //rendelkezésre álló ragacsfoltok mennyisége

    /**
     * Erre két okból lesz szükség. Egyrészt azért, hogy miután lépett, akkor utána ne tudjon vele még egyet lépni a játékos.
     * Másrészt, hogyha olajfolton áll éppen akkor ne tudjon módosítani a sebességén
     * Alapból minden kör elején true, hogyha olajfolton áll éppen akkor false.
     * Miután lépett, akkor újra false lesz, amíg egy újabb kör nem indul
     */

    public boolean canMove;

    public Robot () {
        oilAmount = START_OIL_AMOUNT;
        puttyAmount = START_PUTTY_AMOUNT;

        vel = new Velocity();
    }

    public void jump(Velocity v) {
        //TODO
    }


    public boolean putOil() {
        oilAmount -= 1;
        return true;
    }

    public boolean putPutty() {
        puttyAmount -= 1;
        return true;
    }



}
