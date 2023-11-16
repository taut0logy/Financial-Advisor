package com.advancedprogramminglab.financial_advisor;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class SetBudgetController {
    public DatePicker start;
    public DatePicker end;
    public TextField amount;
    public Button confirm;

    public void initialize(){
        //set both datepickers to current date
        start.setValue(java.time.LocalDate.now());
        end.setValue(java.time.LocalDate.now());
        confirm.setOnAction(event -> {
            if(start.getValue().isAfter(end.getValue()))
                return;
            if(amount.getText().isEmpty())
                return;
            if(!amount.getText().matches("[0-9]+"))
                return;
            if(Integer.parseInt(amount.getText())<0)
                return;
            if(end.getValue()==null)
                return;
            //get the dates and amount
            java.sql.Date s=java.sql.Date.valueOf(start.getValue());
            java.sql.Date e=java.sql.Date.valueOf(end.getValue());
            int a=Integer.parseInt(amount.getText());
            //set the budget
            DBConnector.updateBudget(a,s,e);
            //close the window
            Stage stage = (Stage) confirm.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/dashboard-view.fxml"));
            try {
                stage.setScene(new javafx.scene.Scene(fxmlLoader.load()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            stage.show();
        });

    }
}
