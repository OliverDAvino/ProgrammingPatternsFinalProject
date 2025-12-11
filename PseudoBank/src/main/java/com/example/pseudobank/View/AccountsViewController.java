package com.example.pseudobank.View;

import com.example.pseudobank.Database.connectionManager;
import com.example.pseudobank.Model.Account;
import com.example.pseudobank.Model.Login;
import com.example.pseudobank.Model.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AccountsViewController {

    @FXML
    private ImageView menuBarLogo;

    @FXML
    private Label accountName;

    @FXML Label accountBalance;

    @FXML
    private Button homeButton;

    @FXML
    private TextField initialAmountTextField;
    @FXML
    private Button createAccountButton;

    @FXML
    private Label createAccountError;

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button signOutButton;

    @FXML
    private TableView<String> accountTypeTableView;

    @FXML
    private TableColumn<String, String> displayAccountsTableView;

    @FXML
    private TableView<Account> accountInfoTableView;

    @FXML
    private TableColumn<Account, String> accountTypeColumn;

    @FXML
    private TableColumn<Account, String> accountNumberColumn;

    @FXML
    private TableColumn<Account, Double> balanceColumn;

    private ObservableList<Account> accountList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        //add the choices that the user can add an account for
        accountTypeComboBox.getItems().addAll("Chequing", "Savings", "TFSA", "RRSP", "Students");

        //displays the accounts types in strings
        displayAccountsTableView.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue())
        );

        //organize the account field into the right table columns
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        //when user clicks on an account type we load the account info in the right table
        accountTypeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                loadAccountsByType(newValue);
            }
        });

        accountName.setText(Session.userName);


    }

    //load all the account types the user has in the left table
    public void loadAccountTypes(int userID){
        List<String> accountTypes = Account.getUserAccounts(userID);
        accountTypeTableView.setItems(FXCollections.observableArrayList(accountTypes));

    }

    //load account details for the selected type
    public void loadAccountsByType(String type) {
        int userID = Session.userId; // however you store logged-in user
        List<Account> accounts = Account.getUserAccountsByType(userID, type);
        accountInfoTableView.setItems(FXCollections.observableArrayList(accounts));
    }


    //go back to home page
    @FXML
    private void handleMainPage() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get the controller that belongs to the loaded FXML
        MainViewController controller = fxmlLoader.getController();

        //Send the usersname  to it
        controller.setAccountName(Session.userName);

        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank");
        stage.show();
    }


    @FXML
    private void handleTransactions() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Transactions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //load all the users account types on the transactios page
        TransactionViewController transactionViewController = fxmlLoader.getController();
        transactionViewController.loadAccountTypes(Session.userId);

        Stage stage = (Stage) transactionsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Transactions");
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

    //handles creating a new account when they click the button
    @FXML
    private void handleCreateAccount() {

        String selectedType = accountTypeComboBox.getValue();
        int userId = Session.userId;
        String balanceInput = initialAmountTextField.getText();

        //validate account type
        if (selectedType == null) {
            createAccountError.setText("Please choose an account type.");
            return;
        }

        //Validate balance
        if (balanceInput.isEmpty()) {
            createAccountError.setText("Please enter an initial balance.");
            return;
        }

        //checks if it is a number
        double balance;
        try {
            balance = Double.parseDouble(balanceInput);
            if (balance < 0) {
                createAccountError.setText("Balance cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            createAccountError.setText("Balance must be a valid number.");
            return;
        }

        // dont allow multiple  account types
        List<String> existingTypes = Account.getUserAccounts(userId);
        if (existingTypes.contains(selectedType)) {
            createAccountError.setText("This account type already exists.");
            return;
        }

        //Create the account in the database with starting balance
        boolean success = Account.createAccount(userId, selectedType, balance);

        if (!success) {
            createAccountError.setText("Error creating account.");
            return;
        }

        //reload the table to show new account
        loadAccountTypes(userId);
        loadAccountsByType(selectedType);
        createAccountError.setText("Account created successfully!");

        initialAmountTextField.clear();
    }
}
