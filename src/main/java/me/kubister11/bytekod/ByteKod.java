package me.kubister11.bytekod;

import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import me.kubister11.bytekod.storage.files.CodesStorage;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.TextUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class ByteKod extends JavaPlugin {

    @Getter private static InventoryManager invManager;

    @Override
    public void onEnable() {
        long startLoad = System.currentTimeMillis();

        new Config(this, Config.class, "config.yml");
        new CodesStorage(this, CodesStorage.class, "codes.yml");

        invManager = new InventoryManager(this);
        invManager.init();

        TextUtil.sendToConsole("&ebyteKod loaded! &7(took: " + (System.currentTimeMillis() - startLoad) + "ms)");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
