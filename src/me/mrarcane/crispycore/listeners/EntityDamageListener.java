package me.mrarcane.crispycore.listeners;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.commands.ConsentCommand;
import me.mrarcane.crispycore.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.mrarcane.crispycore.utils.ChatUtil.*;

/**
 * File generated by: MrArcane
 * 2/15/2019
 **/
public class EntityDamageListener implements Listener {

    @EventHandler
    private void pvpToggle(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player && e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            PlayerManager vpm = new PlayerManager(damaged.getUniqueId().toString());
            PlayerManager dpm = new PlayerManager(damager.getUniqueId().toString());
            boolean vConsent = vpm.getBoolean("Player.Pvp consent");
            boolean dConsent = dpm.getBoolean("Player.Pvp consent");
            if (!ConsentCommand.bypassConsentMap.containsKey(damager)) {
                if (!dConsent && vConsent) {
                    sendChat(damager, "&cYou must consent to PVP to be able to damage players. Type &e/consent");
                    e.setCancelled(true);
                    return;
                }
                if (!vConsent) {
                    sendChat(damager, "&cThis player doesn't consent to PVP!");
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    private void onDamage(EntityDamageByEntityEvent e) {
        ConfigurationSection cfg = Main.getInstance().getConfig();
        //Stick PlayerItemListener
        List<String> stickLore = new ArrayList<>();
        stickLore.add(color("&dTells the truth about the player"));
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta stickMeta = stick.getItemMeta();
        stickMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stickMeta.addEnchant(Enchantment.ARROW_DAMAGE, 30, true);
        stickMeta.setDisplayName(color("&eStick of Truth"));
        stickMeta.setLore(stickLore);
        stick.setItemMeta(stickMeta);
        //Trout PlayerItemListener
        ItemStack trout = new ItemStack(Material.COD);
        List<String> lore = new ArrayList<>();
        lore.add(color("&d&lKnockback XXX"));
        ItemMeta troutMeta = trout.getItemMeta();
        troutMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        troutMeta.addEnchant(Enchantment.KNOCKBACK, 30, true);
        troutMeta.setDisplayName(color("&eTrout of Destiny"));
        troutMeta.setLore(lore);
        trout.setItemMeta(troutMeta);
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                if (e.getEntity().getType() != EntityType.PLAYER && p.getInventory().getItemInMainHand().getItemMeta().equals(stickMeta)) {
                    sendChat(p, "&cYou must use fun items on players only!");
                    return;
                }
                if (e.getEntity().getType() != EntityType.PLAYER && p.getInventory().getItemInMainHand().getItemMeta().equals(troutMeta)) {
                    return;
                }
                if (p.getInventory().getItemInMainHand().getItemMeta().equals(stickMeta)) {
                    Random r = new Random();
                    List<String> msgs = cfg.getStringList("Fun items.Stick of truth");
                    Player t = (Player) e.getEntity();
                    t.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 30));
                    broadcast(msgs.get(r.nextInt(msgs.size())).replace("{player}", t.getDisplayName()));
                    return;
                }
                //Trout
                if (p.getInventory().getItemInMainHand().getItemMeta().equals(troutMeta)) {
                    Random r = new Random();
                    List<String> msgs = cfg.getStringList("Fun items.Trout of destiny");
                    Player t = (Player) e.getEntity();
                    broadcast(msgs.get(r.nextInt(msgs.size())).replace("{player}", t.getDisplayName()));
                }
            }
        }
    }
}
