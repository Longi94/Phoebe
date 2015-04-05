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

    /**
     * Levetíti a realPosition paramétert a belső törtvonalra (arcBeginning, arcEnd), ha nem lehet rávetíteni akkor a valamelyik végponttal tér vissza
     * @param arcBeginning belső ívszakasz kezdete
     * @param arcEnd belső ívszakasz vége
     * @param realPosition robot valós pozíciója
     * @return levetített pozíció
     */
    private Position projectPosition(Position arcBeginning, Position arcEnd, Position realPosition){

        // ez valami magic képlet, remélem jó
        // http://pastebin.com/n9rUuGRh
        if (arcBeginning != null && arcEnd != null) {
            double apx = realPosition.getX() - arcBeginning.getX();
            double apy = realPosition.getY() - arcEnd.getY();
            double abx = arcEnd.getX() - arcBeginning.getX();
            double aby = arcEnd.getY() - arcBeginning.getY();

            double ab2 = abx * abx + aby * aby;
            double ap_ab = apx * abx + apy * aby;
            double t = ap_ab / ab2;
            if (t < 0) {
                t = 0;
            } else if (t > 1) {
                t = 1;
            }
            return new Position(arcBeginning.getX() + abx * t, arcEnd.getY() + aby * t);
        } else {
            throw new IllegalStateException("nem sikerult kitalalni hogy a palya melyik reszen vagyunk...");
        }
    }

    /**
     * Frissíti a megtett távolságot
     * @param oldPos régi pozíció
     * @return visszatér az új távolsággal
     */
    private double calculateDistance(Position oldPos) {
        if (track.outerArc == null || track.innerArc == null || track.outerArc.size() < 3 || track.innerArc.size() < 3) {
            //ha nem definiáltuk a pályát, egyszerűen a megtett táv növelése
            distanceCompleted += pos.getDistance(oldPos);
            return distanceCompleted;
        } else {
            ArrayList<Position> points = new ArrayList<Position>(4);
            Position newPosInnerArcBeginning = new Position();
            Position newPosInnerArcEnd = new Position();
            Position oldPosInnerArcBeginning = new Position();
            Position oldPosInnerArcEnd = new Position();


            //kitaláljuk melyik útrészen van (vagyis melyik négyszögben)
            for (int i = 0; i < track.innerArc.size(); i++){
                points.clear();
                if (i != (track.innerArc.size() - 1)){
                    points.add(track.innerArc.get(i));
                    points.add(track.innerArc.get(i + 1));
                    points.add(track.outerArc.get(i + 1));
                    points.add(track.outerArc.get(i));
                } else {
                    points.add(track.innerArc.get(i));
                    points.add(track.innerArc.get(0));
                    points.add(track.outerArc.get(0));
                    points.add(track.outerArc.get(i));
                }
                // megnézzük hogy a régi pozíciója hol volt, és elmentjük a belső ív két végpontját
                if (Track.insidePolygon(points, oldPos)){
                    oldPosInnerArcBeginning = new Position(points.get(0).getX(), points.get(0).getY());
                    oldPosInnerArcEnd = new Position(points.get(1).getX(), points.get(1).getY());
                }
                // megnézzük hogy az új pozíciója hol volt, és elmentjük a belső ív két végpontját
                if (Track.insidePolygon(points, pos)) {
                    newPosInnerArcBeginning = new Position(points.get(0).getX(), points.get(0).getY());
                    newPosInnerArcEnd = new Position(points.get(1).getX(), points.get(1).getY());
                }
            }
            if (oldPosInnerArcBeginning.equals(oldPosInnerArcEnd) && oldPosInnerArcBeginning.equals(new Position(0, 0)))
                throw new IllegalStateException("valoszinuleg nem allitodtak be az ertekek rendesen (regi position regioja)");

            // kiszámoljuk hogy hány régión/négyzeten mentünk át
            int numberOfTurnsPassed = track.innerArc.indexOf(newPosInnerArcBeginning) - track.innerArc.indexOf(oldPosInnerArcBeginning);
            //ha pont mentünk egy egész kört akkor legyen pozitív a szám mindenképp
            if (numberOfTurnsPassed < 0) numberOfTurnsPassed += track.innerArc.size();

            Position projectedOldPosOnInnerArc = projectPosition(oldPosInnerArcBeginning, oldPosInnerArcEnd, oldPos);
            Position projectedNewPosOnInnerArc = projectPosition(newPosInnerArcBeginning, newPosInnerArcEnd, pos);
            if (numberOfTurnsPassed == 0) {
                //egyszerű eset
                distanceCompleted += projectedOldPosOnInnerArc.getDistance(projectedNewPosOnInnerArc);
            } else {
                //nem egyszerű eset
                distanceCompleted += projectedOldPosOnInnerArc.getDistance(oldPosInnerArcEnd);
                distanceCompleted += projectedNewPosOnInnerArc.getDistance(newPosInnerArcBeginning);

                int index;
                index = track.innerArc.indexOf(oldPosInnerArcBeginning) + 1;
                for (int i = 0; i < numberOfTurnsPassed; i++, index++) {
                    // itt mindig vesszük a size-al vett maradékát, ezzel küszöbüljük ki a befejezett körök/indexelés újrakezdéséből származó bonyodalmakat
                    distanceCompleted += track.innerArc.get(index % track.innerArc.size()).getDistance(track.innerArc.get((index + 1) % track.innerArc.size()));
                }
            }
            return distanceCompleted;
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
            Oil o= new Oil(new Position(pos.getX(),pos.getY()));
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
