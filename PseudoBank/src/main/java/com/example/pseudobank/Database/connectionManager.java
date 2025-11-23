package com.example.pseudobank.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionManager {

    public static Connection getConnection() {
        String url = "jdbc:mysql://127.0.0.1/pseudobank";
        String user = "root";
        String password = "";

        try  {

            return DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
