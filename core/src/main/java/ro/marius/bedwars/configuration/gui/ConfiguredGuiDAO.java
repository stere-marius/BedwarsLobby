package ro.marius.bedwars.configuration.gui;

import java.util.Map;

public class ConfiguredGuiDAO {

    private final int size;
    private final String name;
    private final Map<Integer, ConfiguredGuiItemDAO> items;

    public ConfiguredGuiDAO(String name, int size, Map<Integer, ConfiguredGuiItemDAO> items) {
        this.name = name;
        this.size = size;
        this.items = items;
    }

    public Map<Integer, ConfiguredGuiItemDAO> getItems() {
        return items;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
