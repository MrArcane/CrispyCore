package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.commands.MessageCommand.pmMap;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;
import static me.mrarcane.crispycore.utils.ChatUtil.sendClickableChat;

public class ReplyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String msg = "";
            if (!pmMap.containsKey(p)) {
                sendChat(p, "&cNo messages to reply to!");
                return true;
            }
            if (args.length < 1) {
                sendChat(p, "&cUsage: /reply <message>");
                return true;
            }
            for (int i = 0; i < args.length; i++) {
                msg = msg + " " + args[i].trim();
            }
            Player t = pmMap.get(p);
            for (Player staff : Bukkit.getOnlinePlayers()) {
                PlayerManager pd = new PlayerManager(staff.getUniqueId().toString());
                if (staff.hasPermission("crispycore.socialspy") && pd.getBoolean("Player.Social spy")) {
                    sendChat(staff, String.format("&7[&cSS&7] &8%s &c-> &8%s:&7%s", p.getName(), t.getName(), msg));
                }
            }
            sendClickableChat(p, String.format("&c&l(!) &f&l[&e&lYou &d-> &e%s&f&l]&b%s", t.getName(), msg), "&aClick to reply", "/reply", ClickEvent.Action.SUGGEST_COMMAND);
            sendClickableChat(t, String.format("&c&l(!) &f&l[&e%s &d-> &e&lYou&f&l]&b%s", p.getName(), msg), "&aClick to reply", "/reply", ClickEvent.Action.SUGGEST_COMMAND);
            pmMap.remove(p);
            pmMap.put(t, p);
        }
        return false;
    }
}
