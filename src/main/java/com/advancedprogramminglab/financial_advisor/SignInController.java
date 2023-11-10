package com.advancedprogramminglab.financial_advisor;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignInController {
    @FXML
    public JFXTextField username;
    @FXML
    public JFXPasswordField password;

    public void initialize() {
        username.setText("Username");
    }
    public void onLoginButtonClick(ActionEvent actionEvent) {

    }
}
