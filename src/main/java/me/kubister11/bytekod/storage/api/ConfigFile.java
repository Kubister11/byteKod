package me.kubister11.bytekod.storage.api;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Getter
public class ConfigFile {
    private final Class configClass;
    private File cfgFile;
    private FileConfiguration cfg;

    public ConfigFile(Plugin plugin, Class configClass, String fileName) {
        this.configClass = configClass;

        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            throw new RuntimeException("Wystąpił błąd podczas tworzenia folderu pluginu!");
        } else {
            File cfgFile = new File(dataFolder, fileName);
            if (!cfgFile.exists()) {
                try {
                    Files.copy(plugin.getClass().getClassLoader().getResourceAsStream(fileName), Paths.get(cfgFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException var3) {
                    throw new RuntimeException("Wystąpił błąd podczas tworzenia configu!", var3);
                }
            }


            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

            this.cfgFile = cfgFile;
            this.cfg = cfg;
            load();
        }
    }

    public void load() {
        try {
            Field[] fields = configClass.getFields();
            for (Field field : fields) {
                if (isConfigField(field)) {
                    String path = getPath(field);
                    if (cfg.isSet(path)) {
                        field.set(null, cfg.get(path));
                    } else {
                        cfg.set(path, field.get(configClass));
                    }
                }
            }
            cfg.save(cfgFile);
        } catch (Exception e) {
            throw new RuntimeException("Wystąpił problem podczas ładowania configu!", e);
        }
    }

    public void save() {
        try {
            Field[] fields = configClass.getFields();
            for (Field field : fields) {
                if (isConfigField(field)) {
                    String path = getPath(field);
                    cfg.set(path, field.get(null));
                }
            }
            cfg.save(cfgFile);
        } catch (Exception e) {
            throw new RuntimeException("Wystąpił problem podczas zapisywania configu!", e);
        }
    }

    public void reload() {
        this.cfg = YamlConfiguration.loadConfiguration(cfgFile);
        load();
        save();
    }

    private boolean isConfigField(Field field) {
        return Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && !Modifier.isTransient(field.getModifiers());
    }
    private String getPath(Field field) {
        return field.getName().toLowerCase().replace("$", "-").replace("_", ".");
    }
}
