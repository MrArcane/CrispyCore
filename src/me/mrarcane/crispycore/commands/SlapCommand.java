package me.mrarcane.crispycore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static me.mrarcane.crispycore.utils.ChatUtil.broadcast;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

/**
 * File generated by: MrArcane
 * 8/5/2018
 **/
public class SlapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length != 1) {
                sendChat(p, "&cUsage: /slap <target>");
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                sendChat(p, String.format("&7%s &chas never played before.", args[0]));
                return true;
            }
            if (!t.isOnline()) {
                sendChat(p, String.format("&c%s is offline!", t));
                return true;
            }
            if (t == p) {
                sendChat(p, "&cSelf harm is bad!");
                return true;
            }
            t.setVelocity(Vector.getRandom());
            broadcast(String.format("&7%s &chas slapped &7%s&c!", p.getDisplayName(), t.getDisplayName()));
            return true;
        }
        return false;
    }
}
