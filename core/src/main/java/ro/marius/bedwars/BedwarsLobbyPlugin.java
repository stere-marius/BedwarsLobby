package ro.marius.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import ro.marius.bedwars.configuration.DefaultConfig;
import ro.marius.bedwars.configuration.LanguageConfiguration;
import ro.marius.bedwars.configuration.gui.GUIConfiguration;
import ro.marius.bedwars.database.Database;
import ro.marius.bedwars.database.mysql.MySQLDatabase;
import ro.marius.bedwars.handler.*;
import ro.marius.bedwars.listeners.BungeecordMessageListener;
import ro.marius.bedwars.listeners.InventoryClickListener;
import ro.marius.bedwars.npc.JoinNpcHologram;
import ro.marius.bedwars.npc.NPCHandler;
import ro.marius.bedwars.listeners.BungeeJoinListener;
import ro.marius.bedwars.npc.events.JoinNpcListener;
import ro.marius.bedwars.sockets.BedwarsServerSocket;

public class BedwarsLobbyPlugin extends JavaPlugin {

    private NPCHandler npcHandler;
    private ArenaHandler arenaHandler;
    private VersionHandler versionHandler;
    private GUIConfiguration guiConfiguration;
    private LanguageConfiguration languageConfiguration;
    private LanguageHandler languageHandler;
    private Database database;
    private PlayerDataHandler playerDataHandler;
    private BungeecordHandler bungeecordHandler;
    private BedwarsServerSocket bedwarsServerSocket;

    @Override
    public void onEnable() {
        JoinNpcHologram.ARMOR_STAND_METADATA = new FixedMetadataValue(this, "BedwarsStand");
        bungeecordHandler = new BungeecordHandler(this);
        npcHandler = new NPCHandler(this);
        arenaHandler = new ArenaHandler(bungeecordHandler, npcHandler);
        versionHandler = new VersionHandler();
        guiConfiguration = new GUIConfiguration(this);
        languageConfiguration = new LanguageConfiguration(this);
        languageHandler = new LanguageHandler(languageConfiguration);
        playerDataHandler = new PlayerDataHandler();
        this.setupDatabase();
        this.setupLoaders();
        this.setupListeners();
        this.setupSocket();
        bungeecordHandler.sendServerNameMessage();
    }

    @Override
    public void onDisable() {
        bedwarsServerSocket.setStarted(false);
        bedwarsServerSocket.closeClientsSocket();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    private void setupLoaders() {
        new CommandHandler(this).registerCommands();
        new DefaultConfig().loadConfiguration(this);
        npcHandler.loadNPCs();
        guiConfiguration.loadConfiguration();
        languageConfiguration.loadConfiguration();
    }

    private void setupDatabase() {
        database = new MySQLDatabase(
                this.getConfig().getString("MySQL.Username"),
                this.getConfig().getString("MySQL.Database"),
                this.getConfig().getString("MySQL.Password"),
                this.getConfig().getString("MySQL.Host"),
                this.getConfig().getInt("MySQL.Port")
        );
        database.createDatabase();
    }

    private void setupListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        Messenger messenger = getServer().getMessenger();
        messenger.registerIncomingPluginChannel(this, "BungeeCord", new BungeecordMessageListener(bungeecordHandler, arenaHandler));
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");
        pluginManager.registerEvents(new JoinNpcListener(this), this);
        pluginManager.registerEvents(new BungeeJoinListener(bungeecordHandler), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
    }

    private void setupSocket() {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit
                    .getScheduler()
                    .runTaskAsynchronously(BedwarsLobbyPlugin.this, bedwarsServerSocket = new BedwarsServerSocket(arenaHandler, 9999));
        }, 40L);
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
