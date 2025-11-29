package com.example.pseudobank.View;

import com.example.pseudobank.Model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TransactionViewController {

    @FXML
    private Button homeButton;

    @FXML
    private Button accountsButton;

    @FXML
    private Button signOutButton;

    @FXML
    private Label accountBalance;

    @FXML
    private TableView<String> transactionTableView;

    @FXML
    private TableColumn<Transaction, String> dateColumn;

    @FXML
    private TableColumn<Transaction, String> transactionIDColumn;

    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;

    @FXML
    private TableColumn<Transaction, String> amountColumn;

}
