import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class PhoebeTestClient {

    // ===========================================================
    // Constants
    // ===========================================================

    //Uzeneteket lezaro karakter
    public static final char END = (char)13;

    // ===========================================================
    // Fields
    // ===========================================================

    public static int port;
    public static String host;

    public static State state = State.DISCONNECTED;
	public static boolean timerRunning = false;

    public static PrintWriter pw;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public static void main(String[] args) {
        System.out.println("PhoebeTestClient initialized");

		//Szukseges valtozok inicializalasa
        Socket connection = null;
        InputStreamReader isr = null;

		//Idozito a kapcsolat megszakadas eszlelesere
        Timer timer = new Timer();

        boolean running = true;

		//Reader a konzolos parancsok beolvasasara
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] command;

        while (running){
            try {
				//Feltetel akkor igaz, amikor van valamit beolvasni
                if (br.ready()) {
                    line = br.readLine();
                    command = line.split(" ");
                    switch (state) {
                        case DISCONNECTED:
                            if (command.length >= 1) {
								//connect <ip cim> <port>, csatlakozas szerverhez
                                if (command[0].equals("connect")) {
                                    try {
                                        host = command[1];
                                        port = Integer.parseInt(command[2]);
										
										//Kapcsolat letrehozasa
                                        connection = new Socket(InetAddress.getByName(host), port);
                                        state = State.CONNECTED;
										
										//Olvasok es irok inicializalasa
                                        pw = new PrintWriter(connection.getOutputStream(), true);
                                        isr = new InputStreamReader(new BufferedInputStream(connection.getInputStream()), "US-ASCII");
                                        System.out.println("Connected to " + host + ":" + port);

										//Idozito elinditasa, 5000 ms-enkent inditodik el
										if (!timerRunning) {
                                            timer = new Timer();
											timer.scheduleAtFixedRate(new ConnectionCheckTask(), 0, 5000);
											timerRunning = true;
										}
                                    } catch (UnknownHostException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
										//Elvileg ide ugrik, ha network hiba tortenik (timeout, connection refused, etc.)
                                        e.printStackTrace();
                                    }
                                } else if (command[0].equals("send")) {
                                    System.out.println("Client not connected");
                                } else if (command[0].equals("disconnect")) {
                                    System.out.println("Client not connected");
                                } else if (command[0].equals("exit")) {
                                    running = false;
                                }
                            }
                            break;
                        case CONNECTED:
                            if (command.length >= 1) {
                                if (command[0].equals("connect")) {
                                    System.out.println("Client already connected to " + host + ":" + port);
                                } else if (command[0].equals("send")) { //send <szo>, karakterek kuldese a szervernek
                                    if (pw != null) {
										//Az uzenetet le kell zarni
                                        pw.write(command[1] + END);
                                    }
                                } else if (command[0].equals("disconnect")) { //disconnect, kapcsolat bontasa a szerverrel
                                    try {
                                        if (connection != null) {
                                            connection.close();
                                            state = State.DISCONNECTED;
                                            System.out.println("Disconnected from " + host + ":" + port);
                                            host = null;
                                            port = 0;
                                        }
										if (timerRunning) {
											timer.cancel();
											timerRunning = false;
										}
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if (command[0].equals("exit")) {
									//kapcsolat bontasa kilepes elott
                                    try {
                                        if (connection != null) {
                                            connection.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    running = false;
                                }
                            }
                            break;
                    }
                }
				//checkoljuk, hogy van-e bejovo uzenet
                if (isr != null && isr.ready()) {
                    int c;
                    StringBuffer instr = new StringBuffer();
                    c = isr.read();
					//6-os (acknowledge) karaktert ignoraljuk, kapcsolat allapot ellenorzesere van
                    if (c != 6) {
						//addig olvasunk amig lezaro karakter nem jon
                        while (c != 13) {
                            instr.append((char) c);
                            c = isr.read();
                        }
                        System.out.println(instr);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

	//enum kliens allapotara
    public static enum State{
        DISCONNECTED, CONNECTED
    }

	//Idozitett feladat kapcsolat allapotanak ellenorzesere
    private static class ConnectionCheckTask extends TimerTask {
        @Override
        public void run() {
            if (pw != null){
				//kuldunk egy 6-os (acknowledge) karaktert
                pw.write(6);
				//checkError igazzal ter vissza, ha nincs kapcsolat (nem sikerult az iras)
                if (pw.checkError()){
                    state = State.DISCONNECTED;
                    System.out.println("Disconnected from " + host + ":" + port);
                }
            }
            if (state == State.DISCONNECTED){
				//Idozito leallitasa
                if (timerRunning) {
					cancel();
					timerRunning = false;
				}
            }
        }
    }
}