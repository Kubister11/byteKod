package me.kubister11.bytekod.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {

    public static ItemStack addGlow(ItemStack itemStack) {
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        final ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public static void renameItem(Inventory inv, int slot, String name) {
        ItemStack is = inv.getItem(slot);
        assert is != null;
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.setDisplayName(TextUtil.fix(name));
        is.setItemMeta(meta);
        inv.setItem(slot, is);
    }


    public static ItemStack createIs (Material material, String displayName, List<String> lore, boolean glow) {
        ItemStack is = new ItemStack(material);
        ItemMeta itemMeta = is.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(TextUtil.fix(displayName));
        itemMeta.setLore(TextUtil.fix(lore));
        is.setItemMeta(itemMeta);

        return glow ? addGlow(is) : is;
    }

    public static ItemStack createIs (Material material, String displayName, boolean glow) {
        ItemStack is = new ItemStack(material);
        ItemMeta itemMeta = is.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(TextUtil.fix(displayName));
        is.setItemMeta(itemMeta);

        return glow ? addGlow(is) : is;
    }

    public static ItemStack createFilter(Material mat, String name) {
        ItemStack filter = new ItemStack(mat, 1);
        ItemMeta meta = filter.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        filter.setItemMeta(meta);
        return filter;
    }

    public static void filterGui(Inventory inv, Material material, String displayName) {
        ItemStack filter = createFilter(material, displayName);
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) {
                inv.setItem(i, filter);
            }

        }
    }




}
