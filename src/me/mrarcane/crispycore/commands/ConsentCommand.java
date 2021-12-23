package me.mrarcane.crispycore.commands;

import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.mrarcane.crispycore.utils.ChatUtil.log;
import static me.mrarcane.crispycore.utils.ChatUtil.sendChat;

public class ConsentCommand implements CommandExecutor {

    public static HashMap<Player, String> consentMap = new HashMap<>();
    public static HashMap<Player, String> bypassConsentMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerManager pm = new PlayerManager(p.getUniqueId().toString());
            boolean consent = pm.getBoolean("Player.Pvp consent");
            if (pm.get("Player.Pvp consent") == null) {
                pm.set("Player.Pvp consent", false);
                pm.save();
            }
            if (args.length == 0) {
                if (consentMap.containsKey(p)) {
                    sendChat(p, "&cYou're already in this process...");
                    return true;
                }
                if (!consent && !consentMap.containsKey(p)) {
                    sendChat(p, "&4&lWARNING!");
                    sendChat(p, "&7You are about to allow yourself to be damaged by other players.");
                    sendChat(p, "&7If you agree to this type &4/consent confirm");
                    sendChat(p, "&7If you don't agree to this type &4/consent deny");
                    consentMap.put(p, "true");
                    return true;
                }
                pm.set("Player.Pvp consent", false);
                pm.save();
                sendChat(p, "&aPvP consent has been disabled!");
                return true;
            }
            if (args[0].equalsIgnoreCase("bypass") && p.hasPermission("crispycore.consent.bypass")) {
                if (!bypassConsentMap.containsKey(p)) {
                    bypassConsentMap.put(p, "bypassing");
                    sendChat(p, "&cYou are now bypassing players consent to be damaged!");
                    return true;
                }
                bypassConsentMap.remove(p);
                sendChat(p, "&cYou can no longer harm other players.");
                return true;
            }
            if (args[0].equalsIgnoreCase("deny")) {
                if (consentMap.containsKey(p)) {
                    consentMap.remove(p);
                    sendChat(p, "&7You denied to consenting to PvP.. &aYou're safe.");
                    return true;
                }
                sendChat(p, "&cPlease type /consent before you use this command!");
                return true;
            }
            if (args[0].equalsIgnoreCase("confirm")) {
                if (consent) {
                    sendChat(p, "&7You've already consented to PvP..");
                    return true;
                }
                if (consentMap.containsKey(p)) {
                    consentMap.remove(p);
                    pm.set("Player.Pvp consent", true);
                    pm.save();
                    sendChat(p, "&aYou are now able to be damaged by other players!");
                    return true;
                }
                sendChat(p, "&cPlease type /consent before you use this command!");
                return true;
            }
            return true;
        }
        log("You must be a player to do this");
        return false;
    }
}
