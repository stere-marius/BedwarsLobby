package ro.marius.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.handler.BungeecordHandler;

public class BungeeJoinListener implements Listener {

    private final BungeecordHandler bungeecordHandler;

    public BungeeJoinListener(BungeecordHandler bungeecordHandler) {
        this.bungeecordHandler = bungeecordHandler;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        new BukkitRunnable() {
            @Override
            public void run() {

                if (!bungeecordHandler.isMessageAlreadySent()) {
                    bungeecordHandler.sendServerNameMessage();
                    return;
                }

                cancel();
            }
        }.runTaskTimer(JavaPlugin.getPlugin(BedwarsLobbyPlugin.class), 60L, 60L);
    }

}
