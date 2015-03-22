package skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Logger osztály
 *
 * @author Thanh Long Tran
 * @since 2015.03.16.
 */
public class PhoebeLogger {

    //Engedélyezve van-e a loggolás
    private static boolean enabled = false;

    //Ebben tároljuk el a sorszámozást
    private static List<Integer> indexes = new ArrayList<Integer>();

    //Figyelni kell hogy éppen visszatértünk-e a függvényből vagy nem
    private static boolean returned = false;

    /**
     * Függvény hívás
     *
     * @param objectName ojbektum neve amelyen meghívták a függvényt
     * @param methodName meghívott metódus neve
     * @param parameters metódusnak átadott paraméterek
     */
    public static void message(String objectName, String methodName, String... parameters) {
        //Nem loggolunk, ha le van tiltva
        if (!enabled) return;

        //Ezzel kezdődik mindig
        String log = "----";

        if (!returned) {
            //Eggyel mélyebbre mentünk
            indexes.add(1);
        } else {
            //Maradunk ezen a szinten, inkrementálunk
            int temp = indexes.get(indexes.size() - 1);
            indexes.set(indexes.size() - 1, ++temp);
        }

        //Nyíl hossza a mélységtől függ
        for (int i = 1; i < indexes.size(); i++) {
            log += "----";
        }

        //Nyílvég
        log += ">";

        //Hozzácsapjuk a sorszámozást
        for (int i = 0; i < indexes.size() - 1; i++) {
            log += "" + indexes.get(i) + ".";
        }
        log += indexes.get(indexes.size() - 1) + ":";

        //Hozzácsapjuk a meghívott objektum és függvény nevét
        log += objectName + "." + methodName + "(";

        //Ha megadtak paramétereket, akkor azt is hozzácsapjuk a Stringhez majd lezárjuk zárójellel
        if (parameters.length > 0) {
            for (int i = 0; i < parameters.length - 1; i++) {
                log += parameters[i] + ", ";
            }
            log += parameters[parameters.length - 1] + ")";
        } else {
            log += ")";
        }

        //Kiírjuk a logot
        System.out.println(log);

        //Nyílván
        returned = false;
    }

    /**
     * Visszatérés paraméter nélkül.
     */
    public static void returnMessage() {
        returnMessage("");
    }

    /**
     * Visszatérés paraméterrel
     *
     * @param returnParameter a visszaadott paraméter
     */
    public static void returnMessage(String returnParameter) {
        //Nem loggolunk, ha le van tiltva
        if (!enabled) return;

        //Dobjunk kivételt, ha nincs hova vissza menni
        if (indexes.size() == 0 || (indexes.size() == 1 && returned))
            throw new IllegalStateException();

        //Kiinduló string
        String log = "";

        //Egy indexet törlünk, ha egy szintel lejjeb léptünk
        if (returned) {
            indexes.remove(indexes.size() - 1);
        }

        //Minél mélyebben vagyunk, annál beljebb van a nyíl
        for (int i = 0; i < indexes.size() - 1; i++) {
            log += "    ";
        }

        //Maga a nyíl
        log += "<----";
        //Visszatérő érték hozzácsapása
        System.out.println(log + returnParameter);

        returned = true;
    }

    /**
     * Számozás újra kezdése, érdemes meghívni use-case elején és végén.
     */
    public static void clear() {
        indexes.clear();
        returned = false;
    }

    /**
     * Loggolás tiltása vagy engedélyezése. (Alapból tiltva van)
     *
     * @param enabled booooooool
     */
    public static void enableLogging(boolean enabled) {
        PhoebeLogger.enabled = enabled;
    }

    /**
     * Objectum létrehozása
     *
     * @param className osztály neve
     * @param variableName változó neve
     */
    public static void create(String className, String variableName) {
        //Nem loggolunk, ha le van tiltva
        if (!enabled) return;

        if (!returned) {
            //Eggyel mélyebbre mentünk
            indexes.add(1);
        } else {
            //Maradunk ezen a szinten, inkrementálunk
            int temp = indexes.get(indexes.size() - 1);
            indexes.set(indexes.size() - 1, ++temp);
        }

        returned = false;

        //Ezzel kezdődik mindig
        String log = "----";

        //Nyíl hossza a mélységtől függ
        for (int i = 1; i < indexes.size(); i++) {
            log += "----";
        }

        //Nyílvég
        log += ">";

        //Hozzácsapjuk a sorszámozást
        for (int i = 0; i < indexes.size() - 1; i++) {
            log += "" + indexes.get(i) + ".";
        }
        //A create tag
        log += indexes.get(indexes.size() - 1) + ":<<create>>";

        //A változó neve és típusa
        log += variableName + ":" + className;

        System.out.println(log);
    }
}