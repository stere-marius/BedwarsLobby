package ro.marius.bedwars.arena;

import ro.marius.bedwars.arena.observers.ArenaObserver;

import java.util.*;

public class Arena {

    private String serverName;
    private String name;
    private String type;
    private int playersPerTeam;
    private int minTeamsToStart;
    private int playersPlaying;
    private int maxPlayers;
    private ArenaState state;

    private Set<UUID> rejoin, spectators;
    private final List<ArenaObserver> observers = new ArrayList<>();

    public Arena(
                 String name,
                 String type,
                 int playersPerTeam,
                 int maxPlayers,
                 int playersPlaying,
                 ArenaState state,
                 Set<UUID> rejoin,
                 Set<UUID> spectators) {
        this.name = name;
        this.type = type;
        this.playersPerTeam = playersPerTeam;
        this.maxPlayers = maxPlayers;
        this.playersPlaying = playersPlaying;
        this.state = state;
        this.rejoin = rejoin;
        this.spectators = spectators;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String arenaType) {
        this.type = arenaType;
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public void setPlayersPerTeam(int playersPerTeam) {
        this.playersPerTeam = playersPerTeam;
    }

    public int getMinTeamsToStart() {
        return minTeamsToStart;
    }

    public void setMinTeamsToStart(int minTeamsToStart) {
        this.minTeamsToStart = minTeamsToStart;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayersPlaying() {
        return playersPlaying;
    }

    public void setPlayersPlaying(int playersPlaying) {
        this.playersPlaying = playersPlaying;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public void setRejoin(Set<UUID> rejoin) {
        this.rejoin = rejoin;
    }

    public Set<UUID> getRejoin() {
        return rejoin;
    }

    public void setSpectators(Set<UUID> spectators) {
        this.spectators = spectators;
    }

    public Set<UUID> getSpectators() {
        return spectators;
    }

    public void notifyObservers(){
        observers.forEach(ArenaObserver::update);
    }

    public void registerObserver(ArenaObserver arenaObserver) {
        this.observers.add(arenaObserver);
    }

    public void removeObserver(ArenaObserver arenaObserver) {
        this.observers.remove(arenaObserver);
    }

}
