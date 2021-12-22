package ro.marius.bedwars.sockets;

import org.bukkit.Bukkit;
import ro.marius.bedwars.arena.ArenaState;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.handler.BungeecordHandler;
import ro.marius.bedwars.utils.ConsoleLogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClientSocket extends Thread {

    private final Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    private boolean started = false;

    private final ArenaHandler arenaHandler;

    public ClientSocket(Socket socket, ArenaHandler arenaHandler) {
        this.arenaHandler = arenaHandler;
        this.socket = socket;
        this.setupInputOutputStreams();
    }

    private void setupInputOutputStreams(){
        try {
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(socket.getInputStream());
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String read = "";

        while (started) {

            try {
                read = input.readUTF();

                String[] info = read.split(",");

                Bukkit.broadcastMessage("Receiving info " + info.length);

                for (String s : info) {
                    System.out.println(s);
                }

                if (info.length == 10) {

                    String serverIP = info[0];
                    String port = info[1];
                    String arenaName = info[2];
                    String arenaType = info[3];
                    int playersPerTeam = Integer.parseInt(info[4]);
                    ArenaState arenaState = ArenaState.valueOf(info[5].toUpperCase());
                    int playersPlaying = Integer.parseInt(info[6]);
                    int maxPlayers = Integer.parseInt(info[7]);
                    Set<UUID> rejoin = convertToList(info[8]);
                    Set<UUID> spectators = convertToList(info[9]);

                    arenaHandler.addGame(
                            serverIP,
                            port,
                            arenaType,
                            arenaName,
                            playersPerTeam,
                            playersPlaying,
                            maxPlayers,
                            arenaState,
                            rejoin,
                            spectators
                    );

                }

            } catch (IOException e) {
                ConsoleLogger.sendError("An exception occurred during the socket connection. Closing the connection.");
                break;
//				e.printStackTrace();
            }

        }

        this.close();

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<UUID> convertToList(String string) {

        String split[] = string.replace("}", "").replace("{", "").split(",");

        Set<UUID> set = new HashSet<>();


        for (int i = 0; i < split.length; i++) {
            set.add(UUID.fromString(split[i]));
        }

        return set;
    }

}
