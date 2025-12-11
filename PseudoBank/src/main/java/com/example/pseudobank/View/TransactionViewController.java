package com.example.pseudobank.View;

import com.example.pseudobank.Model.Account;
import com.example.pseudobank.Model.Session;
import com.example.pseudobank.Model.Transaction;
import com.example.pseudobank.TransactionLogger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TransactionViewController {

    @FXML
    private Button homeButton;

    @FXML
    private Label accountName;

    @FXML
    private Button accountsButton;

    @FXML
    private Button signOutButton;

    @FXML
    private Label accountBalance;

//    @FXML
//    private Button depositButton;
//
//    @FXML
//    private Button withdrawButton;

    @FXML
    private TextField amountTextField;


    @FXML
    private TableView<Transaction> transactionTableView;

    @FXML
    private TableColumn<Transaction, String> dateColumn;

    @FXML
    private TableColumn<Transaction, String> transactionIDColumn;

    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;

    @FXML
    private TableColumn<Transaction, String> amountColumn;

    @FXML
    private TableColumn<String, String> displayAccountsTableView;

    @FXML
    private TableView<String> accountTypeTableView;

    @FXML
    private Label errorLabel;






    @FXML
    public void initialize() {

        // Display strings inside left column
        displayAccountsTableView.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue())
        );

        // Transaction table columns
        transactionIDColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Listen to account selection
        accountTypeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadTransactionsByType(newVal);
            }
        });
        accountName.setText(Session.userName);

    }


    public void loadAccountTypes(int userID){
        List<String> accountTypes = Account.getUserAccounts(userID);
        accountTypeTableView.setItems(FXCollections.observableArrayList(accountTypes));

    }

    public void loadTransactionsByType(String type) {

        int userID = Session.userId;

        // Find the accountId for this user & type
        int accountId = Account.getAccountIdByType(userID, type);
        if (accountId == -1) {
            System.out.println("No account found for type: " + type);
            return;
        }

        // Load transactions for this account
        List<Transaction> txList = Transaction.getTransactionsByAccount(accountId);

        // Display them
        transactionTableView.setItems(FXCollections.observableArrayList(txList));

        // load the balance
        double balance = Account.getBalanceByAccountId(accountId);
        accountBalance.setText(String.format("Balance: $%.2f", balance));
    }


    @FXML
    private void handleMainPage() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get the controller that belongs to the loaded FXML
        MainViewController controller = fxmlLoader.getController();

        //Send data to it
        controller.setAccountName(Session.userName);

        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank");
        stage.show();
    }


    @FXML
    private void handleMyAccounts() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Accounts.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        AccountsViewController accountsViewController = fxmlLoader.getController();

        accountsViewController.loadAccountTypes(Session.userId);

        Stage stage = (Stage) accountsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("My Accounts");
        stage.show();

    }



    @FXML
    private void handleSignOut() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) signOutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank Login");
        stage.show();

    }


    @FXML
    private void handlesDeposit() {
        performTransaction("Deposit");
    }


    @FXML
    private void handlesWithdraw() {

        performTransaction("Withdraw");

    }


    private void performTransaction(String type) {

        String amountText = amountTextField.getText();
        if (amountText.isEmpty()) {
            System.out.println("Amount required");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                System.out.println("Amount must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        // selected account type
        String selectedType = accountTypeTableView.getSelectionModel().getSelectedItem();
        if (selectedType == null) {
            System.out.println("No account selected");
            return;
        }

        int userID = Session.userId;
        int accountId = Account.getAccountIdByType(userID, selectedType);

        if (accountId == -1) {
            System.out.println("Account not found");
            return;
        }

        // If withdraw convert amount to negative
        if (type.equals("Withdraw")) {
            double currentBalance = Account.getBalanceByAccountId(accountId);
            //checking if amount is greater than balance
            if (amount > currentBalance) {
                errorLabel.setText("Insufficient Balance");
                return;
            }


            amount = -amount;
        }

        errorLabel.setText("");
        boolean success = Transaction.insertTransaction(accountId, amount, type);


        if (success) {
            System.out.println("Transaction added!");

            TransactionLogger.log(
                    "UserID " + userID +
                            " | AccountID " + accountId +
                            " | " + type +
                            " | Amount: $" + Math.abs(amount)
            );

            // Update balance
            refreshBalanceLabel(accountId);

            // Reload transactions
            loadTransactionsByType(selectedType);

            amountTextField.clear();
        } else {
            System.out.println("Error adding transaction");
        }
    }



    private void refreshBalanceLabel(int accountId) {
        double balance = Account.getBalanceByAccountId(accountId);
        accountBalance.setText("Balance: $" + String.format("%.2f", balance));
    }





}
