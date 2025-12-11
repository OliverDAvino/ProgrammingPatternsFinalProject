package com.example.pseudobank.View;

import com.example.pseudobank.Model.SignUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class SignUpViewController {


    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button returnButton;

    @FXML
    private Label errorLabel;


    @FXML
    public void initialize() {



    }


    @FXML
    private void handleCreateAccount() {

        String fullName = fullNameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String confirmPass = confirmPasswordTextField.getText().trim();

        //validate

        if (fullName.isEmpty() || username.isEmpty() ||
                password.isEmpty() || confirmPass.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirmPass)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        if (SignUp.usernameExists(username)) {
            errorLabel.setText("Username already taken.");
            return;
        }

        //create user
        boolean success = SignUp.createUser(fullName, username, password);

        if (!success) {
            errorLabel.setText("Error creating account.");
            return;
        }

        errorLabel.setText("Account created!");

        // go to Login Page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) createAccountButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @FXML
    private void handleReturn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank Login");
        stage.show();

    }





}
