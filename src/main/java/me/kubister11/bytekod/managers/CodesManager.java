package me.kubister11.bytekod.managers;

import lombok.Getter;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.files.CodesStorage;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class CodesManager {
    @Getter private final List<Code> codes;

    public CodesManager() {
        this.codes = new ArrayList<>();
    }

    private void load() {
        FileConfiguration cfg = CodesStorage.getConfigFile().getCfg();
//        cfg.getKeys(false).forEach(CodesStorage);
    }
}
