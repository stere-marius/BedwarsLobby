package ro.marius.bedwars.npc.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.menu.JoinInventory;

public class JoinNpcListener implements Listener {

    private final BedwarsLobbyPlugin plugin;

    public JoinNpcListener(BedwarsLobbyPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractJoinNpcEvent e){
        Bukkit.broadcastMessage("PlayerInteractJoinNpcEvent " + e.getAction());
        if (!e.getAction().equalsIgnoreCase("INTERACT_AT")) return;

        int npcID = e.getNpcID();
        String arenaType = plugin.getNpcHandler().getNpcIdArenaType().get(npcID);
        Bukkit.broadcastMessage("npcID " + npcID);
        Bukkit.broadcastMessage("arenaType " + arenaType);

        if(arenaType == null) return;

        JoinInventory joinInventory = new JoinInventory(plugin.getGuiConfiguration(), arenaType);
        e.getPlayer().openInventory(joinInventory.getInventory());
    }

}
