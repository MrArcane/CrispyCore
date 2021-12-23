package me.mrarcane.crispycore.completes;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HomeComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> result = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        Player p = (Player) sender;
        PlayerManager user = new PlayerManager(p.getUniqueId().toString());
        Set<String> homeSection = user.getConfigurationSection("Home data").getKeys(false);
        if (args.length == 1) {
            if (homeSection.size() == 0) {
                commands.add("You don't have any homes");
            }
            commands.addAll(homeSection);
        }
        StringUtil.copyPartialMatches(args[0], commands, result);
        Collections.sort(result);
        return result;
    }
}
