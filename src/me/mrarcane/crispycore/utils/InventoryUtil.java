package me.mrarcane.crispycore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static me.mrarcane.crispycore.utils.ChatUtil.color;

public class InventoryUtil {

    private Inventory inventory;
    public InventoryUtil(Player player, String name, int size) {
        this.inventory = Bukkit.createInventory(player, size, color(name));
    }
    public void createItem(Material material, String name, String... lore) {
        ArrayList<String> loreList = new ArrayList<String>();
        for (String s : lore) {
            loreList.add(color(s));
        }
        ItemStack i = new ItemStack(material);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(color(name));
        im.setLore(loreList);
        i.setItemMeta(im);
        inventory.addItem(i);
    }
    public void open(Player p) {
        p.openInventory(inventory);
    }
}