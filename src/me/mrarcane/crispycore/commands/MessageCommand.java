package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.mrarcane.crispycore.utils.ChatUtil.*;

public class MessageCommand implements CommandExecutor {

    public static HashMap<Player, Player> pmMap = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String msg = "";
            if (args.length < 2) {
                sendChat(p, "&cUsage: /message <player> <message>");
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                sendChat(p, String.format("&cCouldn't find %s", args[0]));
                return true;
            }
            for (int i = 1; i < args.length; i++) {
                msg = msg + " " + args[i].trim();
            }
            for (Player staff : Bukkit.getOnlinePlayers()) {
                PlayerManager pd = new PlayerManager(staff.getUniqueId().toString());
                if (staff.hasPermission("crispycore.socialspy") && pd.getBoolean("Player.Social spy")) {
                    sendChat(staff, String.format("&7[&cSS&7] &8%s &c-> &8%s:&7%s", p.getName(), t.getName(), msg));
                }
            }
            pmMap.put(t, p);
            sendClickableChat(p, String.format("&c&l(!) &f&l[&e&lYou &d-> &e%s&f&l]&b%s", t.getName(), msg), "&aClick to reply", "/reply " + t.getName(), ClickEvent.Action.SUGGEST_COMMAND);
            sendClickableChat(t, String.format("&c&l(!) &f&l[&e%s &d-> &e&lYou&f&l]&b%s", p.getName(), msg), "&aClick to reply", "/reply " + p.getName(), ClickEvent.Action.SUGGEST_COMMAND);
            return true;
        }
        log("You must be a player to use this command");
        return false;
    }
}
