package me.mrarcane.crispycore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import static me.mrarcane.crispycore.Main.getInstance;

public class PlayerTeleportEvent implements Listener {
    private void teleportParticles(final Player player) {
        new BukkitRunnable() {
            double t = 0; // t for Time
            double pi = Math.PI;

            public void run() {

                t += 4 * pi / ((double) 1 * 20);
                Location loc = player.getLocation();

                for (double phi = 0; phi <= 2 * pi; phi += pi / 2) {
                    double x = 0.3 * (4 * pi - t) * Math.cos(t + phi);
                    double y = 0.2 * t;
                    double z = 0.3 * (4 * pi - t) * Math.sin(t + phi);
                    loc.add(x, y, z);
                    Bukkit.getWorld(player.getWorld().getName()).spawnParticle(Particle.SPELL_WITCH, loc, 0, 0, -1, 5, 100);
                    loc.subtract(x, y, z);
                    if (t >= 4 * pi) {
                        this.cancel();
                        loc.add(x, y, z);
                        Bukkit.getWorld(player.getWorld().getName()).spawnParticle(Particle.SPELL_WITCH, loc, 0, 0, -1, 5, 100);

                        loc.subtract(x, y, z);
                    }
                }
            }
        }.runTaskTimer(getInstance(), 0, 1);
    }
   @EventHandler
    private void onTeleport(org.bukkit.event.player.PlayerTeleportEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.SPECTATOR && e.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.COMMAND) {
            teleportParticles(e.getPlayer());
        }
   }
}
