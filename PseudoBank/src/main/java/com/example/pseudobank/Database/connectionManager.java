package com.example.pseudobank.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionManager {

    public static Connection getConnection() {
        String url = "jdbc:mysql://127.0.0.1/";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
