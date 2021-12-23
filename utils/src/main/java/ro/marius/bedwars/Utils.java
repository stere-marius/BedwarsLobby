package ro.marius.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public static Set<Integer> getListOfIntegerFromObject(Object slot) {

        if (slot == null) return Collections.emptySet();

        if (slot instanceof Integer) return Collections.singleton((Integer) slot);

        if (!(slot instanceof String)) return Collections.emptySet();

        String array = (String) slot;

        return Arrays.stream(array.split(","))
                .filter(Utils::isInteger)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void teleportServer(Player p, String server, JavaPlugin javaPlugin) {

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        p.sendPluginMessage(javaPlugin, "BungeeCord", b.toByteArray());
    }

}
