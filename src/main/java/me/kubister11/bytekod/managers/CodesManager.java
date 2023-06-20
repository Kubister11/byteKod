package me.kubister11.bytekod.managers;

import lombok.Getter;
import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.objects.Code;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CodesManager {
    @Getter private final List<Code> codes;

    public CodesManager() {
        this.codes = new ArrayList<>();
    }

    public void load() {
        try {
            ResultSet result = ByteKod.getSqLite().getResult("SELECT * FROM codes");

            while (result.next()) {
                codes.add(new Code(result.getString(1), new ArrayList<>(), result.getString(3), new ArrayList<>()));
            }

            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Code getByName(String name) {
        for (Code code : codes) {
            if (code.getCode().equals(name)) return code;
        }
        return null;
    }

}
