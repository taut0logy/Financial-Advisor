package com.advancedprogramminglab.financial_advisor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LaunchApplication extends Application {

    private static User CurrentUser;
    public static void setCurrentUser( User currentUser) {
        String msg="LaunchApplication.setCurrentUser(): ";
        CurrentUser=new User();
        CurrentUser.setId(currentUser.getId());
        CurrentUser.setFirstName(currentUser.getFirstName());
        CurrentUser.setLastName(currentUser.getLastName());
        CurrentUser.setEmail(currentUser.getEmail());
        CurrentUser.setUsername(currentUser.getUsername());
        CurrentUser.setDateJoined(currentUser.getDateJoined());
        System.out.println(msg+CurrentUser.getFirstName()+" "+CurrentUser.getLastName());
        Connection con = DBConnector.get();
        String table="financial_advisor.current_user";
        String username=currentUser.getUsername();
        int uid=currentUser.getId();
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into "+table+" values('"+username+"',"+uid+")");
        } catch (Exception e) {
            System.out.println("setCurrentUser: "+e.getMessage());
        }
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }

    public void start(Stage primaryStage) throws IOException {
        String msg="LaunchApplication.start(): ";
        Connection con = DBConnector.get();
        String table="financial_advisor.current_user";
        //get 1st row
        String finduser="select * from "+table+" limit 1";
        String username="";
        int uid = 0;
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(finduser);
            if(rs.next()){
                username=rs.getString(1);
                uid=rs.getInt(2);
            }
        } catch (Exception e) {
            System.out.println("LaunchApplication.start(): " + e.getMessage());
        }

        System.out.println(msg + "after try catch " + username);
        if(username.isEmpty()){

            System.out.println(msg+"No user logged in");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Financial Advisor");
            primaryStage.setScene(scene);
        }
        else{
            System.out.println(msg+"User logged in");
            User user=new User();
            DBConnector.getUser(uid,user);
            LaunchApplication.setCurrentUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Financial Advisor");
            primaryStage.setScene(scene);

            System.out.println(msg+user.getFirstName()+" "+user.getLastName());
        }
        primaryStage.setTitle("Financial Advisor");
        primaryStage.show();
    }

    public static void changeScene(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(LaunchApplication.class.getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        //LaunchApplication.getStage().getScene().setRoot(pane);
        stage.setScene(scene);
    }

    public static void logOut(){
        String msg="LaunchApplication.logOut(): ";
        Connection con = DBConnector.get();
        String table="financial_advisor.current_user";
        String username=CurrentUser.getUsername();
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from "+table+" where username='"+username+"'");
        } catch (Exception e) {
            System.out.println(msg+e.getMessage());
        }
        CurrentUser=null;
        System.out.println(msg+"User logged out");
    }

    public static void main(String[] args) {launch(args);}
}
