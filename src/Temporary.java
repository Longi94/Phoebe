/**
 * Created by ThanhLong on 2015.04.04..
 */
public class Temporary {
    public static void main(String[] args) {
        for (int i = 1; i <= 28; i++) {
            System.out.println("test " + i + "...");
            Phoebe.main(new String[] {
                    "assets/test/in/test" + i + ".in",
                    "assets/test/out/test" + i + ".out"
            });
        }
    }
}