package model;

import model.basic.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
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

    //=============================================================================================
    // KONSTRUKTOROK
    //=============================================================================================

    /**
     * Konstruktor, új pályát hoz létre egy külső ív és egy belső ív tömbből
     *
     * @param innerArc belső ív
     * @param outerArc külső ív
     */
    public Track(List<Position> innerArc, List<Position> outerArc) {
        items = new ArrayList<TrackObjectBase>();
        this.innerArc = innerArc;
        this.outerArc = outerArc;
    }

    //=============================================================================================
    // GETTER-SETTEREK
    //=============================================================================================

    /**
     * Megmutatja, hogy egy adott pont a poligon belsejében van-e. (Ray casting algorithm) -> http://stackoverflow.com/questions/11716268/point-in-polygon-algorithm
     *
     * @param arc a poligon pontjainak listája
     * @param pos a pont, aminek státuszát teszteljük
     * @return igaz, ha pont a poligon területén belül van, különben hamis
     */
    public static boolean insidePolygon(List<Position> arc, Position pos) {
        double posX = pos.getX();
        double posY = pos.getY();
        boolean c = false;
        for (int i = 0, j = arc.size() - 1; i < arc.size(); j = i++) {
            /* That's for release
            if ( ((arc.get(i).getY()>posY) != (arc.get(j).getY()>posY)) &&
                    (posX < (arc.get(j).getX()-arc.get(i).getX()) *
                            (posY-arc.get(i).getY()) / (arc.get(j).getY()-arc.get(i).getY()) + arc.get(i).getX()) )
            */
            double iX = arc.get(i).getX();
            double iY = arc.get(i).getY();
            double jX = arc.get(j).getX();
            double jY = arc.get(j).getY();
            if (((iY > posY) != (jY > posY)) && (posX < (jX - iX) * (posY - iY) / (jY - iY) + iX))
                c = !c;
        }
        return c;

    }

    //=============================================================================================
    // LIST-EK KEZELÉSE
    //=============================================================================================

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
        return items.add(object);
    }

    //=============================================================================================
    // EGYÉB FÜGGVÉNYEK
    //=============================================================================================

    /**
     * Eltávolít egy elemet a pályaelemek tömbjéből
     *
     * @param object az eltávolítandó elem
     * @return sikeres volt-e az eltávolítás, vagy sem
     */
    public boolean removeObject(TrackObjectBase object) {
        return items.remove(object);
    }

    /**
     * Megmondja hogy az adott pozíció a pályán van-e. Egy pont a pályán van, ha külső íven belül, és a belső íven kívül, vagy a belső íven található
     *
     * @param pos a pozíció, aminek kiváncsiak vagyunk a státuszára
     * @return true, ha a pályán van és false ha a pályán kívlüre esik.
     */
    public boolean isInTrack(Position pos) {
        return insidePolygon(outerArc, pos) && !insidePolygon(innerArc, pos);

    }


}
