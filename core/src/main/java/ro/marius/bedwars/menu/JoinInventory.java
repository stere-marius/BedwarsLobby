package ro.marius.bedwars.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ro.marius.bedwars.configuration.gui.ConfiguredGuiDAO;
import ro.marius.bedwars.configuration.gui.ConfiguredGuiItemDAO;
import ro.marius.bedwars.configuration.gui.GUIConfiguration;
import ro.marius.bedwars.utils.StringUtils;
import ro.marius.bedwars.utils.Utils;
import ro.marius.bedwars.utils.itembuilder.ItemBuilder;

import java.util.Map;

public class JoinInventory extends ExtraInventory {


    private final ConfiguredGuiDAO configuredGuiDAO;
    private final String arenaType;

    public JoinInventory(GUIConfiguration guiConfiguration, String arenaType) {
        this.configuredGuiDAO = guiConfiguration.readInventory("Menu.BedwarsJoinNPC");
        this.arenaType = arenaType;
    }

    @Override
    public @NotNull Inventory getInventory() {

        Inventory inventory = Bukkit.createInventory(this, configuredGuiDAO.getSize(), Utils.translate(configuredGuiDAO.getName()));

        for (Map.Entry<Integer, ConfiguredGuiItemDAO> entry : configuredGuiDAO.getItems().entrySet()) {

            int slot = entry.getKey();
            ItemBuilder builder = entry.getValue().getBuilder().clone();
            builder.setDisplayName(
                    builder.getDisplayName().replace("<arenaTypeFirstLetterUppercase>", StringUtils.getFirstLetterUpperCase(this.arenaType)))
                    .replaceInLore("<arenaTypeFirstLetterUppercase>", StringUtils.getFirstLetterUpperCase(this.arenaType));

            inventory.setItem(slot, builder.build());

        }

        return inventory;
    }

    @Override
    public void onClick(InventoryClickEvent e) {

        int slot = e.getSlot();
        Player p = (Player) e.getWhoClicked();

        e.setCancelled(true);

        ConfiguredGuiItemDAO item = configuredGuiDAO.getItems().get(slot);

        if (item == null) {
            return;
        }

        if (item.getPlayerCommands().isEmpty()) {
            return;
        }

        for (String command : item.getPlayerCommands()) {
            p.performCommand(command.replace("<arenaType>", arenaType));
        }

    }
}
