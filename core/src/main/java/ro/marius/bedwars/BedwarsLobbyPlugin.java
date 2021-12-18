package ro.marius.bedwars;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.npc.JoinNpcHologram;
import ro.marius.bedwars.npc.NPCHandler;

public class BedwarsLobbyPlugin extends JavaPlugin {

    private NPCHandler npcHandler;
    private ArenaHandler arenaHandler;
    private VersionWrapper versionWrapper;

    @Override
    public void onEnable() {
        JoinNpcHologram.ARMOR_STAND_METADATA = new FixedMetadataValue(this, "BedwarsStand");
        npcHandler = new NPCHandler(this);
    }

    public NPCHandler getNpcHandler() {
        return npcHandler;
    }

    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }
}
