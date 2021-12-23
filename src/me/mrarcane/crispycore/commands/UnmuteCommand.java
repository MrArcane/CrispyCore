package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class UnmuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sendChat(sender, "&cUsage: &7/unmute <player>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            sendChat(sender, String.format("&7%s &cdoesn't exist!", args[0]));
            return true;
        }
        PlayerManager pm = new PlayerManager(target.getUniqueId().toString());
        if (!pm.getBoolean("Player.Muted")) {
            sendChat(sender, String.format("&7%s &cis not muted!", target.getName()));
            return true;
        }
        pm.set("Player.Muted", false);
        pm.save();
        sendChat(sender, String.format("&7%s &ahas been un-muted successfully!", target.getName()));
        return false;
    }
}
