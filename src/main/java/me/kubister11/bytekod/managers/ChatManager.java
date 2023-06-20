package me.kubister11.bytekod.managers;

import lombok.Getter;
import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.gui.CreateGui;
import me.kubister11.bytekod.objects.ChatInputRequest;
import me.kubister11.bytekod.utils.Utils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatManager {
    @Getter private static final Map<UUID, ChatInputRequest> editors = new HashMap<>();

    public static boolean processInput(Player player, String input) {
        ChatInputRequest chatInputType = editors.get(player.getUniqueId());
        if (chatInputType == null) return false;

        switch (chatInputType.getChatInputType()) {
            case ADD_COMMAND -> chatInputType.getCode().getCommands().add(input);
            case REMOVE_COMMAND -> {
                try {
                    chatInputType.getCode().getCommands().remove(Integer.parseInt(input));
                } catch (Exception ignore) {
                }
            }
            case EDIT_BROADCAST -> chatInputType.getCode().setReceiveBroadcast(input);
        }

        Utils.runAsync(ByteKod.getInstance(), () -> chatInputType.getCode().update());
        editors.remove(player.getUniqueId());
        CreateGui.open(player, chatInputType.getCode());
        return true;
    }

}
