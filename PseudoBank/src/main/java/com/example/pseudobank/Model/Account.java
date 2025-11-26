package com.example.pseudobank.Model;

import com.example.pseudobank.Database.connectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private String accountType;
    private String accountNumber;
    private double balance;

    public Account(String accountType, String accountNumber, double balance) {

        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;

    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    //method to get the users accounts and thier types
    public static List<String> getUserAccounts() {

        String sql = "SELECT accountType FROM accounts WHERE UserID = ?";

        List<String> accountTypes = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Session.userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                accountTypes.add(rs.getString("accountType"));
            }



        } catch (SQLException e) {
            e.printStackTrace();

        }

        return accountTypes;

    }


}
