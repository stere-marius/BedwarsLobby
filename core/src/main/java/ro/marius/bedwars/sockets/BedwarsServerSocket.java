package ro.marius.bedwars.sockets;

import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.handler.BungeecordHandler;
import ro.marius.bedwars.utils.ConsoleLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BedwarsServerSocket implements Runnable {

    private ServerSocket serverSocket;

    private final ArenaHandler arenaHandler;

    private final List<ClientSocket> clients = new ArrayList<>();

    private boolean isStarted = false;

    public BedwarsServerSocket(ArenaHandler arenaHandler,  int port) {
        this.arenaHandler = arenaHandler;
        this.setupServerSocket(port);
    }

    private void setupServerSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public void run() {
        ConsoleLogger.sendSuccess("Started BedwarsServerSocket. Finding clients.");

        while (isStarted) {

            try {
                Socket socket = serverSocket.accept();
                ClientSocket thread = new ClientSocket(socket, arenaHandler);
                thread.start();
                clients.add(thread);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

        this.close();
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
