package com.blockmart.nbtenchanter.listeners;

import com.blockmart.nbtenchanter.NBTEnchanter;
import com.blockmart.nbtenchanter.utils.NBTUtils;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Level;

public class BlockBreakListener implements Listener {

    private final NBTEnchanter plugin;

    public BlockBreakListener(NBTEnchanter plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();

        // Example: NBT for 'auto_smelt'
        if (NBTUtils.hasNBT(plugin, heldItem, "auto_smelt") && player.hasPermission("nbtenchanter.effect.auto_smelt")) {
            String autoSmeltValue = NBTUtils.getNBTString(plugin, heldItem, "auto_smelt");
            if (autoSmeltValue != null && autoSmeltValue.equalsIgnoreCase("true")) {
                // Implement auto-smelt logic here, e.g., drop cooked items
                player.sendMessage("§6Your pickaxe has auto-smelt!");
                // For demonstration, let's just show a message. Real implementation would involve modifying drops.
                event.setCancelled(true); // Cancel original drops to handle custom drops.
                // block.getDrops(heldItem).stream().map(ItemStack::getType).forEach(type -> {
                //     // This is placeholder logic. A proper implementation needs a smelting recipe lookup.
                //     ItemStack smeltedItem = new ItemStack(type, 1); // This is wrong without recipe lookup.
                //     block.getWorld().dropItemNaturally(block.getLocation(), smeltedItem);
                // });
                block.getDrops(heldItem).forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
                block.setType(org.bukkit.Material.AIR);
            }
        }

        // Example: NBT for 'glowing'
        if (NBTUtils.hasNBT(plugin, heldItem, "glowing") && player.hasPermission("nbtenchanter.effect.glowing")) {
            String glowingValue = NBTUtils.getNBTString(plugin, heldItem, "glowing");
            if (glowingValue != null && glowingValue.equalsIgnoreCase("true")) {
                player.sendMessage("§bYour item is glowing!");
            }
        }
    }
}