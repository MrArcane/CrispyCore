package me.mrarcane.crispycore.listeners;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

import static me.mrarcane.crispycore.utils.ChatUtil.sendAction;

public class PlayerMoveListener implements Listener {

    private HashMap<Player, String> inClaim = new HashMap<>();

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), true, null);
        if (claim == null) {
            if (inClaim.containsKey(p)) {
                sendAction(p, String.format("&eLeaving &7%s's&e claim &7(&cWilderness&7)", inClaim.get(p)));
                inClaim.remove(p);
            }
            return;
        }
        if (!inClaim.containsKey(p)) {
            inClaim.put(p, claim.getOwnerName());
            sendAction(p, String.format("&eEntering &7%s's&e claim", claim.getOwnerName()));
        }
    }
}
