package ro.marius.bedwars;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class v1_10_R1 implements VersionWrapper{
    @Override
    public void hidePlayer(Player p, Player playerToBeHidden, JavaPlugin javaPlugin) {
        VersionWrapper.super.hidePlayer(p, playerToBeHidden, javaPlugin);
    }

    @Override
    public void showPlayer(Player p, Player toShow, JavaPlugin javaPlugin) {
        VersionWrapper.super.showPlayer(p, toShow, javaPlugin);
    }

    @Override
    public void setOwner(SkullMeta skullMeta, UUID uuid) {
        VersionWrapper.super.setOwner(skullMeta, uuid);
    }

    @Override
    public void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle, boolean send, boolean sendAgainTitle) {

    }

    @Override
    public int getPing(Player p) {
        return 0;
    }

    @Override
    public ItemStack addGlow(ItemStack item) {
        return null;
    }

    @Override
    public ItemStack setNBTTag(ItemStack item, String tag, String value) {
        return null;
    }

    @Override
    public boolean containsNBTTag(ItemStack item, String tag) {
        return false;
    }

    @Override
    public String getNBTTag(ItemStack item, String tag) {
        return null;
    }
}
