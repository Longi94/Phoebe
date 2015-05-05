package model.basic;

import skeleton.PhoebeLogger;

import java.util.List;

/**
 * Pozíciókat megvalósító osztály
 *
 * @author Bence Czipó
 * @author Benedek Fábián
 * @author Gergely Reményi
 * @author Thanh Long Tran
 * @since 2015.02.23.
 */
public class Position {
    /**
     * X koordináta
     */
    private double posX;

    /**
     * Y koordináta
     */
    private double posY;

    /**
     * Paraméter nélküli konstuktor
     * <p/>
     * Inicializálja a pozíciót a (0,0) pontba
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Konstruktor
     * <p/>
     * A paraméterül kapott két double értéket állítja be a pozíciónak
     *
     * @param x x koordináta
     * @param y y koordináta
     */
    public Position(double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * @return pozíció x koordinátája
     */
    public double getX() {
        return posX;
    }

    /**
     * Beállítja a pozíció x koordinátáját
     *
     * @param posX a beállítandó x koordináta
     */
    public void setX(double posX) {
        this.posX = posX;
    }

    /**
     * @return pozíció y koordinátája
     */
    public double getY() {
        return posY;
    }

    /**
     * Beállítja a pozíció y koordinátáját
     *
     * @param posY a beállítandó y koordináta
     */
    public void setY(double posY) {
        this.posY = posY;
    }

    /**
     * E pont távolságát adja meg p-től
     *
     * @param p a pont amelytől távolságot kérünk
     * @return a távolság p-től
     */
    public double getDistance(Position p) {
        return Math.sqrt(Math.pow(posX - p.getX(), 2) + Math.pow(posY - p.getY(), 2));
    }

    /**
     * Megmutatja, hogy egy adott pont a poligon belsejében van-e.
     * (Ray casting algorithm) -> http://stackoverflow.com/questions/11716268/point-in-polygon-algorithm
     *
     * @param arc  a poligon pontjainak listája
     * @param open nyílt-e az intervallum. A nyílt intervallum nem tartalmazza a határvonalait
     * @return igaz, ha pont a poligon területén belül van, különben hamis
     */
    public boolean insidePolygon(List<Position> arc, boolean open) {
        boolean c = false;
        for (int i = 0, j = arc.size() - 1; i < arc.size(); j = i++) {
            /* That's for release
            if ( ((arc.get(i).getY()>posY) != (arc.get(j).getY()>posY)) &&
                    (posX < (arc.get(j).getX()-arc.get(i).getX()) *
                            (posY-arc.get(i).getY()) / (arc.get(j).getY()-arc.get(i).getY()) + arc.get(i).getX()) )
            */
            //ha a pont rajta csücsül a vonalon, akkor nyílt alakzat esetén tuti nincs benne, zárt esetén biztos benne van
            if (this.isInLine(arc.get(i), arc.get(j))) return !open;
            double iX = arc.get(i).getX();
            double iY = arc.get(i).getY();
            double jX = arc.get(j).getX();
            double jY = arc.get(j).getY();
            if (((iY > posY) != (jY > posY)) && (posX < (jX - iX) * (posY - iY) / (jY - iY) + iX))
                c = !c; //ha eddig benn voltunk, most kikerülünk, ha kinn voltunk bekerülünk
        }
        return c;

    }

    /**
     * Megmutatja, hogy egy adott pont rajta van-e egy szakaszon
     *
     * @param start a szakasz egyik vége
     * @param end   a szakasz másik vége
     * @return true ha rajta van, különben false
     */
    public boolean isInLine(Position start, Position end) {
        // ha vízszintes, akkor akkor van rajta, ha y egyezik, és x a két határ közé esik
        if (start.getY() == end.getY()) {
            return start.getY() == this.getY() && (((this.getX() - start.getX()) * (this.getX() - end.getX())) <= 0);  //0-nál még rajta van a vonalon

        }
        //különben van meredekség
        double m = (end.getX() - start.getX()) / (end.getY() - start.getY());
        //és kérdés hogy a pont kiegyenlíti-e az egyenletet
        return (this.getX() == start.getX() + m * (this.getY() - start.getY())) && (((this.getX() - start.getX()) * (this.getX() - end.getX())) <= 0);
        //TODO double egyenlőség vizsgálat nem éppen korrekt
    }

    /**
     * Meghatározza P1P2 és P3P4 egyenesek metszéspontját
     *
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return null, ha a két egyenes párhuzamos, különben pedig a metszéspont
     */
    public static Position intersection(Position p1, Position p2, Position p3, Position p4) {

        //ha az egyik fuggoleges akkor
        if (p1.getY() - p2.getY() == 0) {
            //ha mind a kettő függőleges
            if (p3.getY() - p4.getY() == 0) {
                return null;
            }
            //különben egyszerűen számítható a metszéspont
            double intX = p3.getX() + (p4.getX() - p3.getX()) * (p1.getY() - p3.getY()) / (p4.getY() - p3.getY());
            return new Position(intX, p1.getY());
        }
        //ha a másik függőleges
        if (p3.getY() - p4.getY() == 0) {
            //itt már nem kell azzal törődni, hogy mi van ha mind a kettő függőleges
            double intX = p1.getX() + (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) / (p2.getY() - p1.getY());
            return new Position(intX, p3.getY());
        }
        //különben épeszű meredeksége van mind a két egyenesnek
        double m1 = (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
        double m2 = (p4.getX() - p3.getX()) / (p4.getY() - p3.getY());

        if (m1 == m2) return null;      //ha páruzamosak, még mindig szív6unk

        /*try { // <-- How to make JUnit System.out.println() ;)
            throw new Exception(m1 + " es" + m2);
        }catch (Exception e) {
            e.printStackTrace();
        }*/

        //egyszerű (???) mertszéspontszámolás
        double intY = (p1.getX() - m1 * p1.getY() - p3.getX() + m2 * p3.getY()) / (m2 - m1);
        double intX = (p1.getX() + m1 * (intY - p1.getY()));
        return new Position(intX, intY);
    }

    /**
     * Pozíció módosítása sebességvektor alapján TODO: lehet hogy nem ide kéne, mert csak a Robot használja
     *
     * @param v a sebességvektor
     */
    public void move(Velocity v) {
        posX += v.getVelocityX();
        posY += v.getVelocityY();
        PhoebeLogger.returnMessage();
    }

    public int convertX(double offset, double zoom) {
        return (int) ((posX - offset) * zoom);
    }


    public int convertY(double offset, double zoom) {
        return (int) ((posY - offset) * zoom);
    }

    /**
     * Pozició olvasható formában való megjelenítéséhez
     *
     * @return A pozíció olvasható formában
     */
    @Override
    public String toString() {
        return "Position{" +
                    "x:" + (double) Math.round(100 * posX) / 100 + "," +
                    "y:" + (double) Math.round(100 * posY) / 100 +
                "}";
    }
}

