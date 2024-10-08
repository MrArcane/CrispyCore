package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

/**
 * File generated by: MrArcane
 * 5/28/2018
 **/
public class ModifyHomesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 0 || args.length == 1 || args.length > 2) {
            sendChat(sender, "&7&lUsage: &c/modifyhomes <player> <x>");
            return true;
        }
        org.bukkit.entity.Player p = (org.bukkit.entity.Player) Bukkit.getOfflinePlayer(args[0]);
        PlayerManager pd = new PlayerManager(p.getUniqueId().toString());
        ConfigurationSection pSection = pd.getConfigurationSection("Player");
        ;
        int mHomes = 0;
        if (pSection.getString("Max homes") != null) {
            mHomes = Integer.parseInt(pSection.getString("Max homes"));
        }
        int c = Integer.parseInt(args[1]) + mHomes;
        pSection.set("Max homes", c);
        pd.save();
        if (!args[1].contains("-")) {
            sendChat(sender, String.format("&7Added &a%s &7homes to &a%s&7. total: &a%s", args[1], p.getName(), c));
        } else {
            sendChat(sender, String.format("&7Removed &a%s &7homes to &a%s&7. total: &a%s", args[1].replace("-", ""), p.getName(), c));
        }
        sendChat(p, "&7You now have &a" + c + " homes&7.");
        return false;
    }
}
