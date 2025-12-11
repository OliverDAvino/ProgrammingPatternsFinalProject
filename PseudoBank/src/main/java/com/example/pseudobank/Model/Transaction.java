package com.example.pseudobank.Model;

import com.example.pseudobank.Database.connectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private int transactionId;
    //private int accountId;
    private double amount;
    private String transactionType;
    private String date;

    public Transaction(int transactionId, double amount, String transactionType, String date) {

        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;


    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getDate() {
        return date;
    }

    public int getTransactionId() {
        return transactionId;
    }



    public static List<Transaction> getTransactionsByAccount(int accountId) {

        String sql = "SELECT transactionId, amount, transactionType, date FROM transactions WHERE accountId = ? ORDER BY date DESC";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("transactionId"),
                        rs.getDouble("amount"),
                        rs.getString("transactionType"),
                        rs.getString("date")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static boolean insertTransaction(int accountId, double amount, String type) {

        String insertTx =
                "INSERT INTO transactions (amount, transactionType, accountId) VALUES (?, ?, ?)";

        String updateBalance =
                "UPDATE accounts SET balance = balance + ? WHERE accountId = ?";

        try (Connection conn = connectionManager.getConnection()) {

            conn.setAutoCommit(false);

            //add new transaction
            PreparedStatement txStmt = conn.prepareStatement(insertTx);
            txStmt.setDouble(1, amount);
            txStmt.setString(2, type);
            txStmt.setInt(3, accountId);
            txStmt.executeUpdate();

            //Update balance
            PreparedStatement balStmt = conn.prepareStatement(updateBalance);
            balStmt.setDouble(1, amount);
            balStmt.setInt(2, accountId);
            balStmt.executeUpdate();

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }




}
