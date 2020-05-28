package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import static me.mrarcane.crispycore.commands.BackCommand.back;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

/**
 * File generated by: MrArcane
 * 3/5/2019
 **/
public class HomeTeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player p = (org.bukkit.entity.Player) sender;
            if (args.length != 2) {
                sendChat(p, "&cUsage: /hometeleport <player> <home>");
                return true;
            }
            //TeleportCommand to players home
            OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
            PlayerManager td = new PlayerManager(t.getUniqueId().toString());
            ConfigurationSection th = td.getConfigurationSection("Home data");
            ConfigurationSection home = th.getConfigurationSection(args[1].toLowerCase());
            if (home == null) {
                sendChat(p, "&eHomes: &7" + td.getConfigurationSection("Home data").getKeys(false).toString().replace("[", "").replace("]", "").replace(",", "&c,&7"));
                sendChat(p, String.format("&cHome '&7%s&c' doesn't exist.", args[1].toLowerCase()));
                return true;
            }
            if (p.hasPermission("crispycore.back")) {
                back.put(p, p.getLocation());
                sendChat(p, "&eTo go back type &7/back");
            }
            p.teleport(new Location(Bukkit.getWorld(home.getString("w")), home.getDouble("x"), home.getDouble("y"), home.getDouble("z")));
            sendChat(p, String.format("&eTeleporting to &7%s's &ehome location: &7%s", t.getName(), args[1].toLowerCase()));
            return true;
        }
        return true;
    }
}
