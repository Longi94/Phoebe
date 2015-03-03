package model;

import model.basic.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geri on 2015. 02. 23..
 */
public class Track {

    public List<TrackObjectBase> items;
    public List<Position> innerArc;
    public List<Position> outerArc;

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

    /**
     * Getter a pályán található elemekhez
     * @return az elemek List-je
     */
    public List<TrackObjectBase> getItems() {
        return items;
    }

    /**
     * Megmondja hogy az adott pozíció a pályán van-e
     * @param pos a pozíció, aminek kiváncsiak vagyunk a státuszára
     * @return true, ha a pályán van és false ha a pályán kívlüre esik.
     */
    public boolean isInTrack(Position pos) {
        //TODO
        return true;
    }

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


}
