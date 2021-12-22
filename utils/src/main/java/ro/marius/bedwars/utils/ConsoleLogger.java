package ro.marius.bedwars.utils;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.Set;

public class ConsoleLogger {

    private static final String BEDWARS_WARNING = "&4&l---------- BEDWARS ----------";
    private static final String BEDWARS_ERROR = "&c&l---------- BEDWARS ----------";
    private static final String BEDWARS_SUCCESS = "&a&l---------- BEDWARS ----------";

    public static void sendWarning(String message){
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
        Bukkit.getConsoleSender().sendMessage(message);
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
    }

    public static void sendError(String message){
        Bukkit.getConsoleSender().sendMessage(BEDWARS_ERROR);
        Bukkit.getConsoleSender().sendMessage(message);
        Bukkit.getConsoleSender().sendMessage(BEDWARS_ERROR);
    }

    public static void sendSuccess(String message){
        Bukkit.getConsoleSender().sendMessage(BEDWARS_SUCCESS);
        Bukkit.getConsoleSender().sendMessage(message);
        Bukkit.getConsoleSender().sendMessage(BEDWARS_SUCCESS);
    }

    public static void sendWarning(List<String> messages){
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
        messages.forEach(message -> Bukkit.getConsoleSender().sendMessage(message));
        Bukkit.getConsoleSender().sendMessage(BEDWARS_WARNING);
    }

}
