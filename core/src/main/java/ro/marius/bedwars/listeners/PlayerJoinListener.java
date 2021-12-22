package ro.marius.bedwars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.playerdata.ArenaData;
import ro.marius.bedwars.playerdata.PlayerData;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PlayerJoinListener implements Listener {

    private final BedwarsLobbyPlugin plugin;

    public PlayerJoinListener(BedwarsLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = new PlayerData(player.getUniqueId());

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {

            for (String arenaType : plugin.getArenaHandler().getArenaTypes()) {
                playerData.loadArenaData(arenaType, new ArenaData());
                CompletableFuture<ArenaData> arenaData = plugin.getDatabase().loadPlayerArenaData(player.getUniqueId(), arenaType);
                arenaData.whenComplete((data, error) -> playerData.loadArenaData(arenaType, data));
            }

        });

        plugin.getPlayerDataHandler().getPlayerData().put(player.getUniqueId(), playerData);
    }

}
