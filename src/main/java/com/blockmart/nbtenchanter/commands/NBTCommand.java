package com.blockmart.nbtenchanter.commands;

import com.blockmart.nbtenchanter.NBTEnchanter;
import com.blockmart.nbtenchanter.utils.NBTUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class NBTCommand implements CommandExecutor {

    private final NBTEnchanter plugin;

    public NBTCommand(NBTEnchanter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand.getType() == Material.AIR) {
            player.sendMessage("§cYou must be holding an item to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("§cUsage: /nbt <enchant|remove> ...");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "enchant":
                if (!player.hasPermission("nbtenchanter.command.enchant")) {
                    player.sendMessage("§cYou don't have permission to enchant items.");
                    return true;
                }
                return handleEnchant(player, hand, args);
            case "remove":
                if (!player.hasPermission("nbtenchanter.command.remove")) {
                    player.sendMessage("§cYou don't have permission to remove enchantments.");
                    return true;
                }
                return handleRemove(player, hand, args);
            default:
                player.sendMessage("§cUnknown subcommand. Use 'enchant' or 'remove'.");
                return true;
        }
    }

    private boolean handleEnchant(Player player, ItemStack item, String[] args) {
        if (args.length < 3) {
            player.sendMessage("§cUsage: /nbt enchant <key> <value>");
            return true;
        }

        String key = args[1];
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            valueBuilder.append(args[i]).append(" ");
        }
        String value = valueBuilder.toString().trim();

        NBTUtils.setNBTString(plugin, item, key, value);
        player.getInventory().setItemInMainHand(item);
        player.sendMessage("§aSuccessfully added NBT tag '" + key + "' with value '" + value + "'.");
        return true;
    }

    private boolean handleRemove(Player player, ItemStack item, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /nbt remove <key>");
            return true;
        }

        String key = args[1];
        if (NBTUtils.hasNBT(plugin, item, key)) {
            NBTUtils.removeNBT(plugin, item, key);
            player.getInventory().setItemInMainHand(item);
            player.sendMessage("§aSuccessfully removed NBT tag '" + key + "'.");
        } else {
            player.sendMessage("§cItem does not have NBT tag '" + key + "'.");
        }
        return true;
    }
}