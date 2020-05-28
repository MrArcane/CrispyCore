package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class YllidCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ChatUtil.broadcast(String.format("&7%s &esays Yllid Yllid!", sender.getName()));
        return false;
    }
}
