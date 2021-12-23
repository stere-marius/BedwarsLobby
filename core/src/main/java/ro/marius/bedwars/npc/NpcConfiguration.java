package ro.marius.bedwars.npc;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.NPCSkin;
import ro.marius.bedwars.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NpcConfiguration {

    private final File file;
    private final YamlConfiguration config;

    public NpcConfiguration(BedwarsLobbyPlugin plugin){
        this.file = new File(plugin.getDataFolder(), "npc.yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public List<NpcConfigDAO> getLoadedNPCsFromConfig() {

        if (this.config == null) {
            return Collections.emptyList();
        }
        if (this.config.getConfigurationSection("NPC") == null) {
            return Collections.emptyList();
        }

        List<NpcConfigDAO> npcConfigDAOList = new ArrayList<>();

        for (String strID : this.config.getConfigurationSection("NPC").getKeys(false)) {

            String path = "NPC." + strID;

            int id = Integer.parseInt(strID);
            Location location = Utils.getConvertedStringToLocation(this.config.getString(path + ".Location"));
            String skinName = this.config.getString(path + ".SkinName");
            String skinValue = this.config.getString(path + ".Skin.Value");
            String skinSignature = this.config.getString(path + ".Skin.Signature");
            String arenaType = this.config.getString(path + ".ArenaType");
            List<String> lines = this.config.getStringList(path + ".Lines");

            if (skinValue != null && skinSignature != null) {
                npcConfigDAOList.add(new NpcConfigDAO(id, location, skinValue, skinSignature, arenaType, lines));
                continue;
            }

            if (skinName != null) {
                npcConfigDAOList.add(new NpcConfigDAO(id, location, skinName, arenaType, lines));
                continue;
            }

            npcConfigDAOList.add(new NpcConfigDAO(id, location, arenaType, lines));
        }

        return npcConfigDAOList;
    }

    public int getNewNpcID() {
        ConfigurationSection npcConfigurationSection = this.config.getConfigurationSection("NPC");
        return npcConfigurationSection == null ? 0 : npcConfigurationSection.getKeys(false).size();
    }

    private String getNpcConfigPath(int id) {
        return "NPC." + id;
    }

    private void saveNPC(Location location, int id, String arenaType, List<String> lines) {
        String path = getNpcConfigPath(id);
        this.config.set(path + ".Location", Utils.getConvertedStringToLocation(location));
        this.config.set(path + ".ArenaType", arenaType);
        this.config.set(path + ".Lines", lines);
    }

    public void saveNPC(Location location, int id, String skinName, String arenaType, List<String> lines) {
        saveNPC(location, id, arenaType, lines);
        this.config.set(getNpcConfigPath(id) + ".SkinName", skinName);
        this.saveFile();
    }

    public void setSkin(int id, NPCSkin npcSkin){
        this.config.set(getNpcConfigPath(id) + ".Skin.Value", npcSkin.getValue());
        this.config.set(getNpcConfigPath(id) + ".Skin.Signature", npcSkin.getSignature());
        this.saveFile();
    }

    public void removeNpcConfiguration(int id) {
        this.config.set(getNpcConfigPath(id), null);
        this.saveFile();
    }

    public void saveFile() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
