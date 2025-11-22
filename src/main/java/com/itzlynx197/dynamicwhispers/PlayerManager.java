package com.itzlynx197.dynamicwhispers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private static final Map<UUID, PlayerData> dataMap = new HashMap<>();

    public static PlayerData getData(UUID uuid) {
        return dataMap.computeIfAbsent(uuid, k -> new PlayerData());
    }
}