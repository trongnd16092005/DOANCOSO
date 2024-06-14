package com.example.demofx1.MAIN;

import com.example.demofx1.CONTROLLER.HomeAdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      // Xác định tên của controller và tạo đường dẫn đến tệp FXML
      String controllerName = "HomeAdminController";
      String fxmlPath = "/com/example/demofx1/VIEW/" + controllerName.replaceAll("Controller", "") + ".fxml";

      // Tải tệp FXML
      URL url = getClass().getResource(fxmlPath);
      if (url != null) {
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        // Lấy instance của controller và thực hiện các thiết lập ban đầu
        HomeAdminController controller=loader.getController();
        controller.clearBillProductData();

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BAMBOO");
        primaryStage.show();
      } else {
        System.err.println("Không tìm thấy tệp FXML: " + fxmlPath);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
