package com.example.pseudobank.Controller;

import com.example.pseudobank.Model.Login;

public class LoginController {

    public LoginController() {

    }

    public boolean validateUserLogin(String username, String password) {

       return Login.checkUserLogin(username,password);

    }

    public String getFullName(String username) {
        return Login.getUserFullName(username);
    }



}
