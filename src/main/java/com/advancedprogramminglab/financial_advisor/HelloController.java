package com.advancedprogramminglab.financial_advisor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    public VBox helloRoot;
    @FXML
    public Button cdi;
    public ImageView imgprofile;
    @FXML
    private Label welcomeText;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        String loc="D:/new.json";
        try(FileReader fileReader = new FileReader(loc)){
            Transaction t=new Gson().fromJson(fileReader,Transaction.class);
            System.out.println(t.toJSON());
        } catch (IOException e) {
            System.out.println("HelloController.onCdiButtonClick():"+e.getMessage());
            e.printStackTrace();
        }
//        DBConnector.deleteUser(LaunchApplication.getCurrentUser().getUsername());
//        LaunchApplication.logOut();
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("layout/login-view.fxml"));
//            Scene scene = new Scene(root);
//            ((Stage) helloRoot.getScene().getWindow()).close();
//            Stage stage = new Stage();
//            stage.setTitle("Login");
//            stage.setScene(scene);
//            stage.show();
//            // Hide this current window (if this is what you want)
//            //((Node)(event.getSource())).getScene().getWindow().hide();
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    @FXML
    protected void onCdiButtonClick(ActionEvent event) {

        welcomeText.setText("JavaFX Application CDI");
        LaunchApplication.logOut();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/login-view.fxml"));
            Scene scene = new Scene(root);
            ((Stage) helloRoot.getScene().getWindow()).close();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    private PieChart chartPieFruit;

//    private final Parent root;
//    public Parent getRoot(){
//        return root;
//    }
//    public HelloController() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        root=fxmlLoader.load();
//    }
    public void initialize() {

        //Transaction t=gson.fromJson(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("new.json")),Transaction.class);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        chartPieFruit.setTitle("Imported Fruits");
        chartPieFruit.setData(pieChartData);
        cdi.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onCdiButtonClick(event);
            }
        });
        Image image = DBConnector.getImageFromDatabase(LaunchApplication.getCurrentUser().getId());
        if(image!=null){
            imgprofile.setImage(image);
        }
    }

}
