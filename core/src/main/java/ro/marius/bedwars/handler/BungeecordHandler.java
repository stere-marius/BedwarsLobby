package ro.marius.bedwars.handler;

import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.sockets.ServerInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class BungeecordHandler {

    private final List<String> servers = new ArrayList<>();
    private final Set<ServerInfo> serverInfo = new HashSet<>();
    private boolean isMessageAlreadySent = false;
    private boolean isMessageServerNameSent = false;

    private final BedwarsLobbyPlugin plugin;

    public BungeecordHandler(BedwarsLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    public void sendServerNameMessage() {
        if (isMessageAlreadySent) return;

        if (Bukkit.getOnlinePlayers().isEmpty()) return;


        Player p = Iterables.get(Bukkit.getOnlinePlayers(), 0);

        Bukkit.broadcastMessage("1. sendServerNameMessage");
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("GetServers");
            p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            Bukkit.broadcastMessage("2. sendServerNameMessage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        isMessageServerNameSent = true;

    }

    public void sendServersIPMessage() {

        if (Bukkit.getOnlinePlayers().isEmpty())
            return;

        Player p = Iterables.get(Bukkit.getOnlinePlayers(), 0);
        servers.forEach(server -> sendServerIPMessage(server, p));

        isMessageAlreadySent = true;
    }

    private void sendServerIPMessage(String serverName, Player player) {

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {

            Bukkit.broadcastMessage("Sending ServerIP for server " + serverName);
            out.writeUTF("ServerIP");
            out.writeUTF(serverName);
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            Bukkit.broadcastMessage("sendServerIPMessage(" + serverName + ")");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getBungeeName(String serverIP, int port) {
        Optional<ServerInfo> server = serverInfo
                .stream()
                .filter(s -> s.equals(new ServerInfo(serverIP, port)))
                .findFirst();

        return server.map(ServerInfo::getServerName).orElse(null);
    }

    public void setupServerName() {

        Optional<ServerInfo> serverInfo = this.serverInfo.stream()
                .filter(s -> s.equals(new ServerInfo(Bukkit.getIp(), Bukkit.getPort())))
                .findFirst();

        if (serverInfo.isPresent()) {
            Bukkit.broadcastMessage("Found the server name of the server " + Bukkit.getServer().getIp() + " as being "
                    + serverInfo.get().getServerName());
        } else {
            Bukkit.broadcastMessage("Couldn't find the ip " + Bukkit.getServer().getIp() + " in BungeeCord config.yml");
        }

    }

    public List<String> getServers() {
        return servers;
    }

    public Set<ServerInfo> getServerInfo() {
        return serverInfo;
    }

    public boolean isMessageAlreadySent() {
        return isMessageAlreadySent;
    }
}
