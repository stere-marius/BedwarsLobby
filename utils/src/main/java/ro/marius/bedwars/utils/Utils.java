package ro.marius.bedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final ServerVersion SERVER_VERSION;

    static {
        SERVER_VERSION = ServerVersion.valueOf("v" + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1));
    }


    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");

    public static String translate(String message) {

        if (SERVER_VERSION.isGreaterOrEqualThan(ServerVersion.v1_16_R2)) {
            Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', message));
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1)).toString());
            }

            return matcher.appendTail(buffer).toString();
        }


        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Location getConvertedStringToLocation(String string) {
        String[] sp = string.split(",");

        if (sp.length < 5) {
            return null;
        }

        World world = Bukkit.getWorld(sp[0]);
        double x = Double.parseDouble(sp[1]);
        double y = Double.parseDouble(sp[2]);
        double z = Double.parseDouble(sp[3]);
        float yaw = Float.parseFloat(sp[4]);
        float pitch = Float.parseFloat(sp[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String getStringIntCoordinates(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }

    public static String getConvertedStringToLocation(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ()
                + "," + location.getYaw() + "," + location.getPitch();
    }

    public static List<String> getConvertedLocationsToStrings(List<Location> blocks) {
        List<String> a = new ArrayList<>();
        blocks.forEach(locations -> a.add(getConvertedStringToLocation(locations)));
        return a;
    }

    public static List<Location> getConvertedStringsToLocations(List<String> stringList) {
        List<Location> list = new ArrayList<>();
        stringList.forEach(string -> list.add(getConvertedStringToLocation(string)));
        return list;
    }

}
