package com.itzlynx197.dynamicwhispers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageManager {

    public static void sendRandomMessage(Player player) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(MessageManager.class);
        var messages = plugin.getConfig().getStringList("messages");
        if (messages.isEmpty()) return;
        String message = messages.get((int) (Math.random() * messages.size()));
        player.sendMessage(ChatColor.DARK_GRAY + message);
    }
}