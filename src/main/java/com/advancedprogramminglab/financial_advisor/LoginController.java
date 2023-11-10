package com.advancedprogramminglab.financial_advisor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public Label welcomeTextLogin;

    @FXML
    public TextField loginEmail;
    @FXML
    public PasswordField loginPass;
    @FXML
    public Button btnLogin;
    @FXML
    public VBox loginRoot;


    public void initialize() {

        //Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        welcomeTextLogin.setText("Welcome to your very own Financial Advisor!");
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onLoginButtonClick(event);
            }
        });


    }


    protected void onLoginButtonClick(ActionEvent event) {
        //login
        String msg="LoginController.onLoginButtonClick(): ";
        String username = loginEmail.getText();
        String password = loginPass.getText();

        System.out.println(msg+username + " " + password);
        int id = DBConnector.getSigninID(username,password);
        System.out.println(msg+"id: "+id);
        if(id==-1)
        {
            //show popup
            System.out.println(msg+"Invalid username or password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username");
            alert.setContentText("Please enter a valid username.\nNB: usernames are case sensitive.");
            alert.showAndWait();
        }
        else if(id==-2){
            System.out.println(msg+"Invalid password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid password");
            alert.setContentText("Please enter a valid password");
            alert.showAndWait();
        }
        else {
            int login=DBConnector.logIn(id);
            if(login==0){
                System.out.println(msg+"Login failed");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login failed");
                alert.setContentText("Please try again");
                alert.showAndWait();
                return;
            }
            User user = LaunchApplication.getCurrentUser();
            System.out.println(msg+"Login successful");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Login successful");
            alert.setContentText("Welcome "+user.getFirstName()+" "+user.getLastName());
            alert.showAndWait();
            //load hello page
            try {
                LaunchApplication.changeScene("hello-view.fxml", event);
            } catch (IOException e) {
                String msg2="LoginController.onLoginButtonClick(): "+e.getMessage();
                System.out.println(msg2);
            }
            //close login page
            //Stage stage = (Stage) loginRoot.getScene().getWindow();
            //stage.close();
        }
    }


}

//    User use = new User();
//    int c=DBConnector.getUser(id,use);
//            if(c==0){
//                    System.out.println(msg+"User not found");
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error");
//                    alert.setHeaderText("User not found");
//                    alert.setContentText("Please enter a valid username.\nNB: usernames are case sensitive.");
//                    alert.showAndWait();
//                    return;
//                    }
//                    LaunchApplication.setCurrentUser(use);
//                    User user = LaunchApplication.getCurrentUser();
//                    System.out.println(msg+"Login successful");
//                    System.out.println(msg+user.getFirstName()+" "+user.getLastName());
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Success");
//                    alert.setHeaderText("Login successful");
//                    alert.setContentText("Welcome "+user.getFirstName()+" "+user.getLastName());
//                    alert.showAndWait();
//                    //load hello page
//                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = null;
//        try {
//        scene = new Scene(fxmlLoader.load(), 600, 400);
//        } catch (IOException e) {
//        String msg2="LoginController.onLoginButtonClick(): "+e.getMessage();
//        System.out.println(msg2);
//        }
