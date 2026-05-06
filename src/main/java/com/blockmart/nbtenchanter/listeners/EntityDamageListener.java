package com.blockmart.nbtenchanter.listeners;

import com.blockmart.nbtenchanter.NBTEnchanter;
import com.blockmart.nbtenchanter.utils.NBTUtils;
import org.bukkit.entity.LivingEntity; // Import LivingEntity
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamageListener implements Listener {

    private final NBTEnchanter plugin;

    public EntityDamageListener(NBTEnchanter plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack heldItem = player.getInventory().getItemInMainHand();

            // Example: NBT for 'fire_aspect_nbt'
            if (NBTUtils.hasNBT(plugin, heldItem, "fire_aspect") && player.hasPermission("nbtenchanter.effect.fire_aspect")) {
                String fireAspectValue = NBTUtils.getNBTString(plugin, heldItem, "fire_aspect");
                if (fireAspectValue != null) {
                    try {
                        int level = Integer.parseInt(fireAspectValue);
                        // Apply fire to the damaged entity
                        if (event.getEntity() instanceof LivingEntity) {
                            LivingEntity victim = (LivingEntity) event.getEntity();
                            victim.setFireTicks(level * 20 * 2); // 2 seconds per level
                            player.sendMessage("§cApplied fire aspect from NBT (Level " + level + ")!");
                        }
                    } catch (NumberFormatException e) {
                        plugin.getLogger().warning("Invalid 'fire_aspect' NBT value: " + fireAspectValue + " on item held by " + player.getName());
                    }
                }
            }
        }
    }
}