package me.mrarcane.crispycore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.managers.AfkManager.setAfk;

/**
 * File generated by: MrArcane
 * 3/15/2019
 **/
public class AfkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            setAfk(p);
            return true;
        }

        return false;
    }
}
