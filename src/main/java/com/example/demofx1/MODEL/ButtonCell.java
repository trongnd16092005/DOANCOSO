package com.example.demofx1.MODEL;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;

public class ButtonCell extends TableCell<ProductData, String> {
    final Button showButton = new Button("Hiển thị");

    public ButtonCell() {
        showButton.setOnAction(event -> {
            ProductData currentProduct = getTableView().getItems().get(getIndex());
            String imagePath = currentProduct.getImage();

            try {
                String sanitizedPath = sanitizePath(imagePath);

                Image image = new Image(sanitizedPath);

                Stage stage = new Stage();
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(image.getWidth());
                imageView.setFitHeight(image.getHeight());
                StackPane root = new StackPane(imageView);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String sanitizePath(String path) {
        if (path.startsWith("file:///")) {
            return path.replace("file:///", "file:/");
        } else if (new File(path).exists()) {
            return new File(path).toURI().toString();
        }
        return path;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(showButton);
        }
    }
}
