package me.kubister11.bytekod.storage.files;

import lombok.Getter;
import me.kubister11.bytekod.storage.api.ConfigFile;
import org.bukkit.plugin.Plugin;

public class Config extends ConfigFile {
    @Getter private static ConfigFile configFile;
    public Config(Plugin plugin, Class configClass, String fileName) {
        super(plugin, configClass, fileName);
        configFile = this;
    }

    public static String MESSAGES_COMMAND$CORRECT$USE = "&7Poprawne użycie: &c[CMD]";
    public static String MESSAGES_RECEIVED = "&aPomyślnie wykorzystano kod!";
    public static String MESSAGES_ALREADY$RECEIVED = "&cWykorzystałeś/aś już ten kod!";
}
