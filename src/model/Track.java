package model;

import model.basic.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
 */
public class Track {

    /** A pályán található elemek listája */
    public List<TrackObjectBase> items;

    /** A pálya belső ívéhez tartozó pontok listája */
    public List<Position> innerArc;

    /** A pálya külső ívéhez tartozó pontok listája */
    public List<Position> outerArc;

    //=============================================================================================
    // KONSTRUKTOROK
    //=============================================================================================

    /**
     * Konstruktor, új pályát hoz létre egy külső ív és egy belső ív tömbből
     * @param innerArc belső ív
     * @param outerArc külső ív
     */
    public Track(ArrayList<Position> innerArc,ArrayList<Position> outerArc) {
        items = new ArrayList<TrackObjectBase>();
        this.innerArc = innerArc;
        this.outerArc = outerArc;
    }

    //=============================================================================================
    // GETTER-SETTEREK
    //=============================================================================================

    /**
     * Getter a pályán található elemekhez
     * @return az elemek List-je
     */
    public List<TrackObjectBase> getItems() {
        return items;
    }

    //=============================================================================================
    // LIST-EK KEZELÉSE
    //=============================================================================================

    /**
     * Hozzáad egy elemet a pályaelemek tömbjéhez
     * @param object a hozzáadandó elem
     * @return sikeres volt-e a hozzáadás vagy nem
     */
    public boolean addObject(TrackObjectBase object) {
        return items.add(object);
    }

    /**
     * Eltávolít egy elemet a pályaelemek tömbjéből
     * @param object az eltávolítandó elem
     * @return sikeres volt-e az eltávolítás, vagy sem
     */
    public boolean removeObject(TrackObjectBase object) {
        return items.remove(object);
    }

    //=============================================================================================
    // EGYÉB FÜGGVÉNYEK
    //=============================================================================================

    /**
     * Megmutatja hogy egy pont egy egyenes melyik oldalára esik
     * @param i1 Az egyenes egyik végpontja
     * @param i2 Az egyenes másik végpontja
     * @param pos A pont, amiről el kell dönteni, hogy melyik oldalára esik
     * @return 1, hogyha a pont az egyenes i1-i2 vektor irányba vett jobb oldalára esik, különben -1
     */
    private int overLine(Position i1, Position i2, Position pos) {
        /*
        * Két végpontjával (A,B) adott egyenes egyenlete
        * A_Y + (B_Y - A_Y) * (X - A_X) / (B_X - A_X) = Y
        */
       return (i1.getY() + (i2.getY() - i1.getY()) * (pos.getX() - i1 . getX()) /(i2.getX() - i1.getX()) - pos.getY() > 0) ? 1 : -1;
    }

    /**
     * Megmondja hogy az adott pozíció a pályán van-e
     * @param pos a pozíció, aminek kiváncsiak vagyunk a státuszára
     * @return true, ha a pályán van és false ha a pályán kívlüre esik.
     */
    public boolean isInTrack(Position pos) {
        //WARNING!!! NEM BIZTOS HOGY JÓ, CSAK BÍZOK BENNE

        int siz = innerArc.size();
        for (int i = 0; i<siz; i++) {
            Position i1 = innerArc.get(i);
            Position i2 = innerArc.get( i+1 % siz ); //az utolsó pont utáni az az első
            Position o1 = outerArc.get(i);
            Position o2 = outerArc.get( i+1 % siz);

            int overInner = overLine(i1,i2,pos);
            int overOuter = overLine(o1,o2,pos);
            int overTop = overLine(i2, o2, pos);
            int overBottom = overLine(i1, o1, pos);
            //az kell, hogy az egyiken túl legyen a másikon meg ne, és mind a külső belső, mind a felső-alsó határok tekintetébe
            if (overInner * overOuter < 0 && overTop * overBottom < 0) {
                return true;
            }
            return false;
        }

        return true;
    }

}
