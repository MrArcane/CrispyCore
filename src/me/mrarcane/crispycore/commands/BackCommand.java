package me.mrarcane.crispycore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.mrarcane.crispycore.utils.ChatUtil.log;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

/**
 * File generated by: MrArcane
 * 7/20/2018
 **/
public class BackCommand implements CommandExecutor {
    public static HashMap<Player, Location> back = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (back.containsKey(sender)) {
                Player p = (Player) sender;
                p.teleport(back.get(p));
                back.remove(sender);
                sendChat(p, "&eYou went back to your last location.");
                return true;
            }
            sendChat(sender, "&cNo locations stored.");
            return true;
        }
        log("&cOnly players can use this command!");
        return false;
    }
}
