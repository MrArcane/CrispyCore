package me.mrarcane.crispycore.utils;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.hooks.Vault;
import org.bukkit.Bukkit;

import static me.mrarcane.crispycore.utils.ChatUtil.log;

public class HookUtil {
    public static void loadHooks() {
        if (Main.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
            log("Vault not found, disabled crispycore.");
        } else {
            log("Dependency Vault found.");
            Vault.loadVault();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) {
            log("GriefPrevention hook found.");
        } else {
            log("GriefPrevention hook not found, disabling.");
        }
    }
}
