package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.inventories.HomeInventory;
import me.mrarcane.crispycore.utils.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

/**
 * File generated by: MrArcane
 * 6/6/2018
 **/
public class HomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerUtil pd = new PlayerUtil(p.getUniqueId().toString());
            if (pd.getConfigurationSection("Home data") == null) {
                sendChat(p, "&cNo homes defined.");
                return true;
            }
            new HomeInventory(p);
        }
        return false;
    }

}
