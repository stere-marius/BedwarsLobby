package ro.marius.bedwars.handler;

import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.arena.ArenaState;
import ro.marius.bedwars.arena.observers.NPCObserver;
import ro.marius.bedwars.npc.NPCHandler;
import ro.marius.bedwars.sockets.ServerInfo;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaHandler {

    private final Map<ServerInfo, Arena> serverInfoArena = new HashMap<>();

    private final BungeecordHandler bungeecordHandler;
    private final NPCHandler npcHandler;

    public ArenaHandler(BungeecordHandler bungeecordHandler, NPCHandler npcHandler) {
        this.bungeecordHandler = bungeecordHandler;
        this.npcHandler = npcHandler;
    }


    public void addGame(
            String serverIP,
            int serverPort,
            String arenaType,
            String arenaName,
            int playersPerTeam,
            int playersPlaying,
            int maxPlayers,
            ArenaState arenaState,
            Set<UUID> rejoin,
            Set<UUID> spectators
    ) {

        ServerInfo serverInfo = new ServerInfo(serverIP, serverPort);
        Arena arenaFound = serverInfoArena.get(serverInfo);

        if (arenaFound == null) {
            Arena arena = new Arena(arenaName, arenaType, playersPerTeam, maxPlayers, playersPlaying, arenaState, rejoin, spectators);
            arena.setServerName(bungeecordHandler.getBungeeName(serverIP, serverPort));
            arena.registerObserver(new NPCObserver(arena, npcHandler, this));
            serverInfoArena.put(serverInfo, arena);
            arena.notifyObservers();
            return;
        }

        arenaFound.setServerName(bungeecordHandler.getBungeeName(serverIP, serverPort));
        arenaFound.setName(arenaName);
        arenaFound.setType(arenaType);
        arenaFound.setState(arenaState);
        arenaFound.setPlayersPlaying(playersPlaying);
        arenaFound.setPlayersPerTeam(playersPerTeam);
        arenaFound.setRejoin(rejoin);
        arenaFound.setSpectators(spectators);

        arenaFound.notifyObservers();
    }

    public Arena findArena(String name) {
        return serverInfoArena.values().stream()
                .filter(s -> s.getName().equals(name) && s.getState() == ArenaState.WAITING)
                .findFirst().orElse(null);
    }

    public int getPlayersPlaying(String arenaType) {

        return serverInfoArena
                .values()
                .stream()
                .filter(arena -> arena.getType().equals(arenaType))
                .map(Arena::getPlayersPlaying)
                .mapToInt(Integer::intValue).sum();
    }

    public Set<String> getArenaTypes() {
        return serverInfoArena
                .values()
                .stream()
                .map(Arena::getType)
                .collect(Collectors.toSet());
    }

    public Collection<Arena> getArenas() {
        return serverInfoArena.values();
    }

    public Optional<ServerInfo> getArenaServerInfo(Arena arena){

        return serverInfoArena
                .keySet()
                .stream()
                .filter(serverInfo -> serverInfoArena.get(serverInfo) == arena)
                .findFirst();
    }

    public Map<ServerInfo, Arena> getServerInfoArena() {
        return serverInfoArena;
    }

    public Optional<Arena> getAvailableArenaByType(String arenaType) {
        return getArenas()
                .stream()
                .filter(a -> a.getType().equals(arenaType) && a.getState() == ArenaState.WAITING && a.getPlayersPlaying() < a.getMaxPlayers())
                .findFirst();
    }

    public Optional<Arena> getAvailableEmptyArena() {
        return getArenas()
                .stream()
                .filter(a -> a.getState() == ArenaState.WAITING && a.getPlayersPlaying() < a.getMaxPlayers())
                .findFirst();
    }
}
