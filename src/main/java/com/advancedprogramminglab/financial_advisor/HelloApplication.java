package com.advancedprogramminglab.financial_advisor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

//        Connection con = DBConnector.get();
//        System.out.println(con);
//        try {
//            assert con != null;
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from signin_info");
//            while (rs.next())
//                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        stage.setTitle("Financial Advisor");
        stage.setScene(scene);
        stage.show();
    }

    //public static void main(String[] args) {
    //    launch();
    //}
}