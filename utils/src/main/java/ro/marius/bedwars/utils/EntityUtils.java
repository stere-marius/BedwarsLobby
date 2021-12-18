package ro.marius.bedwars.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class EntityUtils {

    public static ArmorStand getSpawnedArmorStand(Location location, String customName) {
        ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setCustomName(customName);
        stand.setCustomNameVisible(true);
        stand.setMarker(true);
        return stand;
    }

}
