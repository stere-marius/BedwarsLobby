package ro.marius.bedwars.database;

import ro.marius.bedwars.playerdata.ArenaData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface Database {

    void createDatabase();

    CompletableFuture<ArenaData> loadPlayerArenaData(UUID uuid, String arenaType);

}
