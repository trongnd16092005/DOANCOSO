package com.example.demofx1.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginScreenMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            String controllerName = "LoginScreenController";
            String fxmlPath = "/com/example/demofx1/FXML/" + controllerName.replaceAll("Controller", "") + ".fxml";

            // Lấy URL của tệp FXML
            URL url = getClass().getResource(fxmlPath);
            if (url != null) {
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();

                Scene scene = new Scene(root);
                primaryStage.setTitle("B.A.M.B.O.O");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            } else {
                System.err.println("Không tìm thấy tệp FXML: " + fxmlPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}