package ro.marius.bedwars.configuration.gui;

import ro.marius.bedwars.itembuilder.ItemBuilder;

import java.util.List;

public class ConfiguredGuiItemDAO {

    private final ItemBuilder builder;
    private final List<String> playerCommands;

    public ConfiguredGuiItemDAO(ItemBuilder builder, List<String> playerCommands) {
        this.builder = builder;
        this.playerCommands = playerCommands;
    }

    public ItemBuilder getBuilder() {
        return this.builder;
    }

    public List<String> getPlayerCommands() {
        return this.playerCommands;
    }

}
