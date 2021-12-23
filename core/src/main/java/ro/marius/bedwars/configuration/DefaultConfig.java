package ro.marius.bedwars.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import ro.marius.bedwars.BedwarsLobbyPlugin;

public class DefaultConfig {


    public void loadConfiguration(BedwarsLobbyPlugin plugin){
        FileConfiguration config = plugin.getConfig();
        config.options().header("Official Documentation https://bitbucket.org/STRMarius/bedwarswiki/wiki/Home");
        config.options().copyHeader(true);
        config.addDefault("JoinArenaNpcAdapter", "BEDWARS");
        config.addDefault("PartyAdapter", "BEDWARS_ADAPTER");
        config.addDefault("MySQL.Enabled", false);
        config.addDefault("MySQL.Host", "localhost");
        config.addDefault("MySQL.Database", "TestDatabase");
        config.addDefault("MySQL.Username", "root");
        config.addDefault("MySQL.Password", "");
        config.addDefault("MySQL.Port", 3306);
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }

}
