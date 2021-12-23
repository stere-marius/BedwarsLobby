package ro.marius.bedwars.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.marius.bedwars.AbstractCommand;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.ISubCommand;
import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.commands.subcommands.NPCCommand;
import ro.marius.bedwars.configuration.LanguageKeys;
import ro.marius.bedwars.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BedwarsCommand extends AbstractCommand {

    private final Map<String, ISubCommand> subCommands = new HashMap<>();
    private final BedwarsLobbyPlugin plugin;

    public BedwarsCommand(BedwarsLobbyPlugin plugin) {
        super("bedwars");
        this.plugin = plugin;
        this.subCommands.put("joinnpc", new NPCCommand(plugin.getNpcHandler()));
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

            p.sendMessage("Arena size " + plugin.getArenaHandler().getArenas().size());
            p.sendMessage("Arenas " + plugin.getArenaHandler().getArenas().stream().map(Arena::getName).collect(Collectors.toSet()));

            Arena arena = plugin.getArenaHandler().findArena(arenaName);

            if (arena == null) {
                plugin.getLanguageHandler().sendMessage(LanguageKeys.COULD_NOT_FIND_ARENA, p);
                return;
            }

            if (arena.getServerName() == null) {
                p.sendMessage(Utils.translate("&cCould not get the server name for the arena " + args[1] + "."));
                return;
            }

            Utils.teleportServer(p, arena.getServerName(), plugin);

            return;
        }

        if ("randomJoin".equalsIgnoreCase(args[0])) {

            if (args.length >= 2) {

                String arenaType = args[1];
                Optional<Arena> arenaOptional = plugin.getArenaHandler().getAvailableArenaByType(arenaType);

                if (!arenaOptional.isPresent()) {
                    plugin.getLanguageHandler().sendMessage(LanguageKeys.COULD_NOT_FIND_ARENA, p);
                    return;
                }

                Utils.teleportServer(p, arenaOptional.get().getServerName(), plugin);

                return;
            }

            Optional<Arena> arenaOptional = plugin.getArenaHandler().getAvailableEmptyArena();

            if (!arenaOptional.isPresent()) {
                plugin.getLanguageHandler().sendMessage(LanguageKeys.COULD_NOT_FIND_ARENA, p);
                return;
            }

            Utils.teleportServer(p, arenaOptional.get().getServerName(), plugin);

            return;
        }

        if(args[0].equalsIgnoreCase("")){

            return;
        }

        ISubCommand subCommand = this.subCommands.get(args[0].toLowerCase());

        if (subCommand == null) return;

        subCommand.onCommand(sender, args);

    }
}
