package com.advancedprogramminglab.financial_advisor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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
    @FXML
    public Label dontTextLogin;


    public void initialize() {
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
            Stage stage1 = (Stage) btnLogin.getScene().getWindow();
            stage1.close();
            Stage stage = new Stage();
            stage.setTitle("Financial Advisor - Home");
            //load hello page
            try {
                //LaunchApplication.changeScene("layout/hello-view.fxml", event);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/dashboard-view.fxml"));
                Parent root = fxmlLoader.load();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                String msg2="LoginController.onLoginButtonClick(): "+e.getMessage();
                System.out.println(msg2);
            }
            //close login page
            //Stage stage = (Stage) loginRoot.getScene().getWindow();
            //stage.close();
        }
    }


    public void ondontentered(MouseEvent mouseEvent) {
        //dontTextLogin.setUnderline(true);
    }

    public void ondontexited(MouseEvent mouseDragEvent) {
        //dontTextLogin.setUnderline(false);
    }

    public void dontonmouseclicked(MouseEvent mouseEvent) {
        //load signup page
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/signup-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = (Stage) dontTextLogin .getScene().getWindow();
            stage1.close();
            Stage stage = new Stage();
            stage.setTitle("Financial Advisor - Sign up");
            stage.setScene(new Scene(root));
            stage.show();
            //close current stage

        } catch (IOException e) {
            String msg="LoginController.dontonmouseclicked(): "+e.getMessage();
            System.out.println(msg);
        }

    }
}
