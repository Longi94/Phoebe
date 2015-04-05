package model;

import model.basic.Position;
import skeleton.PhoebeLogger;

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
    public List<TrackObjectBase> items;

    /**
     * A pálya belső ívéhez tartozó pontok listája
     */
    public List<Position> innerArc;

    /**
     * A pálya külső ívéhez tartozó pontok listája
     */
    public List<Position> outerArc;


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

    /**
     * Megmutatja, hogy egy adott pont a poligon belsejében van-e.
     * (Ray casting algorithm) -> http://stackoverflow.com/questions/11716268/point-in-polygon-algorithm
     *
     * @param arc a poligon pontjainak listája
     * @param pos a pont, aminek státuszát teszteljük
     * @param open nyílt-e az intervallum. A nyílt intervallum nem tartalmazza a határvonalait
     * @return igaz, ha pont a poligon területén belül van, különben hamis
     */
    public static boolean insidePolygon(List<Position> arc, Position pos, boolean open) {
        double posX = pos.getX();
        double posY = pos.getY();
        boolean c = false;
        for (int i = 0, j = arc.size() - 1; i < arc.size(); j = i++) {
            /* That's for release
            if ( ((arc.get(i).getY()>posY) != (arc.get(j).getY()>posY)) &&
                    (posX < (arc.get(j).getX()-arc.get(i).getX()) *
                            (posY-arc.get(i).getY()) / (arc.get(j).getY()-arc.get(i).getY()) + arc.get(i).getX()) )
            */
            if (isInLine(pos,arc.get(i),arc.get(j))) return !open;
            double iX = arc.get(i).getX();
            double iY = arc.get(i).getY();
            double jX = arc.get(j).getX();
            double jY = arc.get(j).getY();
            if (((iY > posY) != (jY > posY)) && (posX < (jX - iX) * (posY - iY) / (jY - iY) + iX))
                c = !c;
        }
        return c;

    }

    /**
     * Kiszámolja a két pont között ugrással megtett távolság mekkora távolságnak felel meg
     *
     * @param pos az aktuális pozíció
     * @param oldPos régi pozíció
     * @return a megtett távolság változása
     */
    public double calculateDistance(Position pos,Position oldPos) {
        double distanceChange = 0;
        if (outerArc == null || innerArc == null || outerArc.size() < 3 || innerArc.size() < 3) {
            //ha nem definiáltuk a pályát, egyszerűen a megtett táv növelése
            distanceChange += pos.getDistance(oldPos);
        } else {
            //ellenőrizzük hogy a pályán van-e egyáltalán a robot
            if (Track.insidePolygon(innerArc, pos, true) || !Track.insidePolygon(outerArc, pos, false))
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
     * Megmutatja, hogy egy adott pont rajta van-e egy szakaszon
     * @param pos a pont
     * @param start a szakasz egyik vége
     * @param end a szakasz másik vége
     * @return true ha rajta van, különben false
     */
    public static boolean isInLine(Position pos, Position start, Position end) {
        if (start.getY() == end.getY()) {
            return start.getY() == pos.getY() && (((pos.getX() - start.getX()) * (pos.getX() - end.getX())) <= 0);  //0-nál még rajta van a vonalon

        }
        double m = (end.getX() - start.getX()) / (end.getY() - start.getY());
        return (pos.getX() == start.getX() + m * (pos.getY() - start.getY())) && (((pos.getX() - start.getX()) * (pos.getX() - end.getX())) <= 0);

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
     * @param pos a pozíció
     * @return a szektor száma (vagy -1 ha nincs a pályán a pozíció)
     */
    public int getSector(Position pos) {
        for (int i = 0; i<innerArc.size();i++) {
            int j = (i+1) % innerArc.size();
            ArrayList<Position> sector = new ArrayList<Position>();
            sector.add(innerArc.get(i));
            sector.add(innerArc.get(j));
            sector.add(outerArc.get(j));
            sector.add(outerArc.get(i));
            if (insidePolygon(sector,pos,false)) {
                return i;
            }
        }
        return -1; //ez biza gáz..
    }

    /**
     * Meghatározza P1P2 és P3P4 egyenes metszéspontját
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return null, ha a két egyenes párhuzamos, különben pedig a metszéspont
     */
    public static Position intersection(Position p1, Position p2, Position p3, Position p4){

        //ha az egyik fuggoleges akkor
        if (p1.getY() - p2.getY() == 0) {
            //ha mind a kettő függőleges
            if (p3.getY() - p4.getY() == 0) {
                return null;
            }
            //különben egyszerűen számítható a metszéspont
            double intX = p3.getX() + (p4.getX() - p3.getX()) * (p1.getY() - p3.getY()) / (p4.getY() - p3.getY());
            return new Position(intX,p1.getY());
        }
        //ha a másik függőleges
        if (p3.getY() - p4.getY() == 0) {
            //itt már nem kell azzal törődni, hogy mi van ha mind a kettő függőleges
            double intX = p1.getX() + (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) / (p2.getY() - p1.getY());
            return new Position(intX,p3.getY());
        }
        //különben épeszű meredeksége van mind a két egyenesnek
        double m1 = (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
        double m2 = (p4.getX() - p3.getX())/ (p4.getY() - p3.getY());

        if (m1 == m2) return null;      //ha páruzamosak, még mindig szív6unk

        /*try {
            throw new Exception(m1 + " es" + m2);
        }catch (Exception e) {
            e.printStackTrace();
        }*/

        double intY = (p1.getX() - m1 * p1.getY() - p3.getX() + m2 * p3.getY()) / (m2 - m1);
        double intX = (p1.getX() + m1 * (intY - p1.getY()));
        return new Position(intX, intY);
    }

    /**
     * Visszaadja a szektor hosszát
     * @param id a szektor sorszáma
     * @return a szektor belső ívének hossza
     */
    public double getSectorLength(int id) {
        Position start = innerArc.get(id % innerArc.size());
        Position end = innerArc.get((id+1) % innerArc.size());
        return start.getDistance(end);
    }

    /**
     * Visszaadja az adott pont szektorának elejétől vett távolságát
     * @param pos a pozíció
     * @param id a szektor sorszáma
     * @return -1, ha nincs a szektoron belül a pont, különben az elejétől vett távolság
     */
    public double getSectorDistance(Position pos, int id) {
        int i = id % innerArc.size();
        int j = (id+1) % innerArc.size();
        ArrayList<Position> sector = new ArrayList<Position>();
        sector.add(innerArc.get(i));
        sector.add(innerArc.get(j));
        sector.add(outerArc.get(j));
        sector.add(outerArc.get(i));
        if (!insidePolygon(sector,pos,false)) {
            return -1;
        }
        //meghatározzuk a két határvonal metszéspontját, ha létezik
        Position is = intersection(sector.get(0),sector.get(3),sector.get(1),sector.get(2));
        if (is == null) {
            // ha a két határ párhuzamos volt, akkor a ponton átmenő párhuzamos metszete kell a belső ívvel
            is = new Position(pos.getX() + (sector.get(0).getX() - sector.get(3).getX()), pos.getY() + (sector.get(0).getY() - sector.get(3).getY()));
        }
        Position vet = intersection(sector.get(0),sector.get(1),is,pos);
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
                insidePolygon(outerArc, pos, false) && !insidePolygon(innerArc, pos, true);
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
