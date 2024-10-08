package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

import static me.mrarcane.crispycore.utils.ChatUtil.*;
import static me.mrarcane.crispycore.utils.ChatUtil.color;

/**
 * File generated by: MrArcane
 * 5/28/2018
 **/
public class DeleteHomeCommand implements CommandExecutor {

    public static HashMap<Player, String> delHomeMap = new HashMap<>();
    private int homeAmount = 0;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            PlayerManager pm = new PlayerManager(p.getUniqueId().toString());
            Set<String> homes = pm.getConfigurationSection("Home data").getKeys(false);
            homeAmount = pm.getInt("Player.Homes");

            if (homes.size() == 0) {
                sendChat(p, "&cNo homes found!");
                return true;
            }
            if (args.length == 0) {
                //Check if the player only has 1 home
                if (homes.size() == 1) {
                    for (String home : homes) {
                        delHomeMap.put(p, home);
                    }
                    p.spigot().sendMessage(message(p));
                    return true;
                }
                sendChat(p, "&cUsage: /deletehome <home>");
                return true;
            }
            if (args.length == 1) {
                if (pm.contains("Home data." + args[0].toLowerCase())) {
                    String home = args[0].toLowerCase();
                    delHomeMap.put(p, home);
                    p.spigot().sendMessage(message(p));
                    return true;
                }
                if (!pm.contains("Home data." + args[0].toLowerCase()) && (!args[0].contains("confirm") && !args[0].contains("deny"))) {
                    sendChat(p, "&cNo such home found!");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("confirm")) {
                if (!delHomeMap.containsKey(p)) {
                    sendChat(p, "&cPlease run the full command!");
                    return true;
                }
                homeAmount--;
                pm.set("Player.Homes", homeAmount);
                pm.set("Home data." + delHomeMap.get(p), null);
                pm.save();
                sendChat(p, String.format("&7%s &adeleted successfully!", delHomeMap.get(p)));
                delHomeMap.remove(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("deny")) {
                if (!delHomeMap.containsKey(p)) {
                    sendChat(p, "&cPlease run the full command!");
                    return true;
                }
                sendChat(p, "&aAction cancelled.");
                delHomeMap.remove(p);
                return true;
            }
        }
        return false;
    }
    private BaseComponent message(Player p) {
        //Create the message
        TextComponent request = new TextComponent(color(String.format("&aAre you sure you want to delete home: &7%s&a? ", delHomeMap.get(p))));
        TextComponent confirm = new TextComponent(color("&7[&a✔&7]"));
        TextComponent space = new TextComponent(" ");
        TextComponent deny = new TextComponent(color("&7[&4✖&7]"));
        //Events
        confirm.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deletehome confirm"));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deletehome deny"));
        confirm.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to delete the home!").create()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to deny deleting the home!").create()));
        //Add to the message
        request.addExtra(confirm);
        request.addExtra(space);
        request.addExtra(deny);
        return request;
    }
}
