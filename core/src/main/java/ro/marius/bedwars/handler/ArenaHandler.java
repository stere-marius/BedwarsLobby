package ro.marius.bedwars.handler;

import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.arena.ArenaState;
import ro.marius.bedwars.sockets.ServerInfo;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaHandler {

    private final Map<ServerInfo, Arena> arenas = new HashMap<>();

    public void addGame(
            String serverIP,
            String serverPort,
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
        Arena arenaFound = arenas.get(serverInfo);

        if (arenaFound == null) {
            Arena arena = new Arena(arenaName, arenaType, playersPerTeam, maxPlayers, playersPlaying, arenaState, rejoin, spectators);
            arenas.put(serverInfo, arena);
            arena.notifyObservers();
            return;
        }

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
        return arenas.values().stream()
                .filter(s -> s.getName().equals(name) && s.getState() == ArenaState.WAITING)
                .findFirst().orElse(null);
    }

    public int getPlayersPlaying(String arenaType) {

        return arenas
                .values()
                .stream()
                .filter(arena -> arena.getType().equals(arenaType))
                .map(Arena::getPlayersPlaying)
                .mapToInt(Integer::intValue).sum();
    }

    public Set<String> getArenaTypes() {
        return arenas
                .values()
                .stream()
                .map(Arena::getType)
                .collect(Collectors.toSet());
    }

    public Collection<Arena> getArenas() {
        return arenas.values();
    }

}
