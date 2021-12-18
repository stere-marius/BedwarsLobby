package ro.marius.bedwars.npc;

import org.bukkit.Location;

import java.util.List;

public class NpcConfigDAO {

    private int id;
    private Location location;
    private String skinName;
    private String skinValue;
    private String skinSignature;
    private String arenaType;
    private List<String> lines;

    public NpcConfigDAO(int id, Location location, String skinName, String skinValue, String skinSignature, String arenaType, List<String> lines) {
        this.id = id;
        this.location = location;
        this.skinName = skinName;
        this.skinValue = skinValue;
        this.skinSignature = skinSignature;
        this.arenaType = arenaType;
        this.lines = lines;
    }

    public NpcConfigDAO(int id, Location location, String arenaType, List<String> lines) {
        this(id, location, null, null, null, arenaType, lines);
    }

    public NpcConfigDAO(int id, Location location, String skinName, String arenaType, List<String> lines) {
        this(id, location, skinName, null, null, arenaType, lines);
    }

    public NpcConfigDAO(int id, Location location, String skinValue, String skinSignature, String arenaType, List<String> lines) {
        this(id, location, null, skinValue, skinSignature, arenaType, lines);
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getSkinName() {
        return skinName;
    }

    public String getSkinValue() {
        return skinValue;
    }

    public String getSkinSignature() {
        return skinSignature;
    }

    public String getArenaType() {
        return arenaType;
    }

    public List<String> getLines() {
        return lines;
    }
}
