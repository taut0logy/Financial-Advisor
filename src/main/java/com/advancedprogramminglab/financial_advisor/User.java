package com.advancedprogramminglab.financial_advisor;

import javafx.scene.image.Image;

import java.sql.Timestamp;
import java.util.Date;

public class User {
    private String username,firstName,lastName,email;
    private Timestamp dateJoined;
    private int id,balance,budget;
    private java.sql.Date budgetDate,budgetEndDate;

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(java.sql.Date budgetDate) {
        this.budgetDate = budgetDate;
    }

    public Date getBudgetEndDate() {
        return budgetEndDate;
    }

    public void setBudgetEndDate(java.sql.Date budgetEndDate) {
        this.budgetEndDate = budgetEndDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Timestamp dateJoined) {
        this.dateJoined = dateJoined;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(){balance=0;}

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public User(String username, String firstName, String lastName, String email, Timestamp dateJoined, int id) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateJoined = dateJoined;
        this.id = id;
    }
}
