package ro.marius.bedwars.npc;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.NPCSkin;
import ro.marius.bedwars.arena.observers.NPCObserver;
import ro.marius.bedwars.npc.bedwars.BedwarsNPC;
import ro.marius.bedwars.npc.citizens.CitizensNPC;
import ro.marius.bedwars.npc.skin.SkinFetcher;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class NPCHandler {

    private final Map<String, List<NPCArena>> arenaTypeNpc = new HashMap<>();
    private final Map<Integer, String> npcIdArenaType = new HashMap<>();

    private final BedwarsLobbyPlugin plugin;
    private final NpcConfiguration npcConfiguration;

    private BedwarsJoinNPC joinNPC;

    public NPCHandler(BedwarsLobbyPlugin plugin){
        this.plugin = plugin;
        this.npcConfiguration = new NpcConfiguration(plugin);
    }

    public void loadNPCGameObservers() {
        plugin.getArenaHandler()
                .getArenas()
                .forEach(game -> game.registerObserver(new NPCObserver(game, this, plugin.getArenaHandler())));
    }


    public void loadNPCs() {
        String joinNPCAdapter = plugin.getConfig().getString("JoinArenaNpcAdapter", "BEDWARS");

        // For Citizens adapter the load NPC logic will be handled when the Citizens plugin gets loaded
        this.joinNPC = "CITIZENS".equalsIgnoreCase(joinNPCAdapter) ? new CitizensNPC() : new BedwarsNPC(plugin);
        Bukkit.getServer().getPluginManager().registerEvents(joinNPC, plugin);

        if (!joinNPCAdapter.equalsIgnoreCase("CITIZENS")) {
            spawnConfiguredNPCs();
        }
    }

    public void spawnConfiguredNPCs(){
        List<NpcConfigDAO> configuredNPCs = npcConfiguration.getLoadedNPCsFromConfig();
        for(NpcConfigDAO npcConfigDAO : configuredNPCs){
            if (npcConfigDAO.getSkinValue() != null && npcConfigDAO.getSkinSignature() != null) {
                spawnNPC(npcConfigDAO.getId(),
                        npcConfigDAO.getLocation(),
                        new NPCSkin(npcConfigDAO.getSkinValue(), npcConfigDAO.getSkinSignature()),
                        npcConfigDAO.getArenaType(),
                        npcConfigDAO.getLines());
                continue;
            }

            if (npcConfigDAO.getSkinName() != null) {
                spawnNPC(npcConfigDAO.getId(),
                        npcConfigDAO.getLocation(),
                        npcConfigDAO.getSkinName(),
                        npcConfigDAO.getArenaType(),
                        npcConfigDAO.getLines());
                continue;
            }

            spawnNPC(npcConfigDAO.getId(),
                    npcConfigDAO.getLocation(),
                    SkinFetcher.DEFAULT_NPC_SKIN,
                    npcConfigDAO.getArenaType(),
                    npcConfigDAO.getLines());
        }
    }

    public void spawnNPC(int index, Location location, String skinName, String arenaType, List<String> lines) {
        joinNPC.spawnNPC(index, location, skinName);
        setupNPCArena(index, location, arenaType, lines);
    }

    public void spawnNPC(int index, Location location, NPCSkin npcSkin, String arenaType, List<String> lines) {
        joinNPC.spawnNPC(index, location, npcSkin);
        setupNPCArena(index, location, arenaType, lines);
    }

    private void setupNPCArena(int index, Location location, String arenaType, List<String> lines) {
        List<NPCArena> npcList = this.arenaTypeNpc.computeIfAbsent(arenaType, k -> new ArrayList<>());
        NPCArena npcArena = new NPCArena(index, location, arenaType, lines);
        npcArena.getNpcHologram().spawnHolograms(plugin.getArenaHandler().getPlayersPlaying(arenaType));
        npcList.add(npcArena);
    }


    public List<NPCArena> getNPCList(Chunk chunk) {
        return  arenaTypeNpc
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .stream()
                .filter(npc -> npc.getNpcHologram().getLocation().getChunk().equals(chunk))
                .collect(Collectors.toList());
    }

    public Optional<NPCArena> getNPCByUUID(UUID uuid) {

        return  arenaTypeNpc
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .stream()
                .filter(npc -> npc.getUuid().equals(uuid))
                .findFirst();
    }

    public void despawnNPC(int id) {
        joinNPC.removeNPC(id);
    }

    public int getNextNPCId(){ return npcConfiguration.getNewNpcID();}

    public NpcConfiguration getNpcConfiguration() {
        return npcConfiguration;
    }
    public Map<Integer, String> getNpcIdArenaType() {
        return npcIdArenaType;
    }

    public Map<String, List<NPCArena>> getArenaTypeNpc() {
        return arenaTypeNpc;
    }

    public void setSkin(int index, NPCSkin npcSkin) {
        joinNPC.updateSkin(index, npcSkin);
        npcConfiguration.setSkin(index, npcSkin);
    }
}
