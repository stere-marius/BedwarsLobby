package ro.marius.bedwars.configuration;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.ConsoleLogger;
import ro.marius.bedwars.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LanguageConfiguration {

    private File languageFile;
    private YamlConfiguration config;
    private final Set<String> languagePaths = new HashSet<>();
    private String defaultLanguagePath;

    private final BedwarsLobbyPlugin plugin;

    public LanguageConfiguration(BedwarsLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    public void readLanguagePaths() {
        ConfigurationSection configurationSection = config.getConfigurationSection("languages");
        languagePaths.addAll(configurationSection.getKeys(false));
    }

    public String getDefaultLanguagePath() {
        return defaultLanguagePath == null ?
                defaultLanguagePath = config.getString("default-language-path", "en") :
                defaultLanguagePath;
    }

    public void loadConfiguration() {

        languageFile = new File(plugin.getDataFolder(), "lang.yml");
        Logger log = Bukkit.getLogger();

        if (!languageFile.exists()) {
            try {
                plugin.getDataFolder().mkdir();
                languageFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
                log.severe("[Bedwars] Couldn't create language file.");
            }
        }

        config = YamlConfiguration.loadConfiguration(languageFile);
        for (LanguageKeys item : LanguageKeys.values()) {
            if (config.getString(item.getKey()) == null) {
                config.set("en." + item.getKey(), item.getObject());
            }
        }


        try {
            config.save(languageFile);
        } catch (IOException e) {
            log.log(Level.WARNING, "Bedwars: Bedwars to save lang.yml.");
            e.printStackTrace();
        }

    }

    public String getString(String path) {

        String value = config.getString(path);

        if (value == null) {
            ConsoleLogger.sendWarning("Missing lang data: " + path);
            value = "";
        }

        return Utils.translate(value);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

}
