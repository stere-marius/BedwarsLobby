package ro.marius.bedwars;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ro.marius.bedwars.configuration.LanguageConfiguration;
import ro.marius.bedwars.configuration.gui.GUIConfiguration;
import ro.marius.bedwars.database.Database;
import ro.marius.bedwars.database.mysql.MySQLDatabase;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.handler.LanguageHandler;
import ro.marius.bedwars.handler.PlayerDataHandler;
import ro.marius.bedwars.handler.VersionHandler;
import ro.marius.bedwars.npc.JoinNpcHologram;
import ro.marius.bedwars.npc.NPCHandler;

public class BedwarsLobbyPlugin extends JavaPlugin {

    private NPCHandler npcHandler;
    private ArenaHandler arenaHandler;
    private VersionHandler versionHandler;
    private GUIConfiguration guiConfiguration;
    private LanguageConfiguration languageConfiguration;
    private LanguageHandler languageHandler;
    private Database database;
    private PlayerDataHandler playerDataHandler;

    @Override
    public void onEnable() {
        JoinNpcHologram.ARMOR_STAND_METADATA = new FixedMetadataValue(this, "BedwarsStand");
        versionHandler = new VersionHandler();
        npcHandler = new NPCHandler(this);
        guiConfiguration = new GUIConfiguration(this);
        languageConfiguration = new LanguageConfiguration(this);
        languageHandler = new LanguageHandler(languageConfiguration);
        playerDataHandler = new PlayerDataHandler();
        this.setupDatabase();
    }

    public void setupDatabase() {
        database = new MySQLDatabase(
                this.getConfig().getString("MySQL.Username"),
                this.getConfig().getString("MySQL.Database"),
                this.getConfig().getString("MySQL.Password"),
                this.getConfig().getString("MySQL.Host"),
                this.getConfig().getInt("MySQL.Port")
        );
        database.createDatabase();
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

    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }

    public PlayerDataHandler getPlayerDataHandler() {
        return playerDataHandler;
    }

    public Database getDatabase() {
        return database;
    }
}
