package me.kubister11.bytekod.managers;

import lombok.Getter;
import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.utils.SerializationUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CodesManager {
    @Getter private final List<Code> codes;

    public CodesManager() {
        this.codes = new ArrayList<>();
    }

    public void load() {
        try {
            ResultSet result = ByteKod.getSqLite().getResult("SELECT * FROM codes");
            while (result.next()) {
                codes.add(new Code(result.getString(1), SerializationUtil.deserializeList(result.getString(2)), result.getString(3), SerializationUtil.deserializeList(result.getString(4))));
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
