package me.kubister11.bytekod.tasks;

import me.kubister11.bytekod.managers.ChatManager;
import me.kubister11.bytekod.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class EditorsInfoTask implements Runnable {
    @Override
    public void run() {
        new HashMap<>(ChatManager.getEditors()).forEach((uuid, object) -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendTitle(TextUtil.fix("&2&lEYDTOR"), TextUtil.fix("&aWpisz wartość na chacie."), 20, 60, 20);
            } else {
                ChatManager.getEditors().remove(uuid);
            }
        });
    }
}
