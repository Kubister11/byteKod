package me.kubister11.bytekod.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.managers.ChatManager;
import me.kubister11.bytekod.objects.ChatInputRequest;
import me.kubister11.bytekod.objects.ChatInputType;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.ItemUtils;
import me.kubister11.bytekod.utils.TextUtil;
import me.kubister11.bytekod.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateGui implements InventoryProvider {
    private final Code code;

    public CreateGui(Code code) {
        this.code = code;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(ClickableItem.empty(ItemUtils.createFilter(Material.GRAY_STAINED_GLASS_PANE, " "), false));

        List<String> commands = new ArrayList<>();
        commands.add(TextUtil.fix("&7Nick gracza - &f[PLAYER]"));
        commands.add(" ");
        int cmdId = 0;
        for (String command : code.getCommands()) {
            commands.add("&7ID: " + cmdId + "   &f" + command);
            cmdId++;
        }
        commands.add(" ");
        contents.set(0, 0, ClickableItem.of(ItemUtils.createIs(Material.GREEN_BANNER, "&aDodaj komendę", commands, false), false, inventoryClickEvent -> {
            ChatManager.getEditors().put(player.getUniqueId(), new ChatInputRequest(ChatInputType.ADD_COMMAND, code));
            player.closeInventory();
        }));

        contents.set(0, 2, ClickableItem.of(ItemUtils.createIs(Material.RED_BANNER, "&cUsuń komendę", commands, false), false, inventoryClickEvent -> {
            ChatManager.getEditors().put(player.getUniqueId(), new ChatInputRequest(ChatInputType.REMOVE_COMMAND, code));
            player.closeInventory();
        }));

        contents.set(0, 4, ClickableItem.of(ItemUtils.createIs(Material.BLUE_WOOL, "&9Ustaw broadcast", false), false, inventoryClickEvent -> {
            ChatManager.getEditors().put(player.getUniqueId(), new ChatInputRequest(ChatInputType.EDIT_BROADCAST, code));
            player.closeInventory();
        }));

        contents.set(0, 6, ClickableItem.empty(ItemUtils.createIs(Material.PAPER, "&7Użycia kodu: &f" + code.getClaimed().size(), false), false));


        contents.set(0, 8, ClickableItem.of(ItemUtils.createIs(Material.BARRIER, "&cUsuń", false), false, inventoryClickEvent -> {
            ConfirmGui.open(player,"Usunięcie kodu", () -> {
                player.closeInventory();
                Utils.runAsync(ByteKod.getInstance(), code::delete);
                ByteKod.getCodesManager().getCodes().remove(code);
                TextUtil.sendMessage(player, Config.MESSAGES_DELETED);
            });
        }));

    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }

    public static void open(Player p, Code code) {
        SmartInventory.builder()
                .provider(new CreateGui(code))
                .size(1, 9)
                .title(ChatColor.DARK_GRAY + code.getCode())
                .build().open(p);
    }
}
