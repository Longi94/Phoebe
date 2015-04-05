package model;

import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

/**
 * Robotot megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Robot extends TrackObjectBase {

    /**
     * Robot inicializálásakor kezdő olaj mennyiség
     */
    private static final int START_OIL_AMOUNT = 1;

    /**
     * Robot inicializálásakor kezdő ragacs mennyiség
     */
    private static final int START_PUTTY_AMOUNT = 1;

    /**
     *
     */
    private static int idCount = 0;

    /**
     * Eddig megtett összes távolság
     */
    private double distanceCompleted = 0;

    /**
     * Aktuális sebesség
     */
    private Velocity vel;

    /**
     * Rendelkezésre álló olajfoltok mennyisége
     */
    private int oilAmount;

    /**
     * Rendelkezésre álló ragacsfoltok mennyisége
     */
    private int puttyAmount;

    /**
     * Azonosító
     */
    private int id;

    /**
     * Robot neve
     */
    private String name;

    /**
     * Erre két okból lesz szükség. Egyrészt azért, hogy miután lépett, akkor utána ne tudjon vele még egyet lépni a játékos.
     * Másrészt, hogyha olajfolton áll éppen akkor ne tudjon módosítani a sebességén
     * Alapból minden kör elején true, hogyha olajfolton áll éppen akkor false.
     * Miután lépett, akkor újra false lesz, amíg egy újabb kör nem indul
     */
    private boolean enabled = true;

    /**
     * Konstruktor három paraméterrel
     *
     * @param pos   az objektum pozíciója
     * @param track a pálya, amin az objektum található
     * @param name  a robot neve
     */
    public Robot(Position pos, Track track, String name) {
        super(pos, track);

        oilAmount = START_OIL_AMOUNT;
        puttyAmount = START_PUTTY_AMOUNT;

        id = idCount;
        this.name = name;
        idCount += 1;

        this.enabled = true;

        vel = new Velocity();
    }

    /**
     * Robot ugratása.
     *
     * @param v módosító sebességvektor
     */
    public void jump(Velocity v) {
        //Vektor hozzáadása a robot sebességvektoráhaz.
        if (v != null && enabled) {
            PhoebeLogger.message("vel", "add", "v");
            vel.add(v);
        }
        //Kezdő pozíció elmentése
        Position oldPos = new Position(pos.getX(), pos.getY());

        //Robot mozgatása új pozícióba
        PhoebeLogger.message("pos", "move", "vel");
        pos.move(vel);

        if (!enabled) {

            PhoebeLogger.message("this", "setEnabled", "true");
            setEnabled(true);
        }

        calculateDistance(oldPos);

        PhoebeLogger.message("track", "robotJumped", "this");
        track.robotJumped(this);

        //nem gondolom szükségesnek, csak az olaj állíthatja szerintem
        //enabled = false;

        PhoebeLogger.returnMessage();
    }

    /**
     * Frissíti a megtett távolságot
     *
     * @param oldPos régi pozíció
     */
    private void calculateDistance(Position oldPos) {
        if (track.outerArc == null || track.innerArc == null || track.outerArc.size() < 3 || track.innerArc.size() < 3) {
            //ha nem definiáltuk a pályát, egyszerűen a megtett táv növelése
            distanceCompleted += pos.getDistance(oldPos);
        } else {
            //ellenőrizzük hogy a pályán van-e egyáltalán a robot
            if (Track.insidePolygon(track.innerArc, pos, true) || !Track.insidePolygon(track.outerArc, pos, false))
                return;

            int oldPosSector = track.getSector(oldPos);                             //kitaláljuk melyik útrészen volt (vagyis melyik négyszögben)
            int newPosSector = track.getSector(pos);                                //kitaláljuk melyik útrészen van (vagyis melyik négyszögben)
            //levonjuk, amit az előző szektorban pluszba hozzáadtuk
            distanceCompleted -= track.getSectorDistance(oldPos, oldPosSector);
            //nem egyszerű esetben teljes szektorokat kell hozzáadni
            if (oldPosSector != newPosSector) {
                int siz = track.innerArc.size();
                // kiszámoljuk hogy hány régión/négyzeten mentünk át
                int sectorsJumped = newPosSector - oldPosSector;
                if (sectorsJumped < 0) {
                    //pl ha az utolsóból az elsőbe ugrottunk, akkor is csak egyetlen egy szektort haladtunk
                    sectorsJumped += siz;
                }
                if (sectorsJumped > siz / 2) {
                    for (int i = oldPosSector; i != newPosSector; ) {
                        //azért az elejére kell, mert oldPosSector hosszát nem kell levonni, de a newPosSectorét igen
                        i--;
                        if (i < 0) i += siz;  //ha átcsordulnánk
                        // a teljes szektor hosszát levonjuk
                        distanceCompleted -= track.getSectorLength(i);
                    }
                } else {
                    for (int i = oldPosSector; i != newPosSector; i = (i + 1) % siz) {
                        //a teljes szektor hosszát hozzáadjuk
                        distanceCompleted += track.getSectorLength(i);
                    }
                }
            }

            //a szektorban ahol éppen vagyunk mennyit haladtunk
            distanceCompleted += track.getSectorDistance(pos, newPosSector);

        }
    }

    /**
     * Azt vizsgálja, hogy a robot az adott körben léphet-e
     *
     * @return léphet-e
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Robot letiltása vagy engedélyezése
     *
     * @param enabled robot állapota
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        PhoebeLogger.returnMessage();
    }

    /**
     * @return
     */
    public Velocity getVel() {
        return vel;
    }

    /**
     * @param vel
     */
    public void setVel(Velocity vel) {
        this.vel = vel;
    }

    /**
     * @return
     */
    public double getDistanceCompleted() {
        return distanceCompleted;
    }

    @Override
    public void collide(Robot r) {
        if (vel.getMagnitude() >= r.vel.getMagnitude()) {
            Velocity tmp = this.getVel();
            tmp.add(r.getVel());
            tmp.setMagnitude(tmp.getMagnitude() / 2);
            this.setVel(tmp);
            track.removeObject(r);
        } else {
            Velocity tmp = r.getVel();
            tmp.add(this.vel);
            tmp.setMagnitude(tmp.getMagnitude() / 2);
            r.setVel(tmp);
            track.removeObject(this);   //egyenloseg eseten marad, akire ralepnek (pl jatek elejen jol johet)
        }
        // igazából a többi objectel ütközést megállíthatnánk, de fölösleges, mert elvileg úgyis minden egyidőben zajlik le (és az, hogy kivettük a többől, nem okoz durva problémát)
        // a gc addig nem öli meg, amíg van rá referencia
        //TODO gameControllernek jelezni, hogy kiesett
    }

    @Override
    public void collide(CleaningRobot cr) {
        cr.setAngle(cr.getAngle() + Math.PI / 2);
    }

    public int getOilAmount() {
        return oilAmount;
    }

    public void setOilAmount(int oilAmount) {
        this.oilAmount = oilAmount;
    }

    public int getPuttyAmount() {
        return puttyAmount;
    }

    public void setPuttyAmount(int puttyAmount) {
        this.puttyAmount = puttyAmount;
    }

    /**
     * Olaj lerakása
     */
    public void putOil() {
        if (oilAmount > 0) {
            oilAmount -= 1;
            PhoebeLogger.create("Oil", "oil");
            PhoebeLogger.returnMessage();
            PhoebeLogger.message("track", "addObject", "oil");
            Oil o = new Oil(new Position(pos.getX(), pos.getY()));
            track.addObject(o);
        } else {
            throw new IllegalStateException("Elfogyott az olaj");
        }

        PhoebeLogger.returnMessage();
    }

    /**
     * Ragacs lerakása
     */
    public void putPutty() {
        if (puttyAmount > 0) {
            puttyAmount -= 1;
            PhoebeLogger.create("Putty", "putty");
            PhoebeLogger.returnMessage();
            PhoebeLogger.message("track", "addObject", "putty");
            track.addObject(new Putty(new Position(pos.getX(), pos.getY())));
        } else {
            throw new IllegalStateException("Elfogyott az ragacs");
        }

        PhoebeLogger.returnMessage();
    }

    /**
     * Olaj hozzáadása a készlethez
     */
    public void addOil() {
        oilAmount++;
        PhoebeLogger.returnMessage();
    }

    /**
     * Ragacs hozzáadása a készlethez
     */
    public void addPutty() {
        puttyAmount++;
        PhoebeLogger.returnMessage();
    }

    /**
     * A sebesség megfelezése
     */
    public void halveVelocity() {
        //Sebesség megfelezése
        PhoebeLogger.message("vel", "setMagnitude", "vel.magnitude/2");
        vel.setMagnitude(vel.getMagnitude() / 2.0);
        PhoebeLogger.returnMessage();
        PhoebeLogger.returnMessage();
    }

    /**
     * Név getter függvény
     *
     * @return a robot neve
     */
    public String getName() {
        return name;
    }

    /**
     * Játék feladása
     */
    public void forfeit() {
        PhoebeLogger.message("track", "removeObject", "this");
        track.removeObject(this);
        PhoebeLogger.returnMessage();
    }

    /**
     * Robot adatainak kiiratásához olvasható formátum
     *
     * @return olvasható robot
     */
    @Override
    public String toString() {
        return "Robot{" +
                super.toString() + "," +
                "distanceCompleted:" + (double) Math.round(100 * distanceCompleted) / 100 + "," +
                vel.toString() + "," +
                "oilAmount:" + oilAmount + "," +
                "puttyAmount:" + puttyAmount + "," +
                "id:" + id + "," +
                //"name: " + name  +  "\n" + //basszus, ez kimaradt
                "enabled:" + enabled +
                "}";
    }
}
