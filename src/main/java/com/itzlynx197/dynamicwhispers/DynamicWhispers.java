package com.itzlynx197.dynamicwhispers;

import org.bukkit.plugin.java.JavaPlugin;

public class DynamicWhispers extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new WhisperListener(this), this);
        long interval = getConfig().getLong("task_interval_ticks", 100L);
        new WhisperTask(this).runTaskTimer(this, 0L, interval);
    }
}