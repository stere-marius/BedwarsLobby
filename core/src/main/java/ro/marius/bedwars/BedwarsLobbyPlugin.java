package ro.marius.bedwars;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ro.marius.bedwars.configuration.gui.GUIConfiguration;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.handler.VersionHandler;
import ro.marius.bedwars.npc.JoinNpcHologram;
import ro.marius.bedwars.npc.NPCHandler;

public class BedwarsLobbyPlugin extends JavaPlugin {

    private NPCHandler npcHandler;
    private ArenaHandler arenaHandler;
    private VersionHandler versionHandler;
    private GUIConfiguration guiConfiguration;

    @Override
    public void onEnable() {
        JoinNpcHologram.ARMOR_STAND_METADATA = new FixedMetadataValue(this, "BedwarsStand");
        npcHandler = new NPCHandler(this);
        guiConfiguration = new GUIConfiguration(this);
        versionHandler = new VersionHandler();
    }

    public NPCHandler getNpcHandler() {
        return npcHandler;
    }

    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public VersionHandler getVersionHandler() {
        return versionHandler;
    }

    public GUIConfiguration getGuiConfiguration() {
        return guiConfiguration;
    }
}
