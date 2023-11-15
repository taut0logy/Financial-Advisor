module com.advancedprogramminglab.financial_advisor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.jfoenix;
    //requires javax.json;
    requires com.google.gson;

    //requires mysql.connector.java;

    opens com.advancedprogramminglab.financial_advisor to javafx.fxml,com.google.gson;
    exports com.advancedprogramminglab.financial_advisor;
}