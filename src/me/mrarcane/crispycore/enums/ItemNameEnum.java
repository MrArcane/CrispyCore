package me.mrarcane.crispycore.enums;

import org.apache.commons.lang.WordUtils;

import java.util.HashMap;
import java.util.Map;

public enum ItemNameEnum {

    //Pickaxes
    WOODEN_PICKAXE("Wooden Pickaxe"),
    STONE_PICKAXE("Stone Pickaxe"),
    IRON_PICKAXE("Iron Pickaxe"),
    GOLDEN_PICKAXE("Golden Pickaxe"),
    DIAMOND_PICKAXE("Diamond Pickaxe"),
    //Shovels
    WOODEN_SHOVEL("Wooden Shovel"),
    STONE_SHOVEL("Stone Shovel"),
    IRON_SHOVEL("Iron Shovel"),
    GOLDEN_SHOVEL("Golden Shovel"),
    DIAMOND_SHOVEL("Diamond Shovel"),
    //Axes
    WOODEN_AXE("Wooden Axe"),
    STONE_AXE("Stone Axe"),
    IRON_AXE("Iron Axe"),
    GOLDEN_AXE("Golden Axe"),
    DIAMOND_AXE("Diamond Axe"),
    //Hoes
    WOODEN_HOE("Wooden Hoe"),
    STONE_HOE("Stone Hoe"),
    IRON_HOE("Iron Hoe"),
    GOLDEN_HOE("Golden Hoe"),
    DIAMOND_HOE("Diamond Hoe"),
    //Swords
    WOODEN_SWORD("Wooden Sword"),
    STONE_SWORD("Stone Sword"),
    IRON_SWORD("Iron Sword"),
    GOLDEN_SWORD("Golden Sword"),
    DIAMOND_SWORD("Diamond Sword"),
    //Misc Tools
    TRIDENT("Trident"),
    FISHING_ROD("Fishing Rod"),
    CARROT_ON_A_STICK("Carrot on a Stick"),
    SHEARS("Shears"),
    FLINT_AND_STEEL("Flint and Steel"),
    ELYTRA("Elytra"),
    BOW("Bow"),
    SHIELD("Shield"),
    //Boots
    LEATHER_BOOTS("Leather Boots"),
    IRON_BOOTS("Iron Boots"),
    GOLDEN_BOOTS("Golden Boots"),
    DIAMOND_BOOTS("Diamond Boots"),
    CHAINMAIL_BOOTS("Chainmail Boots"),
    //Legs
    LEATHER_LEGGINGS("Leather Leggings"),
    IRON_LEGGINGS("Iron Leggings"),
    GOLDEN_LEGGINGS("Golden Leggings"),
    DIAMOND_LEGGINGS("Diamond Leggings"),
    CHAINMAIL_LEGGINGS("Chainmail Leggings"),
    //Chest
    LEATHER_CHESTPLATE("Leather Chestplate"),
    IRON_CHESTPLATE("Iron Chestplate"),
    GOLDEN_CHESTPLATE("Golden Chestplate"),
    DIAMOND_CHESTPLATE("Diamond Chestplate"),
    CHAINMAIL_CHESTPLATE("Chainmail Chestplate"),
    //Helm
    LEATHER_HELMET("Leather Helmet"),
    IRON_HELMET("Iron Helmet"),
    GOLDEN_HELMET("Golden Helmet"),
    DIAMOND_HELMET("Diamond Helmet"),
    CHAINMAIL_HELMET("Chainmail Helmet");
    private static final Map<String, ItemNameEnum> lookup = new HashMap<String, ItemNameEnum>();
    private final String name;

    private ItemNameEnum(final String name) {
        this.name = name;
    }

    //Returns the Material name from the given block name
    public static String getMaterialName(String fromBlockName) {
        for (ItemNameEnum n : values()) {
            lookup.put(n.toString(), n);
        }
        return lookup.get(fromBlockName).name();
    }

    public String toString() {
        return name;
    }

    //Returns the item name with the first letter uppercased (Example: pressure plate -> Pressure plate)
    public String firstUpperCased() {
        char first = Character.toUpperCase(name.charAt(0));
        return first + name.substring(1);
    }

    //Returns the item name with all the words with the first letter uppercased (Example: pressure plate -> Pressure Plate)
    public String firstAllUpperCased() {
        return WordUtils.capitalizeFully(name);
    }

    //Returns the item name with all the letters uppercased (Example: pressure plate -> PRESSURE PLATE)
    public String allUpperCased() {
        return name.toUpperCase();
    }
}