package com.advancedprogramminglab.financial_advisor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DBConnector {
    static int randomNum() {
        return (int) (Math.random() * 1000000000 * Math.random());
    }
    public static Connection get() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/financial_advisor", "root", "suruKUET113");

        } catch (Exception e) {
            String msg="DBConnector.get(): "+e.getMessage();
            System.out.println(msg);
        }
        return null;
    }
        public static int getUser(int id,User user){
        Connection con = DBConnector.get();
        String table="financial_advisor.user_info";
        String finduser="select * from "+table;
        //User user = new User();
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(finduser);
            while (rs.next()){
                if(id==rs.getInt(1)) {
                        user.setId(rs.getInt(1));
                        user.setUsername(rs.getString(2));
                        user.setFirstName(rs.getString(3));
                        user.setLastName(rs.getString(4));
                        user.setEmail(rs.getString(5));
                        user.setDateJoined(rs.getTimestamp(6));
                        con.close();
                        System.out.println("DBConnector.getUser(): "+user.getFirstName()+" "+user.getLastName());
                        return 1;
                    }
            }
            con.close();
            return 0;
        } catch (Exception e) {
            String msg="DBConnector.getUser(): "+e.getMessage();
            System.out.println(msg);
        }
        return 0;
    }
    public static int getSigninID(String username,String password) {
        Connection con = DBConnector.get();
        String table="financial_advisor.signin_info";
        String finduser="select * from "+table;
        User user = new User();
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(finduser);
            while (rs.next()){
                if(username.equals(rs.getString(1))) {
                    if(password.equals(rs.getString(2))){
                        int id=rs.getInt(3);
                        con.close();
                        return id;
                    }
                    else {
                        con.close();
                        return -2;
                    }
                }
            }
            con.close();
            return -1;

        } catch (Exception e) {
            String msg="DBConnector.getSigninID(): "+e.getMessage();
            System.out.println(msg);
        }
        return 0;
    }

    public static int logIn(int id){
        User user = new User();
        if(getUser(id,user)==0){
            System.out.println("DBConnector.logIn(): User not found");
            return 0;
        }
        LaunchApplication.setCurrentUser(user);
        User user1 = LaunchApplication.getCurrentUser();
        System.out.println("DBConnector.logIn(): Login successful");
        System.out.println("DBConnector.logIn(): "+user1.getFirstName()+" "+user1.getLastName());
        return 1;
    }

    public static int newUser(String username,String password,String firstName,String lastName,String email){
        Connection con = DBConnector.get();
        String table="financial_advisor.signin_info";
        String finduser="select * from "+table;
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(finduser);
            while (rs.next()){
                if(username.equals(rs.getString(1))) {
                    con.close();
                    return -1;
                }
            }
            int id=randomNum();
            String insert="insert into "+table+" values('"+username+"','"+password+"',"+id+")";
            stmt.executeUpdate(insert);
            con.close();
            Connection con2 = DBConnector.get();
            Statement stmt2 = con2.createStatement();
            table="financial_advisor.user_info";
            insert="insert into "+table+" values("+id+",'"+username+"','"+firstName+"','"+lastName+"','"+email+"',now())";
            stmt2.executeUpdate(insert);
            con2.close();
            User user = new User();
            if(getUser(id,user)==0){
                System.out.println("DBConnector.newUser(): User not found");
                return 0;
            }
            LaunchApplication.setCurrentUser(user);
            User user1 = LaunchApplication.getCurrentUser();
            System.out.println("DBConnector.newUser(): Signup successful");
            System.out.println("DBConnector.newUser(): "+user1.getFirstName()+" "+user1.getLastName());
            return 1;
        } catch (Exception e) {
            String msg="DBConnector.newUser(): "+e.getMessage();
            System.out.println(msg);
        }
        return 0;
    }
    public static int updateUser(String username,String password,String firstName,String lastName,String email){
        Connection con = DBConnector.get();
        String table="financial_advisor.signin_info";
        String finduser="select * from "+table;
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(finduser);
            while (rs.next()){
                if(username.equals(rs.getString(1))) {
                    int id=rs.getInt(3);
                    String update="update "+table+" set password='"+password+"' where id="+id;
                    stmt.executeUpdate(update);
                    con.close();
                    con = DBConnector.get();
                    table="financial_advisor.user_info";
                    update="update "+table+" set first_name='"+firstName+"',last_name='"+lastName+"',email='"+email+"' where id="+id;
                    stmt.executeUpdate(update);
                    con.close();
                    return 1;
                }
            }
            con.close();
            return -1;
        } catch (Exception e) {
            String msg="DBConnector.updateUser(): "+e.getMessage();
            System.out.println(msg);
        }
        return 0;
    }
    public static int deleteUser(String username){
        Connection con = DBConnector.get();
        String table="financial_advisor.signin_info";
        String finduser="select * from "+table;
        try {
            assert con != null;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(finduser);
            while (rs.next()){
                if(username.equals(rs.getString(1))) {
                    int id=rs.getInt(3);
                    String delete="delete from "+table+" where id="+id;
                    stmt.executeUpdate(delete);
                    con.close();
                    con = DBConnector.get();
                    table="financial_advisor.user_info";
                    delete="delete from "+table+" where id="+id;
                    stmt.executeUpdate(delete);
                    con.close();
                    return 1;
                }
            }
            con.close();
            return -1;
        } catch (Exception e) {
            String msg="DBConnector.deleteUser(): "+e.getMessage();
            System.out.println(msg);
        }
        return 0;
    }
}