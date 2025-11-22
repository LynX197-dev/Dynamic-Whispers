package com.itzlynx197.dynamicwhispers;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WhisperEntity {

    public static void spawnEntity(Player player) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(WhisperEntity.class);
        Location playerLoc = player.getLocation();
        Vector direction = new Vector(Math.random() - 0.5, 0, Math.random() - 0.5).normalize();
        double minDist = plugin.getConfig().getDouble("entity_distance_min", 20);
        double maxDist = plugin.getConfig().getDouble("entity_distance_max", 30);
        double distance = minDist + Math.random() * (maxDist - minDist);
        Location spawnLoc = playerLoc.add(direction.multiply(distance));
        spawnLoc.setY(playerLoc.getY() + 1); // Slightly above ground

        // Create black leather armor items
        ItemStack blackHelmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) blackHelmet.getItemMeta();
        helmetMeta.setColor(Color.BLACK);
        blackHelmet.setItemMeta(helmetMeta);

        ItemStack blackChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) blackChestplate.getItemMeta();
        chestMeta.setColor(Color.BLACK);
        blackChestplate.setItemMeta(chestMeta);

        ItemStack blackLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legMeta = (LeatherArmorMeta) blackLeggings.getItemMeta();
        legMeta.setColor(Color.BLACK);
        blackLeggings.setItemMeta(legMeta);

        ItemStack blackBoots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootMeta = (LeatherArmorMeta) blackBoots.getItemMeta();
        bootMeta.setColor(Color.BLACK);
        blackBoots.setItemMeta(bootMeta);

        // Spawn invisible armor stand with black armor
        final ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(spawnLoc, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        armorStand.setGravity(false);
        armorStand.getEquipment().setHelmet(blackHelmet);
        armorStand.getEquipment().setChestplate(blackChestplate);
        armorStand.getEquipment().setLeggings(blackLeggings);
        armorStand.getEquipment().setBoots(blackBoots);

        // Double spawn sometimes
        double doubleChance = plugin.getConfig().getDouble("double_entity_chance", 0.1);
        if (Math.random() < doubleChance) {
            Location secondLoc = spawnLoc.add(new Vector(5, 0, 5));
            final ArmorStand secondStand = (ArmorStand) player.getWorld().spawnEntity(secondLoc, EntityType.ARMOR_STAND);
            secondStand.setVisible(false);
            secondStand.setMarker(true);
            secondStand.setGravity(false);
            secondStand.getEquipment().setHelmet(blackHelmet);
            secondStand.getEquipment().setChestplate(blackChestplate);
            secondStand.getEquipment().setLeggings(blackLeggings);
            secondStand.getEquipment().setBoots(blackBoots);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!secondStand.isDead()) {
                    secondStand.remove();
                }
            }, 40L); // 2s
        }

        // Despawn after configured duration
        long minDuration = plugin.getConfig().getLong("entity_duration_min", 40);
        long maxDuration = plugin.getConfig().getLong("entity_duration_max", 80);
        long despawnTime = minDuration + (long) (Math.random() * (maxDuration - minDuration));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }, despawnTime);

        // Send initial whisper
        WhisperManager.sendWhisper(player, "Someone is watching you...");
    }
}