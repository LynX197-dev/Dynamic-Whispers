package com.itzlynx197.dynamicwhispers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WhisperListener implements Listener {

    private final DynamicWhispers plugin;

    public WhisperListener(DynamicWhispers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerData data = PlayerManager.getData(e.getPlayer().getUniqueId());
        if (data.waited) {
            WhisperManager.sendWhisper(e.getPlayer(), "It waited for you.");
            data.waited = false;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerData data = PlayerManager.getData(e.getPlayer().getUniqueId());
        data.waited = true;
    }
}