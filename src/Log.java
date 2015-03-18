/**
 * Created by ThanhLong on 2015.03.16..
 */
public class Log {

    /**
     * Függvény hívás
     *
     * @param objectName ojbektum neve amelyen meghívták a függvényt
     * @param methodName meghívott metódus neve
     * @param parameters metódusnak átadott paraméterek
     */
    public static void message(String objectName, String methodName, String... parameters) {

    }

    /**
     * Visszatérés paraméter nélkül.
     */
    public static void returnMessage() {
        System.out.println("<----");
    }

    /**
     * Visszatérés paraméterrel
     *
     * @param returnParameter a visszaadott paraméter
     */
    public static void returnMessage(String returnParameter) {
        System.out.println("<----" + returnParameter);
    }
}