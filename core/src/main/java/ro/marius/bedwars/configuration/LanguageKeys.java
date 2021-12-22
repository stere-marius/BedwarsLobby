package ro.marius.bedwars.configuration;


public enum LanguageKeys {

    NO_PERMISSION("You don't have permission"),
    JOIN_COMMAND_USAGE("&cInsufficient arguments: /bedwars join <arenaName>"),
    COULD_NOT_FIND_ARENA("&cCould not find an arena with name <arenaName>"),
    SPAWN_NPC_COMMAND_USAGE("&cInsufficient arguments: /bedwars spawnJoinNPC <arenaType> <skinName> <lines>"),;


    private final Object message;

    LanguageKeys(Object message) {
        this.message = message;
    }

    public String getKey() {
        return this.name().toLowerCase().replace("_", "-");
    }

    public Object getObject() {
        return this.message;
    }

}
