import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhLong on 2015.03.16..
 */
public class PhoebeLogger {

    private static List<Integer> indexes = new ArrayList<Integer>();
    private static boolean returned = false;

    /**
     * Függvény hívás
     *
     * @param objectName ojbektum neve amelyen meghívták a függvényt
     * @param methodName meghívott metódus neve
     * @param parameters metódusnak átadott paraméterek
     */
    public static void message(String objectName, String methodName, String... parameters) {

        String log = "";

        if (!returned) {
            indexes.add(1);
        } else {
            int temp = indexes.get(indexes.size() - 1);
            indexes.set(indexes.size() - 1, ++temp);
        }

        for (int i = 1; i < indexes.size(); i++) {
            log += "----";
        }

        log += ">";

        for (int i = 0; i < indexes.size() - 1; i++) {
            log += "" + indexes.get(i) + ".";
        }
        log += indexes.get(indexes.size() - 1) + ":";

        log += objectName + "." + methodName + "(";

        if (parameters.length > 0) {
            for (int i = 0; i < parameters.length - 1; i++) {
                log += parameters[i] + ", ";
            }
        }
        log += parameters[parameters.length - 1] + ")";

        returned = false;
        System.out.println(log);
    }

    /**
     * Visszatérés paraméter nélkül.
     */
    public static void returnMessage() {
        System.out.println("<----");
        returned = true;
    }

    /**
     * Visszatérés paraméterrel
     *
     * @param returnParameter a visszaadott paraméter
     */
    public static void returnMessage(String returnParameter) {
        System.out.println("<----" + returnParameter);
        returned = true;
    }
}