package ro.marius.bedwars.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ro.marius.bedwars.menu.ExtraInventory;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClickExtra(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (e.getInventory().getHolder() == null) {
            return;
        }

        if (!(e.getView().getTopInventory().getHolder() instanceof ExtraInventory)) {
            return;
        }

        ExtraInventory extraInventory = (ExtraInventory) e.getView().getTopInventory().getHolder();
        extraInventory.onClick(e);
    }

}
