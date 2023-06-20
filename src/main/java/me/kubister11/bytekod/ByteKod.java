package me.kubister11.bytekod;

import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import me.kubister11.bytekod.managers.CodesManager;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.database.SQLite;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.TextUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ByteKod extends JavaPlugin {

    @Getter private static InventoryManager invManager;
    @Getter private static CodesManager codesManager;
    @Getter private static Plugin instance;
    @Getter private static SQLite sqLite;

    @Override
    public void onEnable() {
        long startLoad = System.currentTimeMillis();
        instance = this;

        new Config(this, Config.class, "config.yml");

        invManager = new InventoryManager(this);
        invManager.init();

        sqLite = new SQLite(null, "database");
        sqLite.openConnection();

        codesManager = new CodesManager();
        codesManager.load();

        TextUtil.sendToConsole("&ebyteKod loaded! &7(took: " + (System.currentTimeMillis() - startLoad) + "ms)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
