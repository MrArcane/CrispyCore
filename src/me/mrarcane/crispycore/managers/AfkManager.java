package me.mrarcane.crispycore.managers;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

import static me.mrarcane.crispycore.Main.debug;
import static me.mrarcane.crispycore.managers.PlayerBedManager.*;
import static me.mrarcane.crispycore.utils.ChatUtil.*;

/**
 * File generated by: MrArcane
 * 3/15/2019
 **/
public class AfkManager implements Listener {
    public static HashMap<Player, String> afk = new HashMap<>();
    public static HashMap<Player, Location> afkLoc = new HashMap<>();
    public static HashMap<Player, Integer> afkMinutes = new HashMap<>();

    public static BukkitTask afkTask(Plugin plugin) {
        //Start timer
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
                if (debug()) {
                    log("Afk task running..");
                }
                //Add the players that are online into the location hashmap
                if (!afkLoc.containsKey(p)) {
                    afkLoc.put(p, p.getLocation());
                    if (debug()) {
                        log("Afk location " + p.getDisplayName() + " created!");
                    }
                }
                //Add minute to player
                if (!afk.containsKey(p)) {
                    addMinute(p);
                }
                //Set player to afk mode
                if (getTime(p) == 5) {
                    if (afkLoc.get(p).equals(p.getLocation())) {
                        setAfk(p);
                        afkLoc.remove(p);
                        afkMinutes.remove(p);
                        if (debug()) {
                            log(p.getDisplayName() + " timer has stopped!");
                        }
                    } else {
                        afkLoc.put(p, p.getLocation());
                        resetTime(p);
                        if (debug()) {
                            log("location incorrect for " + p.getDisplayName() + " time reset!");
                        }
                    }
                }
            }
        }, 1200, 1200);
    }

    public static int getTime(Player player) {
        if (afkMinutes.containsKey(player)) {
            return afkMinutes.get(player);
        } else {
            return 0;
        }
    }

    private static void addMinute(Player player) {
        if (afkMinutes.containsKey(player)) {
            if (debug()) {
                log(player.getDisplayName() + ": " + getTime(player));
            }
            afkMinutes.put(player, afkMinutes.get(player) + 1);
        }
        if (!afkMinutes.containsKey(player)) {
            if (debug()) {
                log("Time map: " + player.getDisplayName() + " created!");
            }
            afkMinutes.put(player, 0);
        }
    }

    public static void setAfk(Player p) {
        if (bed.contains(p)) {
            sendChat(p, "&cYou're not allowed to go AFK while in bed!");
            return;
        }
        if (!afk.containsKey(p)) {
            afk.put(p, "AFK");
            p.setSleepingIgnored(true);
            broadcast(String.format("&7%s &eis now AFK!", p.getDisplayName()));
            p.setPlayerListName(color(String.format("&4[AFK] %s", p.getDisplayName())));
            if (active.contains(p)) {
                active.remove(p);
                if (bed.size() >= 1) {
                    broadcast(bedSection.getString("Vote change").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())).replace("{reason}", "went AFK"));
                    skipNight();
                }
                if (debug()) {
                    ChatUtil.log(String.format("%s removed from the active list [Afk].", p.getDisplayName()));
                }
            }
        }
    }

    public static void delAfk(Player p) {
        if (afk.containsKey(p)) {
            afk.remove(p);
            afkLoc.remove(p);
            resetTime(p);
            p.setSleepingIgnored(false);
            PlayerManager.getGroup(p);
            broadcast(String.format("&7%s &eis no longer AFK!", p.getDisplayName()));
            if (!active.contains(p)) {
                active.add(p);
                if (bed.size() >= 1) {
                    broadcast(bedSection.getString("Vote change").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())).replace("{reason}", "no longer AFK"));
                    skipNight();
                }
                if (debug()) {
                    ChatUtil.log(String.format("%s added to the active list [Afk].", p.getDisplayName()));
                }
            }
        }
    }

    public static boolean isAfk(Player player) {
        return afk.containsKey(player);
    }

    private static void resetTime(Player p) {
        afkMinutes.put(p, 0);
        if (debug()) {
            log(p.getDisplayName() + "'s time reset!");
        }
    }

    @EventHandler
    private void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ() && e.getFrom().getBlockY() == e.getTo().getBlockY()) {
            delAfk(p);
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        resetTime(p);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (afk.containsKey(p)) {
            afk.remove(p);
            if (debug()) {
                log("Removed " + p.getDisplayName() + " from afk!");
            }
        }
        afkLoc.remove(p);
        afkMinutes.remove(p);
    }

    @EventHandler
    private void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        delAfk(p);
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        delAfk(p);
    }

    @EventHandler
    private void onShear(PlayerShearEntityEvent e) {
        Player p = e.getPlayer();
        delAfk(p);
    }

    @EventHandler
    private void onFish(PlayerFishEvent e) {
        Player p = e.getPlayer();
        delAfk(p);
    }

    @EventHandler
    private void onShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            delAfk(p);
        }
    }
}