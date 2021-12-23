package ro.marius.bedwars.sockets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ro.marius.bedwars.arena.ArenaState;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.ConsoleLogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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

    private void setupInputOutputStreams() {
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

        ConsoleLogger.sendSuccess("&a&lFound a new client for server socket with the address " + socket.getInetAddress().getHostAddress());
        String read = "";

        while (started) {

            try {

                String string = input.readUTF();

                if (string.equalsIgnoreCase("TEST CONNECTION")) continue;

                JsonObject jsonObject = new JsonParser().parse(string).getAsJsonObject();

                String serverIP = jsonObject.get("ServerIP").getAsString();
                int port = jsonObject.get("ServerPort").getAsInt();
                String arenaName = jsonObject.get("GameName").getAsString();
                String arenaType = jsonObject.get("ArenaType").getAsString();
                int playersPerTeam = jsonObject.get("PlayersPerTeam").getAsInt();
                ArenaState arenaState = ArenaState.valueOf(jsonObject.get("MatchState").getAsString());
                int playersPlaying = jsonObject.get("MatchPlayers").getAsInt();
                int maxPlayers = jsonObject.get("MaxPlayers").getAsInt();
                Set<UUID> rejoin = getSetFromJSONArray(jsonObject.get("RejoinUUID").getAsJsonArray());
                Set<UUID> spectators = getSetFromJSONArray(jsonObject.get("SpectatorUUID").getAsJsonArray());

                Bukkit.broadcastMessage("Receiving info " + string);
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

            } catch (IOException e) {
                if (shouldLogException(e)) {
                    ConsoleLogger.sendError("An exception occurred during the socket connection. Closing the connection.");
                    e.printStackTrace();
                }
                break;
            }

        }

        this.close();

    }

    public boolean shouldLogException(IOException exception) {
        return !(exception instanceof SocketException);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<UUID> getSetFromJSONArray(JsonArray jsonArray) {
        Set<UUID> uuidSet = new HashSet<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            uuidSet.add(UUID.fromString(jsonArray.get(i).getAsString()));
        }

        return uuidSet;
    }

    public Set<UUID> convertToList(String string) {

        String[] split = string.replace("}", "").replace("{", "").split(",");

        Set<UUID> set = new HashSet<>();

        for (String s : split) {
            set.add(UUID.fromString(s));
        }

        return set;
    }

}
