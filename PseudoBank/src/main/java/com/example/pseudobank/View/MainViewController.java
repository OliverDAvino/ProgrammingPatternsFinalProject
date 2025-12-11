package com.example.pseudobank.View;


import com.example.pseudobank.Controller.MainController;
import com.example.pseudobank.Model.Session;
import com.example.pseudobank.Model.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;


public class MainViewController {

//    private MainController mainController = new MainController();

    @FXML
    private ImageView menuBarLogo;

    @FXML
    private Label financialTipLabel;

    @FXML
    private Label accountName;

    @FXML
    private Button myAccountsButton;

    @FXML
    private Button transactionsButton;

    @FXML
    private Button signOutButton;

    @FXML
    private Label menuWelcomeLabel;

    @FXML
    public void initialize() {
        //make logo circle
        menuBarLogo.setFitHeight(80);
        menuBarLogo.setFitWidth(80);

        //display a tip
        int index = (int)(Math.random() * Session.tips.length);
        String tip = Session.tips[index];
        financialTipLabel.setText(tip);

        //make logo circle
        Circle clip = new Circle(40,40, 40);

        menuBarLogo.setClip(clip);

    }


    @FXML
    public void setAccountName(String name) {
        accountName.setText(name);

        //get the current hour
        int hour = java.time.LocalTime.now().getHour();

        //change the welcom message depending on time
        if (hour < 12) {
            menuWelcomeLabel.setText("Good Morning, " + name);
        } else if (hour < 18) {
            menuWelcomeLabel.setText("Good Afternoon, " + name);
        } else {
            menuWelcomeLabel.setText("Good Evening, " + name);
        }

    }

    @FXML
    private void handleMyAccounts() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Accounts.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //get the accounts controller, and pass the all the users accounts that they have
        AccountsViewController accountsViewController = fxmlLoader.getController();

        accountsViewController.loadAccountTypes(Session.userId);

        Stage stage = (Stage) myAccountsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("My Accounts");
        stage.show();

    }

    @FXML
    private void handleTransactions() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Transactions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //again pass the users accounts to the transaction page
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



}
