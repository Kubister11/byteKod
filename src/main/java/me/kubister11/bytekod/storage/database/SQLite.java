package me.kubister11.bytekod.storage.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kubister11.bytekod.ByteKod;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;


@AllArgsConstructor
@Getter
public class SQLite {

    private Connection connection;
    private String database;

    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS `codes` (" +
            "  `code` text NOT NULL," +
            "  `commands` text NOT NULL," +
            "  `receiveBroadcast` text NOT NULL," +
            "  `claimed` text NOT NULL" +
            ")";

    public void openConnection() {
        File dataFolder = new File(ByteKod.getInstance().getDataFolder(), database+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                ByteKod.getInstance().getLogger().log(Level.SEVERE, "File write error: "+database+".db");
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
        } catch (SQLException ex) {
            ByteKod.getInstance().getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            ByteKod.getInstance().getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
    }

    public void createTable() {
        Connection connection = getConnection();
        try {
            if (connection != null) {
                Statement statement = getConnection().createStatement();
                statement.execute(createTableSQL);
            }
        } catch (SQLException sQLException) {
            System.out.println("[sqlite] table create error!");
            sQLException.printStackTrace();
        }
    }

    public ResultSet getResult(String ps) {
        ResultSet resultSet = null;
        Connection connection = getConnection();
        try {
            if (connection != null) {
                Statement statement = getConnection().createStatement();
                resultSet = statement.executeQuery(ps);
            }
        } catch (SQLException sQLException) {
            System.out.println("[sqlite] wrong when want get result: '" + ps + "'!");
        }
        return resultSet;
    }
    public boolean isConnected() {
        return (getConnection() != null);
    }

    public Connection getConnection() {
        validateConnection();
        return connection;
    }

    private void validateConnection() {
        try {
            if (this.connection == null) {
                System.out.println("[sqlite] aborted. Connecting again");
                reConnect();
            }
            if (!this.connection.isValid(4)) {
                System.out.println("[sqlite] timeout.");
                reConnect();
            }
            if (this.connection.isClosed()) {
                System.out.println("[sqlite] closed. Connecting again");
                reConnect();
            }
        } catch (Exception exception) {
        }
    }

    private void reConnect() {
        System.out.println("[sqlite] connection again");
        openConnection();
    }
}
