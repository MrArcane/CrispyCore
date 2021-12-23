package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mrarcane.crispycore.utils.ChatUtil.log;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class SocialSpyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerManager pd = new PlayerManager(p.getUniqueId().toString());
            boolean socialSpy = pd.getBoolean("Player.Social spy");
            if (socialSpy) {
                pd.set("Player.Social spy", false);
                pd.save();
                sendChat(p, "&eSocial spy disabled!");
                return true;
            }
            pd.set("Player.Social spy", true);
            pd.save();
            sendChat(p, "&eSocial spy enabled!");
            return true;
        }
        log("You must be a player to do this!");
        return false;
    }
}
