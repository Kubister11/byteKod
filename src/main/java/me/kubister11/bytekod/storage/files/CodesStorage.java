package me.kubister11.bytekod.storage.files;

import lombok.Getter;
import me.kubister11.bytekod.storage.api.ConfigFile;
import org.bukkit.plugin.Plugin;

public class CodesStorage extends ConfigFile {
    @Getter private static ConfigFile configFile;
    public CodesStorage(Plugin plugin, Class configClass, String fileName) {
        super(plugin, configClass, fileName);
        configFile = this;
    }
}
