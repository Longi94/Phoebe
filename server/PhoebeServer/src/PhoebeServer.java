import socket.PhoebeClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PhoebeServer extends Thread implements PhoebeClientHandler.onDisconnectListener {
    // ===========================================================
    // Constants
    // ===========================================================

    //A kliensek listaja
    private ArrayList<PhoebeClientHandler> clients;

    private int port;

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Konstruktor
     * @param port
     */
    public PhoebeServer(int port) {
        this.port = port;
        clients = new ArrayList<PhoebeClientHandler>();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Klient keresese kliens azonosito alapjan
     * @param id
     * @return a kliens, null ha nince ilyen azonositoju
     */
    public PhoebeClientHandler getClient(int id){
        for (PhoebeClientHandler client : clients){
            if (client.getClientId() == id && client.isConnected())
                return client;
        }
        return null;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void run() {
        //Azonosito autoinkrementalasara
        int count = 0;
        try {
            ServerSocket socket1 = new ServerSocket(port);
            System.out.println("PhoebeServer Initialized");

            //Folyamatosan varunk kapcsolodo kliensekre
            while (true) {
                Socket connection = socket1.accept();
                PhoebeClientHandler socketServer = new PhoebeClientHandler(connection, ++count);
                clients.add(socketServer);
                socketServer.setOnDisconnectListener(this);
                socketServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect(PhoebeClientHandler client) {
        //Lecsatlakozot kliens kivetele a listabol
        clients.remove(client);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public static void main(String[] args) {

        //Server elinditasa
        PhoebeServer server = new PhoebeServer(Integer.parseInt(args[0]));
        server.start();

        boolean running = true;

        //Reader a konzolos parancsok beolvasasara
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] command;

        while (running){
            try {
                if (br.ready()) {
                    line = br.readLine();
                    command = line.split(" ");

                    if (command[0].equals("announce")) { //announce <uzenet>
                        server.announce(command[1]);
                    } else if (command[0].equals("send")) { //send <id> <uzenet>
                        server.send(Integer.parseInt(command[1]), command[2]);
                    } else if (command[0].equals("drop")) { //drop <id>
                        server.drop(Integer.parseInt(command[1]));
                    } else if (command[0].equals("stop")) { //stop, szerver leallitasa
                        running = false;
                    } else if (command[0].equals("list")) { //list, kliensek kilistazasa
                        server.listClients();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Osszes szal leallitasa
        System.exit(0);
    }

    /**
     * Uzenet kuldese minden kliensnek
     * @param message
     */
    public void announce(String message) {
        for (PhoebeClientHandler client : clients){
            client.sendMessage(message);
        }
    }

    /**
     * Azuenet kuldese egy kliensnek
     * @param id
     * @param message
     */
    public void send(int id, String message) {
        PhoebeClientHandler client = getClient(id);
        client.sendMessage(message);
    }

    /**
     * Kliensnek kilistazasa konzolra
     */
    public void listClients(){
        for (PhoebeClientHandler client : clients){
            System.out.println("address:" + client.getAddress() + " id:" + client.getClientId());
        }
    }

    /**
     * Kapcsolat bontasa egy klienssel
     * @param id
     */
    public void drop(int id) {
        PhoebeClientHandler client = getClient(id);
        client.disconnect();
        clients.remove(client);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}