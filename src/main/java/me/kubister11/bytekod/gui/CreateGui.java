package me.kubister11.bytekod.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.ItemUtils;
import me.kubister11.bytekod.utils.TextUtil;
import me.kubister11.bytekod.utils.Utils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
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
        commands.add(TextUtil.fix("&7Nick gracza - &f[P]"));
        commands.add(" ");
        int cmdId = 0;
        for (String command : code.getCommands()) {
            commands.add("&7ID: " + cmdId + "   &f" + command);
            cmdId++;
        }
        commands.add(" ");
        contents.set(1, 1, ClickableItem.of(ItemUtils.createIs(Material.GREEN_BANNER, "&aDodaj komendę", commands, false), false, inventoryClickEvent -> {
            new AnvilGUI.Builder()
                    .text("say [P] test")
                    .title("Podaj komendę")
                    .plugin(ByteKod.getInstance())
                    .itemLeft(new ItemStack(Material.PAPER))
                    .onClick((slot, stateSnapshot) -> {
                        if (slot != AnvilGUI.Slot.OUTPUT) {
                            return Collections.emptyList();
                        }

                        code.getCommands().add(stateSnapshot.getText());
                        Utils.runAsync(ByteKod.getInstance(), code::update);
                        return List.of(AnvilGUI.ResponseAction.run(() -> open(player, code)));
                    }).open(player);
        }));

        contents.set(1, 3, ClickableItem.of(ItemUtils.createIs(Material.RED_BANNER, "&cUsuń komendę", commands, false), false, inventoryClickEvent -> {
            new AnvilGUI.Builder()
                    .title("Podaj ID komendy")
                    .text("id")
                    .plugin(ByteKod.getInstance())
                    .itemLeft(new ItemStack(Material.PAPER))
                    .onClick((slot, stateSnapshot) -> {
                        if (slot != AnvilGUI.Slot.OUTPUT) {
                            return Collections.emptyList();
                        }

                        Integer integer = Utils.isInteger(stateSnapshot.getText());
                        if (integer == null) return List.of(AnvilGUI.ResponseAction.replaceInputText("Taka komenda nie istnieje!"));

                        code.getCommands().remove((int) integer);
                        Utils.runAsync(ByteKod.getInstance(), code::update);
                        return List.of(AnvilGUI.ResponseAction.run(() -> open(player, code)));
                    }).open(player);
        }));

        contents.set(1, 5, ClickableItem.of(ItemUtils.createIs(Material.BLUE_WOOL, "&9Ustaw broadcast", new ArrayList<>(List.of("&7Wpisz &fOFF &7aby wyłączyć!", " ", code.getReceiveBroadcast())), false), false, inventoryClickEvent -> {
            new AnvilGUI.Builder()
                    .text(code.getReceiveBroadcast())
                    .title("Wpisz tekst")
                    .plugin(ByteKod.getInstance())
                    .itemLeft(new ItemStack(Material.PAPER))
                    .onClick((slot, stateSnapshot) -> {
                        if (slot != AnvilGUI.Slot.OUTPUT) {
                            return Collections.emptyList();
                        }

                        code.setReceiveBroadcast(stateSnapshot.getText());
                        Utils.runAsync(ByteKod.getInstance(), code::update);
                        return List.of(AnvilGUI.ResponseAction.run(() -> open(player, code)));
                    }).open(player);
        }));

        contents.set(1, 7, ClickableItem.of(ItemUtils.createIs(Material.PAPER, "&7Użycia kodu: &f" + code.getClaimed().size(), new ArrayList<>(List.of(" ", "&7Kliknij &flewym, &7aby zresetować konkretnego gracza.", "&7Kliknij &fprawym, &7aby zresetować wszystkie użycia.")), false), false, inventoryClickEvent -> {
            if (inventoryClickEvent.getClick() == ClickType.LEFT) {
                new AnvilGUI.Builder()
                        .title("Wpisz nick gracza")
                        .text("nick")
                        .plugin(ByteKod.getInstance())
                        .itemLeft(new ItemStack(Material.PAPER))
                        .onClick((slot, stateSnapshot) -> {
                            if (slot != AnvilGUI.Slot.OUTPUT) {
                                return Collections.emptyList();
                            }

                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(stateSnapshot.getText());
                            code.getClaimed().remove(offlinePlayer.getUniqueId().toString());
                            Utils.runAsync(ByteKod.getInstance(), code::update);
                            return List.of(AnvilGUI.ResponseAction.run(() -> open(player, code)));
                        }).open(player);
            } else if (inventoryClickEvent.getClick() == ClickType.RIGHT) {
                ConfirmGui.open(player, "Resetowanie użyć kodu", () -> {
                    code.getClaimed().clear();
                    Utils.runAsync(ByteKod.getInstance(), code::update);
                    open(player, code);
                });
            }
        }));


        contents.set(2, 8, ClickableItem.of(ItemUtils.createIs(Material.BARRIER, "&cUsuń", false), false, inventoryClickEvent -> {
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
                .size(3, 9)
                .title(ChatColor.DARK_GRAY + "Kod: " + code.getCode())
                .build().open(p);
    }
}
