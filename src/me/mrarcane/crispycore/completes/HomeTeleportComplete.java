package me.mrarcane.crispycore.completes;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HomeTeleportComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> result = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (args.length == 1) {
                commands.add(p.getName());
                StringUtil.copyPartialMatches(args[0], commands, result);
                Collections.sort(result);
            }
        }
            if (args.length == 2) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                PlayerManager user = new PlayerManager(p.getUniqueId().toString());
                Set<String> homeSection = user.getConfigurationSection("Home data").getKeys(false);
                if (homeSection.size() == 0) {
                    commands.add(String.format("%s doesn't have homes", args[0]));
                }
                for (String home : homeSection) {
                    commands.add(home);
                }
                StringUtil.copyPartialMatches(args[1], commands, result);
                Collections.sort(result);
            }
        return result;
    }
}
