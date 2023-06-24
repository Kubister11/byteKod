package me.kubister11.bytekod.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializationUtil {
    public static String serializeList(List<String> list) {
        if (list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        list.forEach(o -> sb.append(o).append(","));
        String serialData = sb.toString();
        return serialData.substring(0, serialData.length() - 1);
    }

    public static List<String> deserializeList(String data) {
        String[] split = data.split(",");
        return new ArrayList<>(Arrays.asList(split));
    }
}
