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
    public static List<String> getUserAccounts(int userID) {

        String sql = "SELECT DISTINCT accountType FROM accounts WHERE userID = ?";

        List<String> accountTypes = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                accountTypes.add(rs.getString("accountType"));
            }



        } catch (SQLException e) {
            e.printStackTrace();

        }

        return accountTypes;

    }

    public static List<Account> getUserAccountsByType(int userID, String type) {

        String sql = "SELECT accountType, accountNumber, balance FROM accounts WHERE userID = ? AND accountType = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            pstmt.setString(2, type.trim());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                accounts.add(new Account(
                        rs.getString("accountType"),
                        rs.getString("accountNumber"),
                        rs.getDouble("balance")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static int getAccountIdByType(int userID, String type) {
        String sql = "SELECT accountId FROM accounts WHERE userID = ? AND accountType = ? LIMIT 1";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setString(2, type);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("accountId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // NOT FOUND
    }


    public static double getBalanceByAccountId(int accountId) {

        String sql = "SELECT balance FROM accounts WHERE accountId = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    public static boolean accountNumberExists(String accountNumber) {
        String sql = "SELECT COUNT(*) AS count FROM accounts WHERE accountNumber = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createAccount(int userId, String accountType, double balance) {

        String accountNumber;

        // generate UNIQUE account number
        do {
            accountNumber = String.valueOf((int)(Math.random() * 90000000 + 10000000));
        } while (accountNumberExists(accountNumber));

        String sql = "INSERT INTO accounts (userID, accountType, accountNumber, balance) VALUES (?, ?, ?, ?)";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, accountType);
            stmt.setString(3, accountNumber);
            stmt.setDouble(4, balance);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean userHasAccountType(int userId, String accountType) {

        String sql = "SELECT COUNT(*) AS count FROM accounts WHERE userID = ? AND accountType = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, accountType);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0; // true if user already has this type
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
