package ro.marius.bedwars.handler;

import org.bukkit.entity.Player;
import ro.marius.bedwars.configuration.LanguageConfiguration;
import ro.marius.bedwars.configuration.LanguageKeys;
import ro.marius.bedwars.utils.Utils;

import java.util.*;

public class LanguageHandler {

    private final Map<UUID, String> langPath = new HashMap<>();

    private Set<String> paths = new HashSet<>();

    private final LanguageConfiguration languageConfiguration;

    public LanguageHandler(LanguageConfiguration languageConfiguration) {
        this.languageConfiguration = languageConfiguration;
    }

    public void sendMessage(LanguageKeys path, Player... players) {

        for (Player p : players) {
            String message = languageConfiguration.getString(langPath.get(p.getUniqueId()) + "." + path.name());
            p.sendMessage(Utils.translate(message));
        }

    }

    public void sendListMessage(LanguageKeys key, Player... players) {
        for (Player p : players) {
            List<String> messages = languageConfiguration.getStringList(langPath.get(p.getUniqueId()) + "." + key.name());
            messages.forEach(m -> sendMessage(key, p));
        }
    }


    public String getLangPath(UUID uuid) {

        String path = langPath.get(uuid);

        if (path == null) {
            String first = languageConfiguration.getDefaultLanguagePath();
            langPath.put(uuid, first);
            return first;
        }

        return path;

    }

}
