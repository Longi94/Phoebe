package socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Kulon szal egy kliens kezelesere
 */
public class PhoebeClientHandler extends Thread {

    // ===========================================================
    // Constants
    // ===========================================================

    //Uzeneteket lezaro karakter
    public static final char END = (char)13;

    // ===========================================================
    // Fields
    // ===========================================================

    private Socket connection;
    private InputStreamReader isr;
    private PrintWriter pw;

    //A kliens ip-cime
    private String address;
    //Clienshez tartozo azaonosito (auto inkremens)
    private int ID;

    private boolean isConnected = true;
    private boolean timerRunning = false;
    private Timer timer;

    private onDisconnectListener listener;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Konstruktor
     * @param s a kapcsolat
     * @param i azonosito
     */
    public PhoebeClientHandler(Socket s, int i) {
        connection = s;
        ID = i;
        timer = new Timer();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Callback hallgato beallitasa
     * @param listener this
     */
    public void setOnDisconnectListener(onDisconnectListener listener) {
        this.listener = listener;
    }

    /**
     * @return klins azonositoja
     */
    public int getClientId() {
        return ID;
    }

    /**
     * @return klines ip cime
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return csatlakozva-van-e
     */
    public boolean isConnected() {
        return isConnected;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void run() {
        if (connection == null){
            //nince kapcsolat
            return;
        }

        //Idozito elinditasa, 5000 ms-enkent inditodik el
        if (!timerRunning) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new ConnectionCheckTask(), 0, 5000);
            timerRunning = true;
        }

        try {
            //Iro es olvaso inicializalasa
            BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
            isr = new InputStreamReader(is);
            pw = new PrintWriter(connection.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        address = connection.getInetAddress().toString();
        System.out.println("Client connected at " + address + " with id: " + ID);

        //Addig varunk bejovo uzeneteke amig van kapcsolat
        while (isConnected) {
            try {
                if (isr.ready()) {
                    int character;
                    StringBuffer process = new StringBuffer();
                    character = isr.read();
                    //6-os (acknowledge) karaktert ignoraljuk, kapcsolat allapot ellenorzesere van
                    if (character != 6) {
                        //addig olvasunk amig lezaro karakter nem jon
                        while (character != 13) {
                            process.append((char) character);
                            character = isr.read();
                        }
                        System.out.println(process);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Client disconnected at " + address + " with id: " + ID);
        //callback, hogy lekapcsolodtunk
        if (listener != null){
            listener.onDisconnect(this);
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Uzenet kuldese a kliensnek
     * @param message
     */
    public void sendMessage(String message) {
        if (pw != null){
            pw.write(message + END);
        }
    }

    /**
     * kapcsolat bontasa a klienssel
     */
    public void disconnect() {
        try {
            connection.close();
            isConnected = false;
            if (listener != null){
                listener.onDisconnect(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    /**
     * Idozitett feladat kapcsolat allapotanak ellenorzesere
     */
    private class ConnectionCheckTask extends TimerTask {
        @Override
        public void run() {
            if (pw != null){
                //kuldunk egy 6-os (acknowledge) karaktert
                pw.write(6);
                //checkError igazzal ter vissza, ha nincs kapcsolat (nem sikerult az iras)
                if (pw.checkError()){
                    isConnected = false;
                }
            }
            if (!isConnected){
                //Idozito leallitasa
                if (timerRunning) {
                    cancel();
                    timerRunning = false;
                }
            }
        }
    }

    //Interfesz, kapcsolat bontas callback
    public interface onDisconnectListener{
        /**
         * Callback, hogy kapcsolatot bontottunk
         * @param client this
         */
        public void onDisconnect(PhoebeClientHandler client);
    }
}