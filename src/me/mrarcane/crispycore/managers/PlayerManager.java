package me.mrarcane.crispycore.managers;

import me.mrarcane.crispycore.Main;
import me.mrarcane.crispycore.hooks.VaultHook;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

import static me.mrarcane.crispycore.utils.ChatUtil.*;
import static me.mrarcane.crispycore.utils.FileUtil.cfg;

/**
 * File generated by: MrArcane
 * 2/12/2018
 **/
public class PlayerManager extends YamlConfiguration {

    public static String getRank;
    private String player;


    public PlayerManager(String player) {
        this.player = player + (player.endsWith(".yml") ? "" : ".yml");
        createFile();
        if (Main.debug()) {
            log(String.format("Player file %s opened.", player));
        }
    }

    public static String timePlayer(long time) {
        long now = System.currentTimeMillis();
        long date = now - time;

        long seconds = date / 1000 % 60;
        long minutes = date / (60 * 1000) % 60;
        long hours = date / (60 * 60 * 1000) % 24;
        long days = date / (24 * 60 * 60 * 1000);
        return (days > 0 ? days + " days, " : "") + (hours > 0 ? hours + " hours, " : "") + (minutes > 0 ? minutes + " min, " : "") + seconds + " seconds";
        //String fulldate = days+"d, "+hours+"h, "+minutes+"m, "+seconds+"s";
        //return fulldate;
    }

    public static void getGroup(Player p) {
        String group = VaultHook.chat.getPrimaryGroup(p);
        if (Main.getSection("Groups").getConfigurationSection(group) == null) {
            log(String.format("Group %s is not in the Groups section in the config!", group));
            p.setPlayerListName(color(String.format("&m&8-%s-", p.getDisplayName())));
            return;
        }
        String listName = Main.getInstance().getConfig().getConfigurationSection("Groups." + group).getString("List name").replace("{player}", p.getDisplayName());
        p.setPlayerListName(color(listName));
    }

    public static void getMail(Player p) {
        PlayerManager pd = new PlayerManager(p.getUniqueId().toString());
        List<String> m = pd.getConfigurationSection("Player").getStringList("Mail");
        if (m.size() > 0) {
            sendClickableChat(p, "&6You have mail! &7&n/mail read", "&6Open mailbox", "/mail read", ClickEvent.Action.RUN_COMMAND);
        } else {
            sendChat(p, "&6You have no new mail.");
        }
    }
    public void loadData(Player p) {
        PlayerManager pm = new PlayerManager(p.getUniqueId().toString());
        //Check if data is null
        if (pm.getConfigurationSection("Player") == null) {
            pm.createSection("Player");
            ConfigurationSection pSection = pm.getConfigurationSection("Player");
            assert pSection != null;
            //Add new player data.
            pSection.set("Time", System.currentTimeMillis());
            pSection.set("Max homes", 1);
            pSection.set("Homes", 0);
            pSection.set("Teleport toggle", false);
            pSection.set("Suicides", 0);
            pSection.set("Muted", false);
            pm.createSection("Home data");
            pm.save();
            log("&cUser data doesn't exist, created data for &7" + p.getName());
            return;
        }
        //If this is not a new player than lets just update some data..
        ConfigurationSection pSection = pm.getConfigurationSection("Player");
        assert pSection != null;
        pSection.set("Time", System.currentTimeMillis()); //Update the player's time.
        pm.save();
    }

    private void createFile() {
        try {
            File file = new File(Main.getInstance().getDataFolder() + "/Players/", player);
            if (!file.exists()) {
                if (Main.getInstance().getResource(player) != null) {
                    Main.getInstance().saveResource(player, false);
                } else {
                    save(file);
                }
            } else {
                load(file);
                save(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            save(new File(Main.getInstance().getDataFolder() + "/Players/", player));
            if (Main.debug()) {
                log(String.format("Saved file %s", player));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

