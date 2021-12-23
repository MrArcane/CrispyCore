package me.mrarcane.crispycore.completes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamemodeComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> result = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        Player p = (Player) sender;
        if (args.length == 1) {
            if (p.hasPermission("crispycore.gamemode.set"))
                commands.add("set");
            if (p.hasPermission("crispycore.gamemode.survival"))
                commands.add("survival");
            if (p.hasPermission("crispycore.gamemode.creative"))
                commands.add("creative");
            if (p.hasPermission("crispycore.gamemode.adventure"))
                commands.add("adventure");
            if (p.hasPermission("crispycore.gamemode.spectator"))
                commands.add("spectator");
            StringUtil.copyPartialMatches(args[0], commands, result);
            Collections.sort(result);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            if (p.hasPermission("crispycore.gamemode.set")) {
                for (Player op : Bukkit.getOnlinePlayers()) {
                    commands.add(op.getName());
                }
                StringUtil.copyPartialMatches(args[1], commands, result);
                Collections.sort(result);
            }
        }
            if (args.length == 3) {
                if (p.hasPermission("crispycore.gamemode.set")) {
                    commands.add("survival");
                    commands.add("creative");
                    commands.add("adventure");
                    commands.add("spectator");
                    StringUtil.copyPartialMatches(args[2], commands, result);
                    Collections.sort(result);
                }
            }
            return result;
        }
}
