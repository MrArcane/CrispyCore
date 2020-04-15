package me.mrarcane.crispycore.inventories;

import me.mrarcane.crispycore.utils.FileUtil;
import me.mrarcane.crispycore.utils.InventoryUtil;
import me.mrarcane.crispycore.utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

import static me.mrarcane.crispycore.utils.ChatUtil.log;

public class HomeInventory {
int lines = 0;

    public HomeInventory(Player p) {
        PlayerUtil pd = new PlayerUtil(p.getUniqueId().toString());
        Set<String> hdSection = pd.getConfigurationSection("Home data").getKeys(false);
        //Define the lores on the items
        String lore = "&eX: &7%s &eY: &7%s &eZ: &7%s";
        String lore1 = "&cLeft click to teleport" ;
        String lore2 = "&cRight click to change icon";
        FileUtil file = new FileUtil(null, "Icons");
        while(lines * 9 < hdSection.size()) {

            lines++; //while the lines times 9 is less than homes we add one more line to the inventory

        }
        if(lines < 7) {

            int slot = 0;

            for (int i = 0; i < hdSection.size(); i++) {
                InventoryUtil inv = new InventoryUtil(p, "Homes", lines * 9);
                for (String home : hdSection) {
                    ConfigurationSection homeData = pd.getConfigurationSection("Home data." + home);
                    int x = homeData.getInt("x");
                    int y = homeData.getInt("y");
                    int z = homeData.getInt("z");
                    Material defaultIcon = Material.matchMaterial(Objects.requireNonNull(file.getString("Options.Default icon")));
                    if (homeData.contains("Icon")) {
                        inv.createItem(Material.getMaterial(Objects.requireNonNull(homeData.getString("Icon"))), home, String.format(lore, x, y, z), lore1, lore2);
                    }
                    if (defaultIcon == null) {
                        log(String.format("Default icon: %s is not an item! please edit the Default icon in Icons.yml", file.getString("Options.Default icon")));
                    }
                    if (!homeData.contains("Icon")) {
                        inv.createItem(defaultIcon, home, String.format(lore, x, y, z), lore1, lore2);
                    }
                }
                inv.open(p);
            }
        } else {
            lines = 6;
        }
    }
}
