package com.example.demofx1.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScreenMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/com/example/demofx1/FXML/LoginScreen.fxml"));
            Scene scene=new Scene(root);
            primaryStage.setTitle("B.A.M.B.O.O");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
