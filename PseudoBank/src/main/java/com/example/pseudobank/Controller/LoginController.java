package com.example.pseudobank.Controller;

import com.example.pseudobank.Model.Login;

//login controller class to use to connect the model to the view

public class LoginController {

    public LoginController() {

    }

    //method to check the username and password
    //sends the info form the login view to the login model to check

    public boolean validateUserLogin(String username, String password) {

       return Login.checkUserLogin(username,password);

    }

    //method to get the user's full name from the model class
    //and we use it to show the welcome message

    public String getFullName(String username) {
        return Login.getUserFullName(username);
    }

    //method to get the users id from database

    public int getUserID(String username) {
        return  Login.getUserID(username);
    }




}
