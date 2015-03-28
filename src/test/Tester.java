package test;

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
     * Teszt várható eredményeit tartalmazó mapp
     */
    private File expectedDirectory;

    /**
     * Teszt bemeneteket tartalmazó mappa
     */
    private File inDirectory;

    /**
     * Teszt kimeneteket tartalmazó mapp
     */
    private File outDirectory;

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

    public Tester(String inDirectoryName, String expectedDirectoryName, String outDirectoryName) throws FileNotFoundException {

        inDirectory = new File("/assets/test/" + inDirectoryName);
        // Ha helytelen a bemeneti mappa
        if(!inDirectory.exists()) {
            throw new FileNotFoundException("Nem talalhato a teszt bemeneti mappa!");
        }

        expectedDirectory = new File("/assets/test/" + expectedDirectoryName);
        // Ha helytelen a varhato mappa
        if(!inDirectory.exists()) {
            throw new FileNotFoundException("Nem talalhato a teszt varhato mappa!");
        }

        // Ha helytelen a kimeneti mappa
        outDirectory = new File("/assest/test/" + outDirectoryName);
        if(!inDirectory.exists()) {
            // Létrehozza azt a mappát
            outDirectory.mkdir();
        }

        // A bemeneti mappában található fájlok száma lesz a tesztesetek száma
        testNumber = inDirectory.listFiles().length;
        testSuccess = 0;

        tests = new LinkedHashMap<String,Boolean>();
    }

    /**
     * Összes teszt lefuttatása a bemeneti mappából, amely tesztfájl
     *
     * @throws FileNotFoundException Ha nem található valamelyik mappa vagy fájl
     */
    public void runAllTests() throws FileNotFoundException {

        for(final File testInFile : inDirectory.listFiles()) {
            if(!testInFile.isDirectory() && getFileExt(testInFile).equals("in")) {
                File testExpectedFile = new File(expectedDirectory + getFileName(testInFile) + ".exp");
                if(testExpectedFile.exists()) {
                    File testOutFile = new File(outDirectory + getFileName(testInFile) + ".out");
                    //TODO parancsértelmező(testInFile,outDirectory.getName + getFileName(testInFile) + "out")
                    // Ez létrehozza az out fájlt ha minden fain.

                    if(testOutFile.exists()){
                        if(compareFiles(testExpectedFile,testOutFile)) {
                            testSuccess +=1;
                            tests.put(testInFile.getName(),true);
                        }
                        else {
                            tests.put(testInFile.getName(),false);
                        }
                    }
                    else {
                        throw new FileNotFoundException("Nem jott letre a teszt kimeneti fajlja! (" + testOutFile.getName() + ")");
                    }
                }
                else {
                    throw new FileNotFoundException("Nem talalhato a tesztelendő fajl elvart kimenete! (" + testExpectedFile.getName() + ")");
                }
            }
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
     * Fájl kiterjesztésének lekérdezése
     *
     * @param file A fájl
     *
     * @return A kiterjesztés
     */
    public String getFileExt(File file) {
        String[] tokens = file.getName().split("\\.(?=[^\\.]+$)");
        return tokens[1];
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
        returnValue += testNumber + "/" + testSuccess + "\n";

        for(Map.Entry<String,Boolean> test : tests.entrySet()) {
            returnValue += test.getKey() + "\t";
            returnValue += test.getValue() + "\n";
        }

        return returnValue;
    }
}
