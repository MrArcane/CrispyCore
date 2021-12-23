package me.mrarcane.crispycore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class HugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length != 1) {
                sendChat(p, "&cUsage: /hug <player>");
                return true;
            }
            if (args[0].equals(p.getDisplayName())) {
                sendChat(p, "&cCome on, are you that lonely?");
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (args[0] == null) {
                sendChat(p, String.format("&c%s is offline!", args[0]));
                return true;
            }
            if (p.getWorld() != t.getWorld()) {
                sendChat(p, "&cSorry, interdimensional hugs are illegal!");
                return true;
            }
            if (p.getLocation().distance(t.getLocation()) > 5) {
                sendChat(p, String.format("&c%s is too far away, you must be within 5 blocks of each other!", t.getDisplayName()));
                return true;
            }
            sendChat(p, String.format("&dYou've hugged &7%s", t.getDisplayName()));
            sendChat(t, String.format("&dYou've been hugged by &7%s", p.getDisplayName()));
            p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0, 5, 0), 1000, 10, 5, 10);
            t.getWorld().spawnParticle(Particle.HEART, t.getLocation(), 1000, 10, 5, 10);
            return true;
        }
        return false;
    }
}