package model;

import model.basic.Position;
import model.basic.Velocity;
import skeleton.PhoebeLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A pályát megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Track {

    /**
     * A pályán található elemek listája
     */
    private List<TrackObjectBase> items;

    /**
     * A pálya belső ívéhez tartozó pontok listája
     */
    private List<Position> innerArc;

    /**
     * A pálya külső ívéhez tartozó pontok listája
     */
    private List<Position> outerArc;


    /**
     * Konstruktor
     * <p/>
     * Új pályát hoz létre egy külső ív és egy belső ív tömbből
     *
     * @param innerArc belső ív
     * @param outerArc külső ív
     */
    public Track(List<Position> innerArc, List<Position> outerArc) {
        items = new ArrayList<TrackObjectBase>();
        this.innerArc = innerArc;
        this.outerArc = outerArc;
    }

    public Track(String file) {

        try {

            //Fájlból olvasás
            BufferedReader br = new BufferedReader(new FileReader(file));

            //Ply körvonala
            List<Position> in = new ArrayList<Position>();
            List<Position> out = new ArrayList<Position>();

            //Azért hogy a hozzáadási sorrend megegyezzen a fájlban lévő sorrenddel
            List<TrackObjectBase> tempList = new ArrayList<TrackObjectBase>();

            String input;
            String command[];

            while ((input = br.readLine()) != null) {
                command = input.split(" ");
                if (command.length > 0) {
                    if (command[0].equals("inner")) {
                        //Belső ív pont
                        in.add(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                    } else if (command[0].equals("outer")) {
                        //Külső ív pont
                        out.add(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                    } else if (command[0].equals("pickup")) {
                        //Pickupok
                        Pickup pickup = new Pickup(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                        if (command.length > 3) {
                            if (command[3].equals("o")) {
                                pickup.setType(0);
                            } else if (command[3].equals("p")) {
                                pickup.setType(1);
                            }
                        }
                        tempList.add(pickup);
                    } else if (command[0].equals("oil")) {
                        //Olajok
                        Oil oil = new Oil(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                        if (command.length > 3) {
                            oil.setHitsLeft(Integer.parseInt(command[3]));
                            oil.setRoundsLeft(Integer.parseInt(command[4]));
                        }
                        tempList.add(oil);
                    } else if (command[0].equals("putty")) {
                        //Ragacsok
                        Putty putty = new Putty(new Position(Double.parseDouble(command[1]), Double.parseDouble(command[2])));
                        if (command.length > 3) {
                            putty.setHitsLeft(Integer.parseInt(command[3]));
                            putty.setRoundsLeft(Integer.parseInt(command[4]));
                        }
                        tempList.add(putty);
                    } else if (command[0].equals("janitor")) {
                        //Takarítók
                        tempList.add(new CleaningRobot(new Position(Double.parseDouble(command[1]),
                                Double.parseDouble(command[2]))));
                    }
                }
            }

            //Bezárjuk a fájlt
            br.close();

            this.innerArc = in;
            this.outerArc = out;
            this.items = tempList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Kiszámolja a két pont között ugrással megtett távolság mekkora távolságnak felel meg
     *
     * @param pos    az aktuális pozíció
     * @param oldPos régi pozíció
     * @return a megtett távolság változása
     */
    public double calculateDistance(Position pos, Position oldPos) {
        double distanceChange = 0;
        if (outerArc == null || innerArc == null || outerArc.size() < 3 || innerArc.size() < 3) {
            //ha nem definiáltuk a pályát, egyszerűen a megtett táv növelése
            distanceChange += pos.getDistance(oldPos);
        } else {
            //ellenőrizzük hogy a pályán van-e egyáltalán a robot
            if (pos.insidePolygon(innerArc, true) || !pos.insidePolygon(outerArc, false))
                return 0;

            int oldPosSector = getSector(oldPos);                             //kitaláljuk melyik útrészen volt (vagyis melyik négyszögben)
            int newPosSector = getSector(pos);                                //kitaláljuk melyik útrészen van (vagyis melyik négyszögben)
            //levonjuk, amit az előző szektorban pluszba hozzáadtuk
            distanceChange -= getSectorDistance(oldPos, oldPosSector);
            //nem egyszerű esetben teljes szektorokat kell hozzáadni
            if (oldPosSector != newPosSector) {
                int siz = innerArc.size();
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
                        distanceChange -= getSectorLength(i);
                    }
                } else {
                    for (int i = oldPosSector; i != newPosSector; i = (i + 1) % siz) {
                        //a teljes szektor hosszát hozzáadjuk
                        distanceChange += getSectorLength(i);
                    }
                }
            }

            //a szektorban ahol éppen vagyunk mennyit haladtunk
            distanceChange += getSectorDistance(pos, newPosSector);

        }
        return distanceChange;
    }

    /**
     * Getter a pályán található elemekhez
     *
     * @return az elemek List-je
     */
    public List<TrackObjectBase> getItems() {
        return items;
    }

    /**
     * Hozzáad egy elemet a pályaelemek tömbjéhez
     *
     * @param object a hozzáadandó elem
     * @return sikeres volt-e a hozzáadás vagy nem
     */
    public boolean addObject(TrackObjectBase object) {
        object.setTrack(this);
        PhoebeLogger.returnMessage("items.add(object)");
        return items.add(object);
    }

    /**
     * Eltávolít egy elemet a pályaelemek tömbjéből
     *
     * @param object az eltávolítandó elem
     * @return sikeres volt-e az eltávolítás, vagy sem
     */
    public boolean removeObject(TrackObjectBase object) {
        Boolean b = items.remove(object);
        PhoebeLogger.returnMessage(Boolean.toString(b));
        return b;
    }

    /**
     * Megadja hogy az adott pont melyik szektorban található av pályán
     *
     * @param pos a pozíció
     * @return a szektor száma (vagy -1 ha nincs a pályán a pozíció)
     */
    public int getSector(Position pos) {
        for (int i = 0; i < innerArc.size(); i++) {
            int j = (i + 1) % innerArc.size();

            //készítünk a szektornak egy csúcsokból álló tömböt. Fontos milyen sorrendben tesszük bele az elemeket.
            ArrayList<Position> sector = new ArrayList<Position>();
            sector.add(innerArc.get(i));
            sector.add(innerArc.get(j));
            sector.add(outerArc.get(j));
            sector.add(outerArc.get(i));
            if (pos.insidePolygon(sector, false)) {
                //ha belül van visszatérünk egyel
                return i;
            }
        }
        return -1; //TODO jobb lenne hibát dobni
    }

    /**
     * Visszaadja a szektor hosszát
     *
     * @param id a szektor sorszáma
     * @return a szektor belső ívének hossza
     */
    public double getSectorLength(int id) {
        //konvenció szerint egy szektor hossza a belső ív hossza
        Position start = innerArc.get(id % innerArc.size());
        Position end = innerArc.get((id + 1) % innerArc.size());
        return start.getDistance(end);
    }

    /**
     * Visszaadja az adott pont szektorának elejétől vett távolságát
     *
     * @param pos a pozíció
     * @param id  a szektor sorszáma
     * @return -1, ha nincs a szektoron belül a pont, különben az elejétől vett távolság
     */
    public double getSectorDistance(Position pos, int id) {
        int i = id % innerArc.size();
        int j = (id + 1) % innerArc.size();
        ArrayList<Position> sector = new ArrayList<Position>();
        sector.add(innerArc.get(i));
        sector.add(innerArc.get(j));
        sector.add(outerArc.get(j));
        sector.add(outerArc.get(i));
        if (!pos.insidePolygon(sector, false)) {
            return -1;  //TODO szintén hiba kéne hogy dobódjon
        }
        //meghatározzuk a két határvonal metszéspontját, ha létezik
        Position is = Position.intersection(sector.get(0), sector.get(3), sector.get(1), sector.get(2));
        if (is == null) {
            // ha a két határ párhuzamos volt, akkor a ponton átmenő párhuzamos metszete kell a belső ívvel (a fókusz a végtelen távoli pont)
            is = new Position(pos.getX() + (sector.get(0).getX() - sector.get(3).getX()), pos.getY() + (sector.get(0).getY() - sector.get(3).getY()));
        }
        //is-t fókuszontnak használva levetítjük a pontot az egyenesre
        Position vet = Position.intersection(sector.get(0), sector.get(1), is, pos);
        //végül a vetületet megnézzük a szektor elejétől
        return vet.getDistance(sector.get(0));

    }

    /**
     * Megmondja hogy az adott pozíció a pályán van-e.
     * Egy pont a pályán van, ha külső íven belül, és a belső íven kívül, vagy a belső íven található
     *
     * @param pos a pozíció, aminek kiváncsiak vagyunk a státuszára
     * @return true, ha a pályán van és false ha a pályán kívlüre esik.
     */
    public boolean isInTrack(Position pos) {
        boolean b = outerArc == null || innerArc == null || outerArc.size() < 3 || innerArc.size() < 3 ||
                pos.insidePolygon(outerArc, false) && !pos.insidePolygon(innerArc, true);
        PhoebeLogger.returnMessage(Boolean.toString(b));
        return b;
    }

    /**
     * Robot ugrása esetén végrehajtódó függvény
     * Szerintem elegánsabb mint a getteres, mert úgy is a pálya felelőssége, hogy ki áll ott, nem a roboté
     *
     * @param r a robot aki ugrott a pályán
     */
    public void robotJumped(Robot r) {

      /*  List<TrackObjectBase> tmp = new ArrayList<TrackObjectBase>();

        for (TrackObjectBase item : items) {
            //Magával ne ütközzön
            if (item != r && r.hit(item)) {
                tmp.add(item);
            }
        }

        for (TrackObjectBase item : tmp) {
            item.collide(r);
        }
       */
        int i = 0;
        while (i < items.size()) {
            TrackObjectBase item = items.get(i);
            if (item != r) {
                PhoebeLogger.message("r", "hit", "item");
                if (r.hit(item)) {
                    PhoebeLogger.message("item", "collide", "r");
                    item.collide(r);
                }
            }
            if (i < items.size() && item == items.get(i)) i++;
        }
        PhoebeLogger.returnMessage();
    }

    /**
     * CleaningRobot ugrása esetén végrehajtódó függvény
     *
     * @param cr a takarító robot aki ugrott a pályán
     */
    public void cleaningRobotJumped(CleaningRobot cr) {
        int i = 0;
        while (i < items.size()) {
            TrackObjectBase item = items.get(i);
            if (item != cr) {
                if (cr.hit(item)) {
                    item.collide(cr);
                }
            }
            if (i < items.size() && item == items.get(i)) i++;
        }
    }

    /**
     * Megadja a legközelebbi akadály pozícióját a pályán
     *
     * @param p a pozíció, amihez képest viszonyítunk
     * @return null, ha nincs több akadály a pályán, különben a legközelebbi pozíciója
     */
    public Position getClosestObstaclePos(Position p) {
        int i = 0;
        double min = -1;
        Position minPos = null;
        while (i < items.size()) {
            TrackObjectBase item = items.get(i);
            double dist = item.obstacleDistance(p);
            if (dist < min || (min == -1 && dist != -1)) {
                min = dist;
                minPos = item.getPos();
            }
            if (i < items.size() && item == items.get(i)) i++;
        }
        return minPos;
    }

    /**
     * Pálya kiiratásához olvasható formátumra hozása
     *
     * @return olvasható pálya
     */
    @Override
    public String toString() {
        String toRet = "";
        for (TrackObjectBase item : items) {
            toRet += item.toString();
        }
        return toRet;
    }
}
