package ro.marius.bedwars.commands.subcommands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.marius.bedwars.ISubCommand;
import ro.marius.bedwars.npc.NPCArena;
import ro.marius.bedwars.npc.NPCHandler;
import ro.marius.bedwars.npc.skin.SkinFetcher;
import ro.marius.bedwars.Utils;

import java.util.*;

public class NPCCommand implements ISubCommand {

    private final NPCHandler npcHandler;

    public NPCCommand(NPCHandler npcHandler) {
        this.npcHandler = npcHandler;
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length == 1) {
            p.sendMessage("");
            p.sendMessage(Utils.translate("&e-----------------------------------------------"));
            p.sendMessage(Utils.translate(
                    "&e⇨ /bedwars joinNPC spawn <arenaType> <skinName> <firstLine>;<secondLine>;<thirdLine>..."));
            p.sendMessage(Utils.translate("&e⇨ /bedwars joinNPC setSkin <UUID> <playerName>"));
            p.sendMessage(Utils.translate("&e⇨ /bedwars joinNPC list"));
            p.sendMessage("");
            p.sendMessage(Utils.translate("&e-----------------------------------------------"));

            return;
        }

        if ("spawn".equalsIgnoreCase(args[1])) {

            if (args.length < 5) {
                p.sendMessage(Utils.translate(
                        "&cInsufficient arguments: /bedwars joinNPC spawn <arenaType> <skinName> <firstLine>;<secondLine>;<thirdLine>..."));
                return;
            }

            String arenaType = args[2];
            String skinName = args[3];
            List<String> lines = new ArrayList<>();

            StringBuilder builder = new StringBuilder();

            for (int i = 4; i < args.length; i++) {
                builder.append(args[i]);
                builder.append(" ");
            }

            String[] split = builder.toString().split(";");

            Collections.addAll(lines, split);

            int npcIndex = npcHandler.getNextNPCId();
            npcHandler.spawnNPC(npcIndex, p.getLocation(), skinName, arenaType, lines);
            npcHandler.getNpcConfiguration().saveNPC(p.getLocation(), npcIndex, skinName, arenaType, lines);

            return;
        }

        if ("list".equalsIgnoreCase(args[1])) {

            for (Map.Entry<String, List<NPCArena>> entry : npcHandler.getArenaTypeNpc().entrySet()) {

                String arenaType = entry.getKey();
                List<NPCArena> list = entry.getValue();

                p.sendMessage("");
                p.sendMessage(Utils.translate("&e-----------------------------------------------"));
                p.sendMessage(Utils.translate("&a" + arenaType + "'s Join NPC List &d[CLICKABLE MESSAGES]"));
                int index = 0;

                for (NPCArena npcArena : list) {

                    index++;

                    Location location = npcArena.getNpcHologram().getLocation();

                    TextComponent message = new TextComponent(Utils.translate("&e⇨ " + index + "."));

                    TextComponent teleport = new TextComponent(Utils.translate("   &a&lTELEPORT&f   "));
                    teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            "/bedwars joinNPC teleport " + Utils.getConvertedStringToLocation(location)));
                    teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(Utils.translate("&aClick me to teleport")).create()));

                    TextComponent remove = new TextComponent(Utils.translate("&c&lREMOVE"));
                    remove.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            "/bedwars joinNPC remove " + npcArena.getUuid()));
                    remove.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(Utils.translate("&aClick me to remove the NPC")).create()));

                    TextComponent npcSkin = new TextComponent(Utils.translate("   &c&lCHANGE SKIN   "));
                    npcSkin.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                            "/bedwars joinNPC setSkin " + npcArena.getUuid() + " playerName"));
                    npcSkin.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(Utils.translate("&aClick me to write the command for changing the skin")).create()));

                    p.spigot().sendMessage(message, teleport, remove, npcSkin);
                }

            }

            return;
        }

        if ("setSkin".equalsIgnoreCase(args[1])) {

            if (args.length < 4) {
                p.sendMessage(Utils.translate("&e⇨ Usage: /bedwars joinNPC setSkin <UUID> <playerName>"));
                return;
            }

            UUID uuid = UUID.fromString(args[2]);
            Optional<NPCArena> optionalNPCArena = npcHandler.getNPCByUUID(uuid);

            if (!optionalNPCArena.isPresent()) {
                p.sendMessage(Utils.translate("&cCouldn't find the NPC with uuid " + uuid
                        + " . Use /bedwars joinNPC list for a better view of NPC."));
                return;
            }

            NPCArena npcArena = optionalNPCArena.get();

            p.sendMessage(Utils.translate("&aFetching the skin..."));

            SkinFetcher.getSkinFromName(args[3], npcSkin -> {
                npcHandler.setSkin(npcArena.getIndex(), npcSkin);
                p.sendMessage(Utils.translate("&aThe skin has been updated."));
            });

            return;
        }

        if ("remove".equalsIgnoreCase(args[1])) {

            if (args.length < 3) {
                p.sendMessage(Utils.translate("&e⇨ Usage: /bedwars joinNPC remove <UUID>"));
                return;
            }

            UUID uuid = UUID.fromString(args[2]);
            Optional<NPCArena> optionalNPCArena = npcHandler.getNPCByUUID(uuid);

            if (!optionalNPCArena.isPresent()) {
                p.sendMessage(Utils.translate("&cCouldn't find the NPC with uuid " + uuid
                        + " . Use /bedwars joinNPC list for a better view of NPC."));
                return;
            }

            NPCArena npcArena = optionalNPCArena.get();
            npcArena.remove();
            npcHandler.getNpcConfiguration().removeNpcConfiguration(npcArena.getIndex());
            npcHandler.despawnNPC(npcArena.getIndex());
            p.sendMessage(Utils.translate("&aThe NPC has been removed successfully."));
            return;
        }

        if ("teleport".equalsIgnoreCase(args[1])) {

            if (args.length < 3) {
                p.sendMessage(Utils.translate("&e⇨ Usage: /bedwars joinNPC teleport <location>"));
                return;
            }

            Location location = Utils.getConvertedStringToLocation(args[2]);

            if (location == null) {
                p.sendMessage(Utils.translate("&cCould not find the location . The world might be null."));
                return;
            }

            p.teleport(location);

        }
    }
}
