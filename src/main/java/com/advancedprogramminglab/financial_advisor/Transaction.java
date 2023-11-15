package com.advancedprogramminglab.financial_advisor;

import com.google.gson.Gson;
import javafx.scene.paint.Color;

import com.google.gson.JsonObject;
import java.util.Date;

//import static javax.json.Json.createObjectBuilder;


public class Transaction {
    private int id,amount;
    private String type,description;
    private Date date;
    private String color;
    public Transaction() {
    }

    public Transaction(int id, int amount, String type, String description, Date date, String color) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = date;
        this.color = color;
    }

    public JsonObject toJSON() {
        Gson gson = new Gson();
        JsonObject jsonObject = new com.google.gson.JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("description", description);
        jsonObject.addProperty("date", date.toString());
        jsonObject.addProperty("color", color);
        return jsonObject;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
