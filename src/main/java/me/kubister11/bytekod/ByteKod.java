package me.kubister11.bytekod;

import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import me.kubister11.bytekod.commands.CodeAdminCommand;
import me.kubister11.bytekod.commands.CodeCommand;
import me.kubister11.bytekod.listeners.ChatListener;
import me.kubister11.bytekod.managers.CodesManager;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.database.SQLite;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.tasks.EditorsInfoTask;
import me.kubister11.bytekod.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

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
        sqLite.createTable();

        codesManager = new CodesManager();
        codesManager.load();


        new ChatListener(this);
        new CodeCommand("kod", List.of("code"), "/kod [kod]", "bytekod.command.kod", true);
        new CodeAdminCommand("adminkod", List.of("admincode"), "/adminkod [kod]", "bytekod.command.adminkod", true);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new EditorsInfoTask(), 20L, 20L);

        TextUtil.sendToConsole("&ebyteKod loaded! &7(took: " + (System.currentTimeMillis() - startLoad) + "ms)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
