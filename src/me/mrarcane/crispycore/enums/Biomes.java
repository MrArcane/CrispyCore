package me.mrarcane.crispycore.enums;

import org.apache.commons.lang.WordUtils;

import java.util.HashMap;
import java.util.Map;

public enum Biomes {
    BEACH("Beach"),
    BIRCH_FOREST("Birch forest"),
    BIRCH_FOREST_HILLS("Birch forest hills"),
    BIRCH_FOREST_HILLS_MOUNTAINS("Birch forest hills mountains"),
    BIRCH_FOREST_MOUNTAINS("Birch forest mountains"),
    COLD_BEACH("Cold beach"),
    COLD_TAIGA("Cold taiga"),
    COLD_TAIGA_HILLS("Cold taiga hills"),
    COLD_TAIGA_MOUNTAINS("Cold taiga mountains"),
    DEEP_OCEAN("Deep ocean"),
    DESERT("Desert"),
    DESERT_HILLS("Desert hills"),
    DESERT_MOUNTAINS("Desert mountains"),
    EXTREME_HILLS("Extreme hills"),
    EXTREME_HILLS_MOUNTAINS("Extreme hills mountains"),
    EXTREME_HILLS_PLUS("Extreme hills plus"),
    EXTREME_HILLS_PLUS_MOUNTAINS("Extreme hills plus mountains"),
    FLOWER_FOREST("Flower forest"),
    FOREST("Forest"),
    FOREST_HILLS("Forest hills"),
    FROZEN_OCEAN("Frozen ocean"),
    FROZEN_RIVER("Frozen river"),
    HELL("Hell"),
    ICE_MOUNTAINS("Ice mountains"),
    ICE_PLAINS("Ice plains"),
    ICE_PLAINS_SPIKES("Ice plains spikes"),
    JUNGLE("Jungle"),
    JUNGLE_EDGE("Jungle edge"),
    JUNGLE_EDGE_MOUNTAINS("Jungle edge mountains"),
    JUNGLE_HILLS("Jungle hills"),
    JUNGLE_MOUNTAINS("Jungle mountains"),
    MEGA_SPRUCE_TAIGA("Mega spruce taiga"),
    MEGA_SPRUCE_TAIGA_HILLS("Mega spruce taiga hills"),
    MEGA_TAIGA("Mega taiga"),
    MEGA_TAIGA_HILLS("Mega taiga hills"),
    MESA("Mesa"),
    MESA_BRYCE("Mesa bryce"),
    MESA_PLATEAU("Mesa plateau"),
    MESA_PLATEAU_FOREST("Mesa plateau"),
    MESA_PLATEAU_FOREST_MOUNTAINS("Mesa plateau mountains"),
    MESA_PLATEAU_MOUNTAINS("Mesa plateau mountains"),
    MUSHROOM_ISLAND("Mushroom island"),
    MUSHROOM_SHORE("Mushroom shore"),
    OCEAN("Ocean"),
    PLAINS("Plains"),
    RIVER("River"),
    ROOFED_FOREST("Roofed forest"),
    ROOFED_FOREST_MOUNTAINS("Roofed forest mountains"),
    SAVANNA("Savanna"),
    SAVANNA_MOUNTAINS("Savanna mountains"),
    SAVANNA_PLATEAU("Savanna plat"),
    SAVANNA_PLATEAU_MOUNTAINS("Savanna plateau mountains"),
    SKY("Sky"),
    SMALL_MOUNTAINS("Small mountains"),
    STONE_BEACH("Stone beach"),
    SUNFLOWER_PLAINS("Sunflower plains"),
    SWAMPLAND("Swampland"),
    SWAMPLAND_MOUNTAINS("Swampland mountains"),
    TAIGA("Taiga"),
    TAIGA_HILLS("Taiga hills"),
    TAIGA_MOUNTAINS("Taiga mountains");
    private final String name;

    private Biomes(final String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    private static final Map<String, Biomes> lookup = new HashMap<String, Biomes>();
    //Returns the Material name from the given block name
    public static String getName(String fromName){
        for(Biomes n : values()){
            lookup.put(n.toString(), n);
        }
        String result = lookup.get(fromName).name();
        return result;
    }

    //Returns the item name with the first letter uppercased (Example: pressure plate -> Pressure plate)
    public String firstUpperCased(){
        char first = Character.toUpperCase(name.charAt(0));
        return first + name.substring(1);
    }

    //Returns the item name with all the words with the first letter uppercased (Example: pressure plate -> Pressure Plate)
    public String firstAllUpperCased(){
        return WordUtils.capitalizeFully(name);
    }

    //Returns the item name with all the letters uppercased (Example: pressure plate -> PRESSURE PLATE)
    public String allUpperCased(){
        return name.toUpperCase();
    }
}