package me.kubister11.bytekod.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.utils.SerializationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Code {
    private final String code;
    private final List<String> commands;
    private String receiveBroadcast;
    private final List<String> claimed;


    public void insert() {
        Connection connection = ByteKod.getSqLite().getConnection();
        try {
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO codes (code, commands, receiveBroadcast, claimed) VALUES (?, ?, ?, ?)");
                ps.setString(1, code);
                ps.setString(2, "");
                ps.setString(3, receiveBroadcast);
                ps.setString(4, "");
                ps.execute();
                ps.close();
            }
        } catch (SQLException sQLException) {
            System.out.println("[sqlite] insert code (" + code + ") error!");
        }
    }

    public void update() {
        Connection connection = ByteKod.getSqLite().getConnection();
        try {
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement("UPDATE codes SET commands=?,receiveBroadcast=?,claimed=? WHERE code=?");
                ps.setString(1, SerializationUtil.serializeList(commands));
                ps.setString(2, receiveBroadcast);
                ps.setString(3, SerializationUtil.serializeList(claimed));
                ps.setString(4, code);
                ps.execute();
                ps.close();
            }
        } catch (SQLException sQLException) {
            System.out.println("[sqlite] update code (" + code + ") error!");
        }
    }

    public void delete() {
        Connection connection = ByteKod.getSqLite().getConnection();
        try {
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM codes WHERE code=?");
                ps.setString(1, code);
                ps.execute();
                ps.close();
            }
        } catch (SQLException sQLException) {
            System.out.println("[sqlite] delete code (" + code + ") error!");
        }
    }
}
