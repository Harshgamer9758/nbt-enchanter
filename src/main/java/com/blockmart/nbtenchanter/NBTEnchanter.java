package com.blockmart.nbtenchanter;

import com.blockmart.nbtenchanter.commands.NBTCommand;
import com.blockmart.nbtenchanter.listeners.BlockBreakListener;
import com.blockmart.nbtenchanter.listeners.EntityDamageListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NBTEnchanter extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("NBTEnchanter has been enabled!");

        // Register commands
        this.getCommand("nbt").setExecutor(new NBTCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("NBTEnchanter has been disabled!");
    }
}