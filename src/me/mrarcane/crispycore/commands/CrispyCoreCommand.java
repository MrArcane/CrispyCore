package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.utils.ChatUtil.*;

/**
 * File generated by: MrArcane
 * 11/7/2017
 **/
public class CrispyCoreCommand implements CommandExecutor {

    private Configuration cfg = Main.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        //Plugin information
        if (args.length == 0) {
            sendChat(sender, "&6Name: &7" + Main.getInstance().getName());
            sendChat(sender, "&6Author: &7" + Main.getInstance().getDescription().getAuthors());
            sendChat(sender, "&6Version: &7" + Main.getInstance().getDescription().getVersion());
            sendChat(sender, "&6Website: &7" + Main.getInstance().getDescription().getWebsite());
            sendChat(sender, "&6Discord: &7MrArcane#0197");
            return true;
        }
        //Debugger command
        if (args[0].equalsIgnoreCase("debug") && sender.isOp()) {
            if (cfg.getBoolean("Settings.Debug")) {
                cfg.set("Settings.Debug", false);
                Main.getInstance().saveConfig();
                sendChat(sender, "&6Debugger disabled.");
                return true;
            } else {
                cfg.set("Settings.Debug", true);
                Main.getInstance().saveConfig();
                sendChat(sender, "&6Debugger enabled.");
                return true;
            }
        }
        //April fools command
        if (args[0].equalsIgnoreCase("april") || args[0].equalsIgnoreCase("ap") && sender.isOp()) {
            if (cfg.getBoolean("April fools")) {
                cfg.set("April fools", false);
                Main.getInstance().saveConfig();
                sendChat(sender, "&6April fools disabled.");
                return true;
            } else {
                cfg.set("April fools", true);
                Main.getInstance().saveConfig();
                sendChat(sender, "&6April fools enabled.");
                return true;
            }
        }
        //Reload command
        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            Main.getInstance().saveConfig();
            Main.getInstance().reloadConfig();
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
            Bukkit.getPluginManager().enablePlugin(Main.getInstance());
            log("Reloaded successfully!");
            sendChat(sender, "&aReloaded CrispyCore!");
            return true;
        }
        //no command exists.
        sendChat(sender, "&cIncorrect command, try again.");
        return false;
    }
}
