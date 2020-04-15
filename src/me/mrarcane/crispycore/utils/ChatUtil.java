package me.mrarcane.crispycore.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * File generated by: MrArcane
 * 11/7/2017
 **/
public class ChatUtil {

    public static String color(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }
    public static void sendAction(Player p, String text)
    {
        ChatMessageType b = ChatMessageType.GAME_INFO;
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + color(text) + "\"}"), b);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
    public static void sendChat(CommandSender p, String msg) {
        p.sendMessage(color(msg));
    }
    public static void broadcast(String msg) {
        Bukkit.getServer().broadcastMessage(color(msg));
    }
    public static void log(String msg) {
        Bukkit.getConsoleSender().sendMessage(color("[CrispyCore] " + msg));
    }
    public static void sendClickableChat(Player p, String message, String hoverText, String command) {
        TextComponent txtcomponent = new TextComponent(color(message));
        txtcomponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        txtcomponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hoverText)).create()));
        p.spigot().sendMessage(txtcomponent);
    }
}
