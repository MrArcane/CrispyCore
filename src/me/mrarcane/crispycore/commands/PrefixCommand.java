package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.mrarcane.crispycore.listeners.PlayerJoinListener.prefixMap;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class PrefixCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 2) {
            sendChat(sender, "&cUsage: /prefix <player> <prefix/clear>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            sendChat(sender, "&cCould not find &7" + args[0]);
            return true;
        }

        PlayerManager pm = new PlayerManager(target.getUniqueId().toString());
        if (args[1].equalsIgnoreCase("clear")) {
            pm.set("Player.Prefix", null);
            pm.save();
            sendChat(sender, String.format("&aPrefix for &7%s &ahas been cleared!", target.getName()));
            if (target.isOnline()) {
                prefixMap.remove(target);
            }
            return true;
        }
        if (args.length > 2) {
            String prefix = "";
            for (int i = 1; i < args.length; i++) {
                prefix = (prefix + " " + args[i]).trim();
            }
            pm.set("Player.Prefix", prefix);
            pm.save();
            sendChat(sender, String.format("&7%s's &aprefix has been set to: &7%s", target.getName(), prefix));
            if (target.isOnline()) {
                prefixMap.put(target, pm.getString("Player.Prefix"));
            }
            return true;
        }
        return false;
    }
}
