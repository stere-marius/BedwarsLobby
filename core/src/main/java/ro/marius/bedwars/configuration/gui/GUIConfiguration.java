package ro.marius.bedwars.configuration.gui;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.ConsoleLogger;
import ro.marius.bedwars.Utils;
import ro.marius.bedwars.XMaterial;
import ro.marius.bedwars.itembuilder.ItemBuilder;
import ro.marius.bedwars.itembuilder.ItemBuilderReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GUIConfiguration {

    private final File file;
    private YamlConfiguration config;
    private final BedwarsLobbyPlugin plugin;

    private final Map<String, ConfiguredGuiDAO> cacheReadGUI = new HashMap<>();

    public GUIConfiguration(BedwarsLobbyPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "menus.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void loadConfiguration() {
        String arenaMenuPath = "Menu.ArenaInventory";
        config.addDefault(arenaMenuPath + ".SearchingDisplay", "&aSearching players");
        config.addDefault(arenaMenuPath + ".StartingDisplay", "&aStarting in <seconds> s");
        String firstItemPath = arenaMenuPath + ".Contents.FIRST_ITEM";

        config.addDefault(arenaMenuPath + ".Size", 27);
        config.addDefault(arenaMenuPath + ".InventoryName", "&eJoin in arena");
        config.addDefault(arenaMenuPath + ".ArenaSlot", "9,10,11,12,13,14,15,16,17");

        if (!file.exists()) {
            config.addDefault(firstItemPath + ".Slot", "0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26");
            config.addDefault(firstItemPath + ".Material", "STAINED_GLASS_PANE");
            config.addDefault(firstItemPath + ".Data", 15);
            config.addDefault(firstItemPath + ".Amount", 1);
            config.addDefault(firstItemPath + ".DisplayName", " ");
            config.addDefault(firstItemPath + ".Glowing", false);
            config.addDefault(firstItemPath + ".Lore", Collections.singletonList(""));
        }

        config.addDefault(arenaMenuPath + ".WaitingArena.Material", "WOOL");
        config.addDefault(arenaMenuPath + ".WaitingArena.Data", (int) XMaterial.LIME_WOOL.getData());
        config.addDefault(arenaMenuPath + ".WaitingArena.Amount", 1);
        config.addDefault(arenaMenuPath + ".WaitingArena.DisplayName", "&e⇨<arenaName>");
        config.addDefault(arenaMenuPath + ".WaitingArena.Glowing", false);
        config.addDefault(arenaMenuPath + ".WaitingArena.Lore",
                Arrays.asList("&e⇨Arena <arenaName>", "&e⇨Mode <arenaType>", " "));

        config.addDefault(arenaMenuPath + ".StartingArena.Material", "WOOL");
        config.addDefault(arenaMenuPath + ".StartingArena.Data", (int) XMaterial.RED_WOOL.getData());
        config.addDefault(arenaMenuPath + ".StartingArena.Amount", 1);
        config.addDefault(arenaMenuPath + ".StartingArena.DisplayName", "&e⇨<arenaName>");
        config.addDefault(arenaMenuPath + ".StartingArena.Glowing", false);
        config.addDefault(arenaMenuPath + ".StartingArena.Lore",
                Arrays.asList("&e⇨Arena <arenaName>", "&e⇨Mode <arenaType>", "&aStarting in <seconds>"));

        config.addDefault(arenaMenuPath + ".NextPage.Slot", 26);
        config.addDefault(arenaMenuPath + ".NextPage.Material", "PAPER");
        config.addDefault(arenaMenuPath + ".NextPage.Data", 0);
        config.addDefault(arenaMenuPath + ".NextPage.Amount", 1);
        config.addDefault(arenaMenuPath + ".NextPage.DisplayName", "&e⇨Next page");
        config.addDefault(arenaMenuPath + ".NextPage.Glowing", false);
        config.addDefault(arenaMenuPath + ".NextPage.Lore", Collections.singletonList(""));

        config.addDefault(arenaMenuPath + ".PreviousPage.Slot", 25);
        config.addDefault(arenaMenuPath + ".PreviousPage.Material", "PAPER");
        config.addDefault(arenaMenuPath + ".PreviousPage.Data", 0);
        config.addDefault(arenaMenuPath + ".PreviousPage.Amount", 1);
        config.addDefault(arenaMenuPath + ".PreviousPage.DisplayName", "&e⇨Previous page");
        config.addDefault(arenaMenuPath + ".PreviousPage.Glowing", false);
        config.addDefault(arenaMenuPath + ".PreviousPage.Lore", Collections.singletonList(""));

        config.addDefault("Menu.BedwarsJoinNPC.Size", 36);
        config.addDefault("Menu.BedwarsJoinNPC.InventoryName", "&8Play Bed Wars");

        if (!file.exists()) {
            config.addDefault("Menu.BedwarsJoinNPC.Contents.BED.Slot", 12);
            config.addDefault("Menu.BedwarsJoinNPC.Contents.BED.Material", "BED");
            config.addDefault("Menu.BedwarsJoinNPC.Contents.BED.DisplayName", "&aBed Wars <arenaTypeFirstLetterUppercase>");
            config.addDefault("Menu.BedwarsJoinNPC.Contents.BED.Lore",
                    Arrays.asList("&7Play Bed Wars <arenaTypeFirstLetterUppercase>", "", "&eClick to play!"));
            config.addDefault("Menu.BedwarsJoinNPC.Contents.BED.PlayerCommands",
                    Collections.singletonList("bedwars randomJoin <arenaType>"));

            config.addDefault("Menu.BedwarsJoinNPC.Contents.MAP_SELECTOR.Slot", 14);
            config.addDefault("Menu.BedwarsJoinNPC.Contents.MAP_SELECTOR.Material", "SIGN");
            config.addDefault("Menu.BedwarsJoinNPC.Contents.MAP_SELECTOR.DisplayName",
                    "&aMap Selector (<arenaTypeFirstLetterUppercase>)");
            config.addDefault("Menu.BedwarsJoinNPC.Contents.MAP_SELECTOR.Lore",
                    Arrays.asList("&7Pick which map you want to play", "&7from a list of available servers.", "",
                            "&eClick to browse"));
            config.addDefault("Menu.BedwarsJoinNPC.Contents.MAP_SELECTOR.PlayerCommands",
                    Collections.singletonList("bedwars arenasGUI <arenaType>"));

            config.addDefault("Menu.BedwarsJoinNPC.Contents.CLOSE.Slot", 31);
            config.addDefault("Menu.BedwarsJoinNPC.Contents.CLOSE.Material", "ENDER_PEARL");
            config.addDefault("Menu.BedwarsJoinNPC.Contents.CLOSE.DisplayName", "&cClose");
            config.addDefault("Menu.BedwarsJoinNPC.Contents.CLOSE.PlayerCommands", Collections.singletonList("bedwars closeInventory"));
        }

        config.options().copyDefaults(true);
        saveConfig();
    }

    public ConfiguredGuiDAO readInventory(String path) {
        if (cacheReadGUI.containsKey(path)) return cacheReadGUI.get(path);

        int size = readInventorySize(path + ".Size");
        String name = config.getString(path + ".InventoryName");
        Map<Integer, ConfiguredGuiItemDAO> items = readInventoryItems(path);
        ConfiguredGuiDAO value = new ConfiguredGuiDAO(name, size, items);

        cacheReadGUI.putIfAbsent(path, value);

        return value;
    }

    private int readInventorySize(String path) {
        int size = config.getInt(path);

        if ((size % 9) != 0) {
            ConsoleLogger.sendWarning(Arrays.asList
                    (
                            "&4[Bedwars][Path=" + path + "] &cThe inventory size must be multiplier of 9 ",
                            "&cAs example: 9, 18, 27"
                    )
            );
            return 54;
        }

        return size;
    }

    private Map<Integer, ConfiguredGuiItemDAO> readInventoryItems(String path) {
        MemorySection memorySection = (MemorySection) config.getConfigurationSection(path + ".Contents");

        if (memorySection == null) return new HashMap<>();

        Map<Integer, ConfiguredGuiItemDAO> items = new HashMap<>();

        for (String name : memorySection.getKeys(false)) {
            Set<Integer> slots = Utils.getListOfIntegerFromObject(config.get(path + ".Contents." + name + ".Slot"));
            ItemBuilder builder = ItemBuilderReader.readFromConfig(config, path + ".Contents." + name, plugin.getVersionHandler().getVersionWrapper());
            List<String> playerCommands = config.getStringList(path + ".Contents." + name + ".PlayerCommands");
            slots.forEach(s -> items.put(s, new ConfiguredGuiItemDAO(builder, playerCommands)));
        }

        return items;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

}
