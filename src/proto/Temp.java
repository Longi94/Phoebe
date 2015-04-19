package proto;

/**
 * Created by Long on 2015.04.19..
 */
public class Temp {
    public static void main(String[] args) {
        for (int i = 1; i <= 37; i++) {
            System.out.println("test " + i + "...");

            if (i < 10) {
                Phoebe.main(new String[]{
                        "assets/test/in/test0" + i + ".in",
                        "assets/test/out/test0" + i + ".out"
                });
            } else {
                Phoebe.main(new String[]{
                        "assets/test/in/test" + i + ".in",
                        "assets/test/out/test" + i + ".out"
                });
            }
        }
    }
}
