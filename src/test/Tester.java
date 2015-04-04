package test;

import proto.Phoebe;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tesztelést megvalósító osztály
 *
 * @author Gergely Reményi
 * @since 2015.03.28.
 */
public class Tester {

    /**
     * Lefuttatandó tesztesetek száma
     */
    private int testNumber;

    /**
     * Sikeresen lefutott tesztek száma
     */
    private int testSuccess;

    /**
     * Összes teszt nevét tartalmazó lista
     */
    private Map<String,Boolean> tests;

    public Tester(String in, String expected, String out) throws FileNotFoundException {

        // Alapértelmezetten 1 teszt fut le, de ha mappát hívtunk akkor átállítja
        // az majd a megfelelő számúra
        testNumber = 1;
        testSuccess = 0;
        tests = new LinkedHashMap<String,Boolean>();

        File testIn = new File(in);
        File testExpected = new File(expected);
        File testOut = new File(out);

        if(testIn.exists() && testExpected.exists()) {
            // Ha mappákat kap
            if(testIn.isDirectory() && testExpected.isDirectory()) {
                // Ha nem létezik a megadott out mappa
                if(testOut.isDirectory()) {
                    try {
                        runAllTestsInDirectory(testIn,testExpected,testOut);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    throw new FileNotFoundException("A megadott kimeneti mappa nem letezik!");
                }
            }
            // Ha sima fajlokat akkor lefuttatja az egy tesztes esetet
            else {
                runTest(testIn,testExpected,testOut);
            }
        }
        else {
            throw new FileNotFoundException("Az input vagy a vart eredmenyeket tartalmazo mappa/fajl eleresi utja helytelen");
        }

    }

    /**
     * Összes teszt lefuttatása a megkapott mappában
     *
     * @throws FileNotFoundException Ha nem található valamelyik mappa vagy fájl
     */
    private void runAllTestsInDirectory(File in, File expected, File out) throws Exception {

        // In mappában lévő fájlok listázása (csak a .in-re végződőek)
        File[] filesIn = in.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".in");
            }
        });

        // Expected mappában lévő fájlok listázása (csak a .in-re végződőek)
        File[] filesExpected = expected.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".exp");
            }
        });

        System.out.println(filesExpected.length + " || " + filesIn.length);
        if(filesExpected.length != filesIn.length) {
            throw new Exception("A bemeneti fajlok szama nem egyezik meg a vart eredmenyeket tartalmazo fajlok szamaval!");
        }

        testNumber = filesIn.length;

        for(int i = 0; i<filesIn.length; i++) {
            runTest(filesIn[i],filesExpected[i],new File(out.getPath() + "/" + getFileName(filesIn[i]) + ".out"));
        }

    }

    /**
     * Egy teszt lefuttatása
     *
     * @param in Parancsokat tartalmazó fájl
     * @throws java.io.FileNotFoundException nem talál valamilyen fájlt
     */
    private void runTest(File in, File expected, File out) throws FileNotFoundException {

        // Mindig újra elkészíti az out file-t, amit majd átad a parancsértelmezőnek
        try {
            if(out.exists())
                out.delete();
            out.createNewFile();
            Phoebe.main(new String[]{in.toString(), out.toString()});
            if(compareFiles(expected,out)) {
                testSuccess +=1;
                tests.put(getFileName(in),true);
            }
            else {
                tests.put(getFileName(in),false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Fájl nevének lekérdezése a kiterjesztés nélkül
     *
     * @param file A fájl
     *
     * @return A név kiterjesztés nélkül
     */
    public String getFileName(File file) {
        String[] tokens = file.getName().split("\\.(?=[^\\.]+$)");
        return tokens[0];
    }

    /**
     * Összehasonlít két fájlt sorról sorra
     *
     * @param expected A várt kimeneti fájl
     * @param out      A valós kimeneti fájl
     *
     * @return         A két fájl megegyezik-e vagy sem
     */
    public boolean compareFiles(File expected, File out) {

        try {
            FileReader fr1 = new FileReader(expected);
            FileReader fr2 = new FileReader(out);

            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);

            String line1;
            String line2;

            // Kiolvasás sorról sorra az első readerbőlamíg van sora
            while((line1 = br1.readLine()) != null) {
                if((line2 = br2.readLine()) != null) {
                    // Ha nem egyezik meg a két kiolvasott string
                    if(!line1.equals(line2)) {
                        return false;
                    }
                }
                // Ha a második reader-ben már nincs sor míg az elsőben még volt
                else {
                    return false;
                }
            }
            // Ha a másik fájlban van még sor akkor sem egyezik meg
            if(br2.readLine() != null) {
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Visszaadja a teszt eredményeket egy sztringbe szépen kiírva
     *
     * @return A teszt eredményei
     */
    @Override
    public String toString() {
        String returnValue = "";

        returnValue += "Tesztelési eredmények:\n";
        returnValue += "======================\n\n";
        returnValue += testSuccess + "/" + testNumber + "\n\n";

        for(Map.Entry<String,Boolean> test : tests.entrySet()) {
            returnValue += test.getKey() + "\t\t\t";
            if(test.getValue())
                returnValue += "OK\n";
            else
                returnValue += "FAIL\n";
        }

        return returnValue;
    }
}
