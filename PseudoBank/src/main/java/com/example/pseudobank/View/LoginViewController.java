package com.example.pseudobank.View;


import com.example.pseudobank.Controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private Label errorLabel;

    @FXML
    private void handleLogin() throws IOException {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean success = loginController.validateUserLogin(username, password);

        if (success) {
            loadMainView();
        } else {
            errorLabel.setText("Invalid username or password");
            errorLabel.setVisible(true);
        }

    }

    private void loadMainView() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank");
        stage.show();

    }

}
