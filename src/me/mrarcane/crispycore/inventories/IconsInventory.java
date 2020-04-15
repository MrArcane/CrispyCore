package me.mrarcane.crispycore.inventories;

import me.mrarcane.crispycore.utils.FileUtil;
import me.mrarcane.crispycore.utils.InventoryUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IconsInventory {
    public IconsInventory(Player p) {
        InventoryUtil inv = new InventoryUtil(p, "Select your home icon", 54);
        FileUtil file = new FileUtil(null, "Icons");
        String lore = file.getString("Options.Icon lore");
        for (String item : file.getStringList("Icons")) {
            item = item.replace("_", " ");
            char first = Character.toUpperCase(item.charAt(0));
            String result = first + item.substring(1);
            inv.createItem(Material.matchMaterial(item), result, lore);
        }
        inv.open(p);
    }
}
