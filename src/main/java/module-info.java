module com.example.demofx1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires  org.kordamp.ikonli.fontawesome5;
    requires  org.kordamp.ikonli.ociicons;
    requires java.sql;
    requires org.apache.pdfbox;
    requires kernel;
    requires layout;
    requires java.desktop;


    exports com.example.demofx1.MAIN;
    opens com.example.demofx1.MAIN to javafx.fxml;
    exports com.example.demofx1.CONTROLLER;
    opens com.example.demofx1.CONTROLLER to javafx.fxml;
    exports com.example.demofx1.MODEL;
    opens com.example.demofx1.MODEL to javafx.fxml;
}