package com.example.pseudobank.Model;

import com.example.pseudobank.Database.connectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Login in model class that checks if the username and the password are correct
//it gets the user's full name and id from the database

public class Login {

    //method to check if the users info are valid and match in the database

    public static boolean checkUserLogin(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //method to get the users fullname from the database so we can use it later in the main page
    //we use the username to find the right peron

    public static String getUserFullName(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();



            if (rs.next()) {
                //save the fullname in Session class for later
                Session.userName = rs.getString("fullName");
                return rs.getString("fullName");
            } else {
                return "";
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }


    }

    //method to get the users id form the database

    public static int getUserID(String username) {

        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return 0;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }


    }



}
