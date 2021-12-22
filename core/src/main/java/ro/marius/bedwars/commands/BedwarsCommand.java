package ro.marius.bedwars.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.marius.bedwars.AbstractCommand;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.ISubCommand;
import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.commands.subcommands.NPCCommand;
import ro.marius.bedwars.configuration.LanguageKeys;
import ro.marius.bedwars.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class BedwarsCommand extends AbstractCommand {

    private final Map<String, ISubCommand> subCommands = new HashMap<>();
    private final BedwarsLobbyPlugin plugin;

    public BedwarsCommand(BedwarsLobbyPlugin plugin) {
        super("bedwars");
        this.plugin = plugin;
        this.subCommands.put("joinnpc", new NPCCommand(npcHandler));
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            return;
        }

        Player p = (Player) sender;

        if (args[0].equalsIgnoreCase("join")) {

            if (args.length < 2) {
                plugin.getLanguageHandler().sendMessage(LanguageKeys.JOIN_COMMAND_USAGE, p);
                return;
            }

            String arenaName = args[1];

            Arena arena = plugin.getArenaHandler().findArena(arenaName);

            if (arena == null) {
                plugin.getLanguageHandler().sendMessage(LanguageKeys.COULD_NOT_FIND_ARENA, p);
                return;
            }

            Utils.teleportServer(p, arena.getServerName(), plugin);

            return;
        }

        ISubCommand subCommand = this.subCommands.get(args[0].toLowerCase());

        if (subCommand == null) return;

        subCommand.onCommand(sender, args);

    }
}
