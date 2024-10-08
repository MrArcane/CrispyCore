package me.mrarcane.crispycore.managers;


import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.mrarcane.crispycore.Main.debug;
import static me.mrarcane.crispycore.utils.ChatUtil.*;

/**
 * File generated by: MrArcane
 * 1/21/2018
 **/

public class PlayerBedManager implements Listener {
    public static ConfigurationSection bedSection = Main.getInstance().getConfig().getConfigurationSection("Night skip");
    public static List<Player> active = new ArrayList<>();
    public static List<Player> bed = new ArrayList<>();
    static boolean passed;
    private static World world = Bukkit.getWorld(bedSection.getString("World"));

    public static void resetBedData() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == world) {
                active.add(p);
            }
            if (debug()) {
                ChatUtil.log(String.format("%s added to the active list [Reset data].", p.getDisplayName()));
            }
        }
    }

    public static void skipNight() {
        double players = 0;
        players = active.size();
        players = players / 2;
        if (bed.size() >= players) {
            passed = true;
            if (debug()) {
                log("Checks have met! time switched to day! [Night skip]");
            }
            world.setStorm(false);
            world.setThundering(false);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time add " + (24000 - world.getTime()));
            List<String> msgs = bedSection.getStringList("Morning messages");
            Random r = new Random();
            broadcast(msgs.get(r.nextInt(msgs.size())));
        }
    }

    //Events
    @EventHandler
    private void onBedEnter(PlayerBedEnterEvent e) {
        Player p = e.getPlayer();
        if (!bed.contains(p) && e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            bed.add(p);
            if (debug()) {
                ChatUtil.log(String.format("%s added to the bed list.", p.getDisplayName()));
            }
            if (p.getWorld() == world) {
                broadcast(bedSection.getString("Bed enter").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())));
            }
            skipNight();
        }
    }

    @EventHandler
    private void onBedLeave(PlayerBedLeaveEvent e) {
        Player p = e.getPlayer();
        if (bed.contains(p)) {
            bed.remove(p);
            skipNight();
            if (!passed) {
                if (p.getWorld() == world) {
                    broadcast(bedSection.getString("Bed leave").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())));
                }
            }
            if (passed && bed.size() == 0) {
                passed = false;
            }
            if (debug()) {
                ChatUtil.log(String.format("%s removed from the bed list.", p.getDisplayName()));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld() == world) {
            active.add(p);
            if (bed.size() >= 1) {
                broadcast(bedSection.getString("Vote change").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())).replace("{reason}", "joined the game"));
                skipNight();
            }
        }
        if (debug()) {
            ChatUtil.log(String.format("%s added to the active list [Login].", p.getDisplayName()));
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (active.contains(p)) {
            active.remove(p);
            if (bed.size() >= 1) {
                if (p.getWorld() == world) {
                    broadcast(bedSection.getString("Vote change").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())).replace("{reason}", "quit the game"));
                }
                skipNight();
            }
            if (debug()) {
                ChatUtil.log(String.format("%s removed from the active list. [Quit]", p.getDisplayName()));
            }
        }
    }

    @EventHandler
    private void onPlayerTeleport(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (active.contains(p) && p.getLocation().getWorld() != world) {
            active.remove(p);
            if (bed.size() >= 1) {
                if (p.getWorld() == world) {
                    broadcast(bedSection.getString("Vote change").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())).replace("{reason}", "left world"));
                }
                skipNight();
            }
            if (debug()) {
                ChatUtil.log(String.format("%s removed from the active list. [World change]", p.getDisplayName()));
            }
        }
        if (!active.contains(p) && p.getLocation().getWorld() == world) {
            active.add(p);
            if (bed.size() >= 1) {
                if (p.getWorld() == world) {
                    broadcast(bedSection.getString("Vote change").replace("{player}", p.getDisplayName()).replace("{inBed}", String.valueOf(bed.size())).replace("{players}", String.valueOf(active.size())).replace("{reason}", "joined world"));
                }
                skipNight();
            }
            if (debug()) {
                ChatUtil.log(String.format("%s added to the active list. [World change]", p.getDisplayName()));
            }
        }
    }
}
