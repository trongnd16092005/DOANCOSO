package com.example.demofx1.MAIN;

import com.example.demofx1.CONTROLLER.HomeAdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdminHomeMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            String controllerName = "HomeAdminController";
            String fxmlPath = "/com/example/demofx1/VIEW/" + controllerName.replaceAll("Controller", "") + ".fxml";

            URL url = getClass().getResource(fxmlPath);
            if (url != null) {
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                HomeAdminController controller=loader.getController();
                controller.clearBillProductData();

                Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            } else {
                System.err.println("Không tìm thấy tệp FXML: " + fxmlPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
