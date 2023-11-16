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

    boolean isExpense(){
        return id==2;
    }
    boolean isIncome(){
        return id==1;
    }
    boolean isSavings(){
        return id==3;
    }
    boolean isInvestment(){
        return id==4;
    }
    boolean isLoan(){
        return id==5;
    }

    boolean isBefore(Date d){
        return date.before(d);
    }

    boolean isAfter(Date d){
        return date.after(d);
    }

    boolean isBetween(Date d1,Date d2){
        return date.after(d1) && date.before(d2);
    }

    boolean isBetweenInclusive(Date d1,Date d2){
        return date.equals(d1) || date.equals(d2) || (date.after(d1) && date.before(d2));
    }

    boolean isBefore(Transaction t){
        return date.before(t.getDate());
    }


    public String toString() {
        String sign="+";
        if(isExpense())
            sign="-";
        else if(isIncome())
            sign="+";
        else if(isSavings())
            sign="+";
        else if(isInvestment())
            sign="+";
        else if(isLoan())
            sign="-";
        String dates=date.toString();
        //dates=dates.substring(0,dates.indexOf(" "));
        String s =type+": "+sign+String.valueOf(amount)+" "+description+" "+dates;
        return s;
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
