package com.advancedprogramminglab.financial_advisor;

import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DashboardController {
    public ImageView dashboardpic;
    public Label namelabel;
    public ProgressBar budgetbar;
    public Label budgetlabel;
    public Label budgetleftlabel;
    public VBox transactionlist;
    public BarChart chartdash;
    public Button addbtn;
    public Button removebtn;
    public Button deletebtn;
    public Button setbudgetbtn;
    public Button logoutbtn;

    public void initialize() {
        namelabel.setText("Hello " + LaunchApplication.getCurrentUser().getUsername() + "!");
        Image image = DBConnector.getImageFromDatabase(LaunchApplication.getCurrentUser().getId());
        if (image != null)
            dashboardpic.setImage(image);
        addbtn.setOnAction(event -> {
            try {
                Stage stage = (Stage) addbtn.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/add-transaction-view.fxml"));
                stage1.setTitle("Financial Advisor - Add Transaction");
                stage1.setScene(new javafx.scene.Scene(fxmlLoader.load()));
                stage1.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        removebtn.setOnAction(event -> {
            try {
                Stage stage = (Stage) removebtn.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/removetransaction-view.fxml"));
                stage1.setTitle("Financial Advisor - Remove Transaction");
                stage1.setScene(new javafx.scene.Scene(fxmlLoader.load()));
                stage1.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deletebtn.setOnAction(event -> {
            try {
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/report-menu-view.fxml"));
                stage1.setTitle("Financial Advisor - Report Generation");
                stage1.setScene(new javafx.scene.Scene(fxmlLoader.load()));
                stage1.show();

            } catch (IOException e) {
                System.out.println("DashboardController.initialize().deletebtn: " + e.getMessage());
                e.printStackTrace();
            }
        });
        setbudgetbtn.setOnAction(event -> {
            try {
                Stage stage = (Stage) setbudgetbtn.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/set-budget-view.fxml"));
                stage1.setTitle("Financial Advisor - Set Budget");
                stage1.setScene(new javafx.scene.Scene(fxmlLoader.load()));
                stage1.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logoutbtn.setOnAction(event -> {
            LaunchApplication.logOut();
            try {
                Stage stage = (Stage) logoutbtn.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/login-view.fxml"));
                stage1.setTitle("Financial Advisor - Login");
                stage1.setScene(new javafx.scene.Scene(fxmlLoader.load()));
                stage1.show();

            } catch (IOException e) {
                System.out.println("DashboardController.initialize().logoutbtn: " + e.getMessage());
                e.printStackTrace();
            }
        });
        ArrayList<Transaction> transactions = DBConnector.getTransactions(LaunchApplication.getCurrentUser().getId());
        int curBalance=0;
        if(transactions!=null && transactions.size()>0) {
            transactions.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            for (Transaction transaction : transactions) {
                Label label = new Label(transaction.toString());
                System.out.println(transaction.getId());
                transactionlist.getChildren().add(label);
                if(transaction.isExpense())
                    curBalance-=transaction.getAmount();
                else if(transaction.isIncome())
                    curBalance+=transaction.getAmount();
                else if(transaction.isSavings())
                    curBalance+=transaction.getAmount();
                else if(transaction.isInvestment())
                    curBalance+=transaction.getAmount();
                else if(transaction.isLoan())
                    curBalance-=transaction.getAmount();
            }
        }
        LaunchApplication.getCurrentUser().setBalance(curBalance);
        DBConnector.updateUserBalance(LaunchApplication.getCurrentUser().getId(),curBalance);
        budgetleftlabel.setText("Balance Left: " + LaunchApplication.getCurrentUser().getBalance());

        //show spending of last 7 days in chart

        budgetbar.setProgress((double) LaunchApplication.getCurrentUser().getBalance() / LaunchApplication.getCurrentUser().getBudget());
        System.out.println((double) LaunchApplication.getCurrentUser().getBalance() / LaunchApplication.getCurrentUser().getBudget());
        budgetlabel.setText("Budget: " + LaunchApplication.getCurrentUser().getBudget());
        budgetleftlabel.setText("Balance Left: " + LaunchApplication.getCurrentUser().getBalance());
    }
}
