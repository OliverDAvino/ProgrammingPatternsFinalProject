package com.example.pseudobank.View;

import com.example.pseudobank.Controller.MainController;
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

    private MainController mainController = new MainController();

    @FXML
    private ImageView menuBarLogo;

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

        menuBarLogo.setFitHeight(80);
        menuBarLogo.setFitWidth(80);

        Circle clip = new Circle(40,40, 40);

        menuBarLogo.setClip(clip);

    }


    @FXML
    public void setAccountName(String name) {
        accountName.setText(name);

        int hour = java.time.LocalTime.now().getHour();

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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AccountsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) myAccountsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("My Accounts");
        stage.show();

    }

    @FXML
    private void handleTransactions() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/TransactionsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

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
