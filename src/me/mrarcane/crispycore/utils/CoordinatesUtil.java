package me.mrarcane.crispycore.utils;

import me.mrarcane.crispycore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

import static me.mrarcane.crispycore.utils.ChatUtil.sendAction;

public class CoordinatesUtil {
    public static HashMap coordsMap = new HashMap<Player, Boolean>();
    private static String compass(Player p) {
        float y = p.getLocation().getYaw();
        if (y < 0.0f) {
            y += 360.0f;
        }
        y %= 360.0f;
        String dir = "";
        final int i = (int)((y + 8.0f) / 22.5);
        if (i == 4) {
            dir = "W";
        } else if (i == 5) {
            dir = "WNW";
        } else if (i == 6) {
            dir = "NW";
        } else if (i == 7) {
            dir = "NNW";
        } else if (i == 8) {
            dir = "N";
        } else if (i == 9) {
            dir = "NNE";
        } else if (i == 10) {
            dir = "NE";
        } else if (i == 11) {
            dir = "ENE";
        } else if (i == 12) {
            dir = "E";
        } else if (i == 13) {
            dir = "ESE";
        } else if (i == 14) {
            dir = "SE";
        } else if (i == 15) {
            dir = "SSE";
        } else if (i == 0) {
            dir = "S";
        } else if (i == 1) {
            dir = "SSW";
        } else if (i == 2) {
            dir = "SW";
        } else if (i == 3) {
            dir = "WSW";
        } else {
            dir = "S";
        }
        return dir;
    }
    public static BukkitTask task(Plugin plugin) {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerUtil pd = new PlayerUtil(p.getUniqueId().toString());
                    int x = p.getLocation().getBlockX();
                    int y = p.getLocation().getBlockY();
                    int z = p.getLocation().getBlockZ();
                    if (coordsMap.containsKey(p)) {
                        sendAction(p, String.format("&6X: &7%s &6Y: &7%s &6Z: &7%s &6D: &7%s", x, y, z, compass(p)));
                        if (!p.isOnline()) {
                            Main.coordinates.cancel();
                        }
                    }
                }
            }, 0L, 10);
        }
}
