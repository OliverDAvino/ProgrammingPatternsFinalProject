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

//loginviewcontroller uses the methods from login controller to check the username and pass to let user in

public class LoginViewController {

    //create login controller to use its methods
    private LoginController loginController = new LoginController();

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button signUpButton;
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


    //method to load the main page after the user logs in
    //and sends the users name so we can display it in the welcome message

    private void loadMainView(String username) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //get teh full name
        String name = loginController.getFullName(username);

        //pass the name to the mainview controller
        MainViewController mainViewController = fxmlLoader.getController();

        mainViewController.setAccountName(name);

        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pseudo Bank");
        stage.show();



    }


    //method to handle the sign up page

    @FXML
    private void handleSignUp() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/SignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();


    }

    //method to handle the login
    //checks if the username and passwoed are correct
    //if wrong error message shows

    @FXML
    private void handleLogin() throws IOException {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean success = loginController.validateUserLogin(username, password);

        if (success) {
            //saves the users id to session userid to use later
            int id = loginController.getUserID(username);
            Session.userId = id;

            loadMainView(username);
        } else {
            errorLabel.setText("Invalid username or password");
            errorLabel.setVisible(true);
        }

    }



}
