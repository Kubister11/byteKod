package me.kubister11.bytekod.utils;

import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Utils {
    public static void runAsync (Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,runnable);
    }

    public static void runSync (Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static void runLatter(Plugin plugin, long delay, Runnable runnable) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,runnable, delay);
    }

    public static Integer isInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long isLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static SlotPos calcSpace(int slot) {
        int row = slot / 9;
        int column = slot - (row * 9);
        return new SlotPos(row, column);
    }
}
