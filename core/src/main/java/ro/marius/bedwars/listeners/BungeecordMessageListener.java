package ro.marius.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import ro.marius.bedwars.ConsoleLogger;
import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.handler.BungeecordHandler;
import ro.marius.bedwars.sockets.ServerInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class BungeecordMessageListener implements PluginMessageListener {

    private int servers = 0;

    private final BungeecordHandler bungeecordHandler;
    private final ArenaHandler arenaHandler;

    public BungeecordMessageListener(BungeecordHandler bungeecordHandler, ArenaHandler arenaHandler) {
        this.bungeecordHandler = bungeecordHandler;
        this.arenaHandler = arenaHandler;
    }


    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player p, byte[] message) {
        Bukkit.broadcastMessage("onPluginMessageReceived");

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(message))) {
            final String subChannel = in.readUTF();

            Bukkit.broadcastMessage("subChannel = " + subChannel);

            if (subChannel.equals("GetServers")) {
                String[] split = in.readUTF().split(", ");

                for (String serverName : split) {
                    servers++;
                    Bukkit.broadcastMessage("Receiving " + serverName + " from channel GetServers");
                    bungeecordHandler.getServers().add(serverName);
                }

                bungeecordHandler.sendServersIPMessage();

                return;
            }

            if (subChannel.equals("ServerIP")) {
                String serverName = in.readUTF();
                String ip = in.readUTF();
                int port = in.readUnsignedShort();

                Bukkit.broadcastMessage(
                        "Receiving [" + serverName + " , " + ip + " : " + port + "] from channel ServerIP");

                ServerInfo serverInfo = new ServerInfo(ip, port);
                Arena arenaFoundByServerInfo = arenaHandler.getServerInfoArena().get(serverInfo);
                ConsoleLogger.sendWarning("&e&lTrying to find the arena by server info " + serverInfo);
                ConsoleLogger.sendWarning("&e&lArena found " + (arenaFoundByServerInfo == null ? " null " : arenaFoundByServerInfo.getName()));
                if (arenaFoundByServerInfo != null) arenaFoundByServerInfo.setServerName(serverName);
                bungeecordHandler.getServerInfo().add(new ServerInfo(ip, port, serverName));
                servers--;

                if (servers == 0) {
                    bungeecordHandler.setupServerName();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
