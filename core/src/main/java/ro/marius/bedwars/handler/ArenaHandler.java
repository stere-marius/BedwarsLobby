package ro.marius.bedwars.handler;

import ro.marius.bedwars.arena.Arena;
import ro.marius.bedwars.arena.ArenaState;
import ro.marius.bedwars.arena.observers.ArenaObserver;

import java.awt.geom.Area;
import java.util.*;
import java.util.stream.Collectors;

public class ArenaHandler {

    private final Map<UUID, Arena> arenas = new HashMap<>();

    public void addGame(UUID uuid,
                        String serverName,
                        String arenaType,
                        String arenaName,
                        int playersPerTeam,
                        int playersPlaying,
                        int maxPlayers,
                        ArenaState arenaState,
                        Set<UUID> rejoin,
                        Set<UUID> spectators
                        ) {

        Arena arenaFound = arenas.get(uuid);

        if (arenaFound == null) {
            Arena arena = new Arena(arenaName, arenaType, playersPerTeam, maxPlayers, playersPlaying, arenaState, rejoin, spectators);
            arenas.put(uuid, arena);
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

    public int getPlayersPlaying(String arenaType) {

        return arenas
                .values()
                .stream()
                .filter(arena -> arena.getType().equals(arenaType))
                .map(Arena::getPlayersPlaying)
                .mapToInt(Integer::intValue).sum();
    }

    public Collection<Arena> getArenas(){
        return arenas.values();
    }

}
