package com.example.pseudobank.View;

import com.example.pseudobank.Model.Account;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

public class AccountsViewController {

    @FXML
    private ImageView menuBarLogo;

    @FXML
    private Label accountName;

    @FXML
    private Button homeButton;

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
    private TableColumn<Account, Integer> accountNumberColumn;

    @FXML
    private TableColumn<Account, Double> balanceColumn;

    private ObservableList<Account> accountList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        displayAccountsTableView.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue())
        );


        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

    }

    public void loadAccountTypes(int userID){
        List<String> accountTypes = Account.getUserAccounts(userID);
        accountTypeTableView.setItems(FXCollections.observableArrayList(accountTypes));


    }

    public void loadAccounts(int userID) {

//        List<Account> accounts = Account.getUserAccounts(userID);
//
//        accountTableView.setItems(FXCollections.observableArrayList(accounts));

    }

    @FXML
    private void handleMainPage() throws IOException {



    }

    @FXML
    private void handleTransactions() throws IOException {



    }

    @FXML
    private void handleSignOut() throws IOException {



    }
}
