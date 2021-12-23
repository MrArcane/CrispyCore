package me.mrarcane.crispycore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            sendChat(p, "Pong!");
            return true;
        }
        sendChat(sender, "&cYou must be a player to use that command.");
        return false;
    }
}
