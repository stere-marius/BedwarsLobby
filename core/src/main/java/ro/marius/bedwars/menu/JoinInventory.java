package ro.marius.bedwars.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ro.marius.bedwars.utils.StringUtils;
import ro.marius.bedwars.utils.Utils;
import ro.marius.bedwars.utils.itembuilder.ItemBuilder;

import java.util.Map;

public class JoinInventory {

//    private final static int SIZE = GUIStructure.getInventorySize("Menu.BedwarsJoinNPC");
//    private final static String MENU_NAME = GUIStructure.getConfig().getString("Menu.BedwarsJoinNPC.InventoryName");
//    private final static Map<Integer, GUIItem> ITEMS = GUIStructure.readInventory("Menu.BedwarsJoinNPC");
//    private final String arenaType;
//    private final String arenaTypeFirstUpper;
//
//    public JoinInventory(String arenaType) {
//        this.arenaType = arenaType;
//        this.arenaTypeFirstUpper = StringUtils.getFirstLetterUpperCase(this.arenaType);
//    }
//
//    @Override
//    public @NotNull Inventory getInventory() {
//
//        Inventory inventory = Bukkit.createInventory(this, SIZE, Utils.translate(MENU_NAME));
//
//        for (Map.Entry<Integer, GUIItem> entry : ITEMS.entrySet()) {
//
//            int slot = entry.getKey();
//            ItemBuilder builder = entry.getValue().getBuilder().clone();
//            builder.setDisplayName(
//                    builder.getDisplayName().replace("<arenaTypeFirstLetterUppercase>", arenaTypeFirstUpper))
//                    .replaceInLore("<arenaTypeFirstLetterUppercase>", arenaTypeFirstUpper);
//
//            inventory.setItem(slot, builder.build());
//
//        }
//
//        return inventory;
//    }
//
//    @Override
//    public void onClick(InventoryClickEvent e) {
//
//        int slot = e.getSlot();
//        Player p = (Player) e.getWhoClicked();
//
//        e.setCancelled(true);
//
//        GUIItem item = ITEMS.get(slot);
//
//        if (item == null) {
//            return;
//        }
//
//        if (item.getPlayerCommands().isEmpty()) {
//            return;
//        }
//
//        for (String command : item.getPlayerCommands()) {
//            p.performCommand(command.replace("<arenaType>", arenaType));
//        }
//
//    }
}
