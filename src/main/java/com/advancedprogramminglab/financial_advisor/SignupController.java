package com.advancedprogramminglab.financial_advisor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;

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
    public ImageView imgprofile;
    public Button imgprofilebtn;

    private String imgPath;

    public void initialize() {
        signupButton.setOnAction(this::onSignupButtonClick);
        dontlogin.setOnMouseClicked(this::onDontLoginClick);
        imgprofilebtn.setOnAction(this::onImgprofilebtnClick);
        imgPath="src/main/resources/com/advancedprogramminglab/financial_advisor/img/profile.jpg";
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
            Connection con = DBConnector.get();
            String table="CREATE TABLE `financial_advisor`.`"+String.valueOf(LaunchApplication.getCurrentUser().getId())+"` (\n" +
                    "  `index` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "  `transaction` JSON NOT NULL,\n" +
                    "  PRIMARY KEY (`index`));";
            try {
                assert con != null;
                con.createStatement().executeUpdate(table);
            } catch (Exception e) {
                System.out.println("SignupController.onSignupButtonClick(): " + e.getMessage());
            }
            System.out.println(msg+"imgPath: "+imgPath);
            File file = new File(imgPath);
            if(!file.exists()){
                //get file from resources
                file = new File(imgPath);
                if(!file.exists()){
                    System.out.println(msg+"File not found");
                    return;
                }
            }
            DBConnector.saveImageToDatabase(LaunchApplication.getCurrentUser().getId(), file);
            System.out.println(msg+"pic saved");
            Stage stage2 = (Stage) signupButton.getScene().getWindow();
            stage2.close();
            Stage stage = new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/hello-view.fxml"));
                Parent root = fxmlLoader.load();
                stage.setTitle("Welcome!");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.out.println("SignupController.onSignupButtonClick(): " + e.toString());
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

    private void onImgprofilebtnClick(ActionEvent event) {
        //selcect image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imgprofilebtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null) {
            //get file size
            long size = selectedFile.length();
            if(size>18000000){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("File too large");
                alert.setContentText("Please select a file less than 1MB.");
                alert.showAndWait();
                return;
            }
            imgPath = selectedFile.getAbsolutePath();
            System.out.println("File selected: " + imgPath);
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            System.out.println("File selected: " + selectedFile.toURI().toString());
            Image image = new Image(selectedFile.toURI().toString());
            double desiredWidth = image.getWidth();
            double desiredHeight = image.getWidth();

            double centerX = image.getWidth() / 2;
            double centerY = image.getHeight() / 2;

            double cropX = centerX - (desiredWidth / 2);
            double cropY = centerY - (desiredHeight / 2);

            imgprofile.setViewport(new Rectangle2D(cropX, cropY, desiredWidth, desiredHeight));
            imgprofile.setImage(image);
        }

    }

}

