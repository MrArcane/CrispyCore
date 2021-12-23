package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.managers.CoordinatesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.managers.CoordinatesManager.coordsMap;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class CoordinatesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        if (coordsMap.containsKey(p)) {
            coordsMap.remove(p);
            sendChat(p, "&6Action bar coordinates disabled!");
            return true;

        } else {
            coordsMap.put(p, true);
            CoordinatesManager.task(Main.getInstance());
            sendChat(p, "&6Action bar coordinates enabled!");
        }

        return false;
    }
}
