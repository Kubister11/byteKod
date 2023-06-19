package me.kubister11.bytekod.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    private static final String pluginPrefix = "&8[&ebyteKod&8]";
    public static void sendMessage (CommandSender sender, String text) { sender.sendMessage(fix(text)); }
    public static void sendToConsole (String text) { Bukkit.getLogger().info(fix(pluginPrefix + " " + text)); }

    public static String fix(String text) {
        return fixGradient(ChatColor.translateAlternateColorCodes('&', text).replaceAll(">>", "»").replaceAll("<<", "«"));
    }


    public static List<String> fix(List<String> strings) {
        for(int i = 0; i < strings.size(); ++i) {
            strings.set(i, fix(strings.get(i)));
        }

        return strings;
    }
    public static String fixGradient(String string) {
        Pattern pattern = Pattern.compile("&(#[a-fA-F0-9]{6})");
        for (Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)) {
            String color = string.substring(matcher.start() + 1, matcher.end());
            string = string.replace("&" + color, net.md_5.bungee.api.ChatColor.of(color) + "");
        }
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}
