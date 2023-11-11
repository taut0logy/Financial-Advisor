package com.advancedprogramminglab.financial_advisor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupController {
    public AnchorPane signupanchor;
    public VBox sidebarvbox;
    public ImageView imgsignup;
    public TextField usernameField;
    public TextField firstNameField;
    public TextField lastNameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Button signupButton;
    public Label dontlogin;
    public TextField emailField;

    public void initialize() {
        signupButton.setOnAction(event -> {
            onSignupButtonClick(event);
        });
        dontlogin.setOnMouseClicked(event -> {
            onDontLoginClick(event);
        });
    }

    private void onDontLoginClick(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/login-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = (Stage) dontlogin .getScene().getWindow();
            stage1.close();
            Stage stage = new Stage();
            stage.setTitle("Financial Advisor - Log in");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("SignupController.onDontLoginClick(): " + e.getMessage());
        }
    }

    private void onSignupButtonClick(ActionEvent event) {
        String msg="SignupController.onSignupButtonClick(): ";
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        System.out.println(msg+username + " " + password);
        if(username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            //show popup
            System.out.println(msg+"Empty fields");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill all the fields.");
            alert.showAndWait();
        }
        else if(!checkEmail(email)){
            //show popup
            System.out.println(msg+"Invalid email");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid email");
            alert.setContentText("Please enter a valid email.");
            alert.showAndWait();
        }
        else if(!password.equals(confirmPassword)){
            //show popup
            System.out.println(msg+"Passwords don't match");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Passwords don't match");
            alert.setContentText("Please enter the same password in both fields.");
            alert.showAndWait();
        }
        else{
            //signup
            int id = DBConnector.newUser(username,password,firstName,lastName,email);
            if(id==-1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Username already exists");
                alert.setContentText("Please enter a different username.");
                alert.showAndWait();
                return;
            }
            else if(id==0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please try again.");
                alert.showAndWait();
                return;
            }
            Stage stage2 = (Stage) signupanchor.getScene().getWindow();
            stage2.close();
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/hello-view.fxml"));
                stage.setTitle("Welcome!");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.out.println("SignupController.onSignupButtonClick(): " + e.getMessage());
            }
        }
    }
    public boolean checkEmail(String email){
        String msg="SignupController.checkEmail(): ";
        if(!email.contains("@")){
            System.out.println(msg+"Invalid email");
            return false;
        }
        return true;
    }
}

