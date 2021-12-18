package ro.marius.bedwars.utils;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.Set;

public class ConsoleWarning {

    private static final String BEDWARS_WARNING = "&4&l---------- BEDWARS ----------";

    public static void sendWarning(String message){
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
        Bukkit.getConsoleSender().sendMessage(message);
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
    }

    public static void sendWarning(List<String> messages){
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(message));
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
    }

}
