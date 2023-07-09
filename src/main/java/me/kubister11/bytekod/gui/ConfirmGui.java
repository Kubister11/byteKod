package me.kubister11.bytekod.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.kubister11.bytekod.utils.ItemUtils;
import me.kubister11.bytekod.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConfirmGui implements InventoryProvider {

    private final Runnable runnable;

    public ConfirmGui(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack filter = ItemUtils.createFilter(Material.GRAY_STAINED_GLASS_PANE, " ");
        contents.fill(ClickableItem.empty(filter, false));

        contents.set(1, 3, ClickableItem.of(ItemUtils.createIs(Material.LIME_DYE, "&a✔", true), false, inventoryClickEvent -> {
            runnable.run();
        }));

        contents.set(1, 5, ClickableItem.of(ItemUtils.createIs(Material.RED_DYE, "&c✖", true), false, inventoryClickEvent -> {
            player.closeInventory();
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static void open(Player p, String text, Runnable runnable) {
        SmartInventory.builder()
                .provider(new ConfirmGui(runnable))
                .size(3, 9)
                .title(TextUtil.fix("&7Na pewno? &8(&f" + text + "&8)"))
                .build().open(p);
    }
}
