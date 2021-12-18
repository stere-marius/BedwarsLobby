package ro.marius.bedwars.utils.itembuilder;

import org.bukkit.configuration.file.YamlConfiguration;
import ro.marius.bedwars.VersionWrapper;
import ro.marius.bedwars.utils.ConsoleWarning;
import ro.marius.bedwars.utils.XMaterial;

import java.util.Arrays;
import java.util.List;

public class ItemBuilderReader {


    public static ItemBuilder readFromConfig(YamlConfiguration configuration, String path, VersionWrapper versionWrapper) {

        String material = configuration.getString(path + ".Material");

        if (material == null) {
            ConsoleWarning.sendWarning(
                    Arrays.asList(
                            "&4&lINVALID ITEM",
                            "&ePath " + path + ".Material", "&eCouldn't find the material",
                            "&eReplaced it with STONE .")
            );
            material = "STONE";
        }

        int data = configuration.getInt(path + ".Data");
        XMaterial mat = XMaterial.matchXMaterial(material, (byte) data);

        if (mat == null) {
            ConsoleWarning.sendWarning(
                    Arrays.asList(
                            "&4&lINVALID ITEM",
                            "&ePath " + path + ".Material",
                            "&eCouldn't find the material " + material + (data != 0 ? " with data " + data : ""),
                            "&eReplaced it with STONE .")
            );
            mat = XMaterial.STONE;
        }

        int amount = configuration.getInt(path + ".Amount", 1);
        String displayName = configuration.getString(path + ".DisplayName");
        boolean glowing = configuration.getBoolean(path + ".Glowing");
        List<String> lore = configuration.getStringList(path + ".Lore");

        return new ItemBuilder(mat.parseItem(amount)).setDisplayName(displayName).setLore(lore).glowingItem(versionWrapper, glowing);
    }

}
