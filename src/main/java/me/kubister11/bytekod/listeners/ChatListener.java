package me.kubister11.bytekod.listeners;

import me.kubister11.bytekod.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener {

    public ChatListener(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setCancelled(ChatManager.processInput(e.getPlayer(), e.getMessage()));
    }
}
