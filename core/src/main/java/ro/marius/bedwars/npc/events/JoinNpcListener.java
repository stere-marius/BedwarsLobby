package ro.marius.bedwars.npc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ro.marius.bedwars.BedwarsLobbyPlugin;

public class JoinNpcListener implements Listener {

    private final BedwarsLobbyPlugin plugin;

    public JoinNpcListener(BedwarsLobbyPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractJoinNpcEvent e){
        if (!e.getAction().equalsIgnoreCase("INTERACT_AT")) return;

        int npcID = e.getNpcID();
        String arenaType = plugin.getNpcHandler().getNpcIdArenaType().get(npcID);

        if(arenaType == null) return;

//        JoinInventory joinInventory = new JoinInventory(arenaType);
//        e.getPlayer().openInventory(joinInventory.getInventory());
    }

}
