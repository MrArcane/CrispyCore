package me.mrarcane.crispycore.completes;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeleteHomeComplete  implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        PlayerManager pm = new PlayerManager(p.getUniqueId().toString());
        Set<String> homes = pm.getConfigurationSection("Home data").getKeys(false);
        List<String> result = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("deletehome")) {
            List<String> name = new ArrayList<>();
            if (args.length == 1) {
                for (String home : homes) {
                    name.add(home);
                }
            }
            return name;
        }
        return result;
    }
}

