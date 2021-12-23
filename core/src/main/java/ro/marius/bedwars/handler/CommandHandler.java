package ro.marius.bedwars.handler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import ro.marius.bedwars.AbstractCommand;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.commands.BedwarsCommand;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class CommandHandler {

    private final BedwarsLobbyPlugin plugin;

    public CommandHandler(BedwarsLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {

        Set<AbstractCommand> abstractCommand = new HashSet<>();
        abstractCommand.add(new BedwarsCommand(plugin));

        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            abstractCommand.forEach(command -> commandMap.register(command.getName(), command));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
