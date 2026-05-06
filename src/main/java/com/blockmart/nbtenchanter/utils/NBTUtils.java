package com.blockmart.nbtenchanter.utils;

import com.blockmart.nbtenchanter.NBTEnchanter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class NBTUtils {

    /**
     * Sets a custom String NBT tag on an ItemStack.
     * @param plugin The instance of your plugin.
     * @param item The ItemStack to modify.
     * @param key The key of the NBT tag.
     * @param value The value of the NBT tag.
     */
    public static void setNBTString(NBTEnchanter plugin, ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        NamespacedKey nbtKey = new NamespacedKey(plugin, key);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(nbtKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    /**
     * Gets a custom String NBT tag from an ItemStack.
     * @param plugin The instance of your plugin.
     * @param item The ItemStack to query.
     * @param key The key of the NBT tag.
     * @return The String value of the NBT tag, or null if not found.
     */
    public static String getNBTString(NBTEnchanter plugin, ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        NamespacedKey nbtKey = new NamespacedKey(plugin, key);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(nbtKey, PersistentDataType.STRING);
    }

    /**
     * Checks if an ItemStack has a specific custom NBT tag.
     * @param plugin The instance of your plugin.
     * @param item The ItemStack to check.
     * @param key The key of the NBT tag.
     * @return True if the NBT tag exists, false otherwise.
     */
    public static boolean hasNBT(NBTEnchanter plugin, ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        NamespacedKey nbtKey = new NamespacedKey(plugin, key);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(nbtKey, PersistentDataType.STRING);
    }

    /**
     * Removes a custom NBT tag from an ItemStack.
     * @param plugin The instance of your plugin.
     * @param item The ItemStack to modify.
     * @param key The key of the NBT tag to remove.
     */
    public static void removeNBT(NBTEnchanter plugin, ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        NamespacedKey nbtKey = new NamespacedKey(plugin, key);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.remove(nbtKey);
        item.setItemMeta(meta);
    }
}