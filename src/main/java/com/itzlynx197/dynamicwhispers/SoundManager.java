package com.itzlynx197.dynamicwhispers;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SoundManager {

    public static void playRandomSound(Player player) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(SoundManager.class);
        var soundNames = plugin.getConfig().getStringList("sounds");
        if (soundNames.isEmpty()) return;
        String soundName = soundNames.get((int) (Math.random() * soundNames.size()));
        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            Location loc = player.getLocation();
            player.playSound(loc, sound, SoundCategory.AMBIENT, 0.3f, 0.5f + (float) Math.random() * 0.5f);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid sound name in config: " + soundName);
        }
    }
}