package com.itzlynx197.dynamicwhispers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WhisperTask extends BukkitRunnable {

    private final DynamicWhispers plugin;

    public WhisperTask(DynamicWhispers plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!plugin.getConfig().getBoolean("enabled", true)) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isNight(player.getWorld())) continue;
            if (!isAlone(player)) continue;
            PlayerData data = PlayerManager.getData(player.getUniqueId());
            long cooldownMs = plugin.getConfig().getLong("scare_cooldown_seconds", 60) * 1000;
            if (System.currentTimeMillis() - data.lastScare < cooldownMs) continue;
            double chance = plugin.getConfig().getDouble("base_scare_chance", 0.1);
            double lowHealthThreshold = plugin.getConfig().getDouble("low_health_threshold", 10);
            if (player.getHealth() < lowHealthThreshold) chance = plugin.getConfig().getDouble("low_health_chance", 0.2);
            if (Math.random() < chance) {
                triggerScare(player);
                data.lastScare = System.currentTimeMillis();
            }
        }
    }

    private boolean isNight(org.bukkit.World world) {
        long time = world.getTime();
        return time > 13000 && time < 23000;
    }

    private boolean isAlone(Player player) {
        double distance = plugin.getConfig().getDouble("alone_distance", 50);
        return player.getWorld().getPlayers().stream().noneMatch(p -> p != player && p.getLocation().distance(player.getLocation()) < distance);
    }

    private void triggerScare(Player player) {
        double rand = Math.random();
        if (rand < 0.4) {
            WhisperEntity.spawnEntity(player);
        } else if (rand < 0.7) {
            WhisperManager.sendRandomWhisper(player);
        } else if (rand < 0.9) {
            MessageManager.sendRandomMessage(player);
        } else {
            SoundManager.playRandomSound(player);
        }
    }
}