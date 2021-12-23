package ro.marius.bedwars.arena.observers;

import org.bukkit.Bukkit;
import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.handler.ArenaHandler;
import ro.marius.bedwars.npc.NPCArena;
import ro.marius.bedwars.npc.NPCHandler;

import java.util.List;

public class NPCObserver implements ArenaObserver {

    private final Arena arena;
    private final NPCHandler npcHandler;
    private final ArenaHandler arenaHandler;

    public NPCObserver(Arena arena, NPCHandler npcHandler, ArenaHandler arenaHandler) {
        this.arena = arena;
        this.npcHandler = npcHandler;
        this.arenaHandler = arenaHandler;
    }

    @Override
    public void update() {
        String arenaType = arena.getType();
        List<NPCArena> list = npcHandler.getArenaTypeNpc().get(arenaType);

        if (list == null || list.isEmpty()) {
            return;
        }

        int players = arenaHandler.getPlayersPlaying(arenaType);
        list.forEach(s -> s.getNpcHologram().update(players));
        Bukkit.broadcastMessage("NPCObserver.update(" + players + ")");
    }
}
