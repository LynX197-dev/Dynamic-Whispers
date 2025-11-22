package com.itzlynx197.dynamicwhispers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WhisperManager {

    public static void sendWhisper(Player player, String message) {
        player.sendMessage(ChatColor.GRAY + message);
    }

    public static void sendRandomWhisper(Player player) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(WhisperManager.class);
        var whispers = plugin.getConfig().getStringList("whispers");
        if (whispers.isEmpty()) return;
        String whisper = whispers.get((int) (Math.random() * whispers.size()));
        sendWhisper(player, whisper);
    }
}