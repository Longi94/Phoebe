package model;

import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.util.ArrayList;

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


        distanceCompleted = calculateDistance(oldPos);




        PhoebeLogger.message("track", "robotJumped", "this");
        track.robotJumped(this);

        //nem gondolom szükségesnek, csak az olaj állíthatja szerintem
        //enabled = false;

        PhoebeLogger.returnMessage();
    }

    private double calculateDistance(Position oldPos) {
        //TODO Nem így kell számolni. Attól függ, mennyit haladt előre a belső íven...
        //TODO Mert így az nyer, aki sokat kacsázik, nem az, aki egyenesen megy
        if (track.innerArc == null) {
            //ha nem definiáltuk a pályát, egyszerűen a megtett táv növelése
            distanceCompleted += pos.getDistance(oldPos);
        } else {
            ArrayList<Position> points = new ArrayList<Position>(4);
            Position innerArcBeginning = new Position();
            Position innerArcEnd = new Position();

            //kitaláljuk melyik útrészen van (négyszögben)
            for (int i = 0; i < track.innerArc.size(); i++){
                points.clear();
                if (i != (track.innerArc.size() - 1)){
                    points.add(track.innerArc.get(i));
                    points.add(track.innerArc.get(i + 1));
                    points.add(track.outerArc.get(i + 1));
                    points.add(track.outerArc.get(i));
                } else {
                    points.add(track.innerArc.get(i));
                    points.add(track.innerArc.get(1));
                    points.add(track.outerArc.get(1));
                    points.add(track.outerArc.get(i));
                }
                if (Track.insidePolygon(points, pos))
                    innerArcBeginning = new Position(points.get(0).getX(), points.get(0).getY());
                    innerArcEnd = new Position(points.get(1).getX(), points.get(1).getY());
                    break;
            }
            if (innerArcBeginning != null && innerArcEnd != null) {
                double apx = pos.getX() - innerArcBeginning.getX();
                double apy = pos.getY() - innerArcBeginning.getY();
                double abx = bx - ax;
                double aby = by - ay;

                double ab2 = abx * abx + aby * aby;
                double ap_ab = apx * abx + apy * aby;
                double t = ap_ab / ab2;
                if (t < 0) {
                    t = 0;
                } else if (t > 1) {
                    t = 1;
                }
                dest.setLocation(ax + abx * t, ay + aby * t);
                return dest;

            }
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

    public Velocity getVel() {
        return vel;
    }

    public void setVel(Velocity vel) {
        this.vel = vel;
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
            track.addObject(new Oil(pos, track));
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
            track.addObject(new Putty(pos, track));
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
