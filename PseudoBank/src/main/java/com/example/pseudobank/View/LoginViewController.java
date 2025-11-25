package com.example.pseudobank.View;


import com.example.pseudobank.Controller.LoginController;
import com.example.pseudobank.Model.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {

    private LoginController loginController = new LoginController();

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        loginButton.setDefaultButton(true);
    }


    private void loadMainView(String username) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        String name = loginController.getFullName(username);


        MainViewController mainViewController = fxmlLoader.getController();

        mainViewController.setAccountName(name);

        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank");
        stage.show();



    }



    @FXML
    private void handleLogin() throws IOException {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean success = loginController.validateUserLogin(username, password);

        if (success) {

            int id = loginController.getUserID(username);
            Session.userId = id;

            loadMainView(username);
        } else {
            errorLabel.setText("Invalid username or password");
            errorLabel.setVisible(true);
        }

    }



}
