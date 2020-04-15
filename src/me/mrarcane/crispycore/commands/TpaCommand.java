package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.utils.PlayerUtil;
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

import static me.mrarcane.crispycore.utils.ChatUtil.*;

public class TpaCommand implements CommandExecutor {
    public static Map<Player, Player> tpmap = new HashMap();

    public boolean onCommand(CommandSender sender, Command cmd, String value, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerUtil pd = new PlayerUtil(p.getUniqueId().toString());
            if (args.length == 0) {
                sender.sendMessage(color("&7Usage: &c/tpa <player>"));
                return false;
            }
            if (args[0].equalsIgnoreCase("toggle")) {
                if (pd.getString("Player.Teleport toggle").equals("false")) {
                    pd.set("Player.Teleport toggle", true);
                    pd.save();
                    sendChat(p, "&aYou enabled tpa.");
                    return true;
                }
                pd.set("Player.Teleport toggle", false);
                pd.save();
                sendChat(p, "&cYou disabled tpa.");
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
            PlayerUtil td = new PlayerUtil(t.getUniqueId().toString());
            if (td.getString("Player.Teleport toggle").equals("false")) {
                sendChat(p, "&cYou can't teleport to this player.");
                return true;
            }
            TextComponent msg = new TextComponent(color(String.format("&7%s &ais requesting to teleport to you. ", p.getDisplayName())));
            TextComponent a = new TextComponent(color("&7[Accept] &aor &7"));
            TextComponent d = new TextComponent(color("&7[Deny]"));
            a.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
            a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to accept!").create()));
            d.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny"));
            d.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to deny!").create()));
            msg.addExtra(a);
            msg.addExtra(d);
            tpmap.put(t, p);
            sendChat(p, String.format("&aRequesting to teleport to &7%s", t.getName()));
            t.spigot().sendMessage(msg);
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