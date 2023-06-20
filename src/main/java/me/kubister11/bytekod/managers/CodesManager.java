package me.kubister11.bytekod.managers;

import lombok.Getter;
import me.kubister11.bytekod.objects.Code;

import java.util.ArrayList;
import java.util.List;

public class CodesManager {
    @Getter private final List<Code> codes;

    public CodesManager() {
        this.codes = new ArrayList<>();
    }

    private void load() {

    }

    private Code getByName(String name) {
        for (Code code : codes) {
            if (code.getCode().equals(name)) return code;
        }
        return null;
    }

}
