package ro.marius.bedwars.handler;

import ro.marius.bedwars.playerdata.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataHandler {

    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public Map<UUID, PlayerData> getPlayerData() {
        return playerData;
    }
}
