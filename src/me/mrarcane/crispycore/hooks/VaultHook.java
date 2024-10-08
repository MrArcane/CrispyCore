package me.mrarcane.crispycore.hooks;

import me.mrarcane.crispycore.Main;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import static me.mrarcane.crispycore.utils.ChatUtil.log;

public class VaultHook {
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    public static void loadVault() {
        setupPermissions();
        setupChat();
        setupEconomy();
        for (String groups : VaultHook.chat.getGroups()) {
            if (!Main.getSection("Groups").contains(groups)) {
                log(String.format("Group %s is not in the Groups section in the config!", groups));
            }
        }
    }

    private static boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private static boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
