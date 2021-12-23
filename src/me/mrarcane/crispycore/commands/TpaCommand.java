package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.managers.PlayerManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static me.mrarcane.crispycore.utils.ChatUtil.color;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class TpaCommand implements CommandExecutor {
    public static Map<Player, Player> tpmap = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String value, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerManager pd = new PlayerManager(p.getUniqueId().toString());
            if (args.length == 0) {
                sendChat(p, "&cUsage: /tpa player");
                return false;
            }
            if (args[0].equalsIgnoreCase("toggle")) {
                if (pd.getString("Player.Teleport toggle").equals("false")) {
                    pd.set("Player.Teleport toggle", true);
                    pd.save();
                    sendChat(p, "&eYou enabled tpa.");
                    return true;
                }
                pd.set("Player.Teleport toggle", false);
                pd.save();
                sendChat(p, "&eYou disabled tpa.");
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (p == t) {
                sendChat(p, "&cYou can't teleport to yourself!");
                return true;
            }
            if (t == null) {
                sendChat(p, String.format("&c%s is offline", args[0]));
                return true;
            }
            PlayerManager td = new PlayerManager(t.getUniqueId().toString());
            if (td.getString("Player.Teleport toggle").equals("false")) {
                sendChat(p, "&cYou can't teleport to this player.");
                return true;
            }
            //Create the message
            TextComponent request = new TextComponent(color(String.format("&7%s &ehas requested to teleport to you. ", p.getName())));
            TextComponent accept = new TextComponent(color("&7[&a✔&7]"));
            TextComponent space = new TextComponent(" ");
            TextComponent deny = new TextComponent(color("&7[&4✖&7]"));
            TextComponent expire = new TextComponent(color("\n&7The request expires in 30 seconds."));
            //Events
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny"));
            accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to accept the teleport!").create()));
            deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to deny the teleport!").create()));
            //Add to the message
            request.addExtra(accept);
            request.addExtra(space);
            request.addExtra(deny);
            request.addExtra(expire);
            tpmap.put(t, p);
            sendChat(p, String.format("&aRequesting to teleport to &7%s", t.getName()));
            t.spigot().sendMessage(request);
            sendChat(t, "&aYou can disable this functionality by typing &7/tpa toggle");
            //Timer to cancel request
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                tpmap.remove(t);
                if (tpmap.containsKey(p)) {
                    sendChat(p, "&cTeleport request canceled!");
                }
            }, 600);
        }
        return false;
    }
}