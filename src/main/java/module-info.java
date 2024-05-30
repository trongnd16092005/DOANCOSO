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


    exports com.example.demofx1.Main;
    opens com.example.demofx1.Main to javafx.fxml;
    exports com.example.demofx1.Controller;
    opens com.example.demofx1.Controller to javafx.fxml;
    exports com.example.demofx1.Data_Connection;
    opens com.example.demofx1.Data_Connection to javafx.fxml;
}