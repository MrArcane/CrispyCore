package me.mrarcane.crispycore.completes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class FunItemComplete implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> result = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("funitem")) {
            List<String> name = new ArrayList<>();
            if (args.length == 1) {
                name.add("trout");
                name.add("stick");
            }
            return name;
        }
        return result;
    }
}
