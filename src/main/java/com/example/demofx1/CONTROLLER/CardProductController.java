package com.example.demofx1.CONTROLLER;

import com.example.demofx1.MODEL.DataBaseConnection;
import com.example.demofx1.MODEL.ProductData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CardProductController implements Initializable {
    @FXML
    private AnchorPane cardForm;

    @FXML
    private Label namePro;

    @FXML
    private Label pricePro;

    @FXML
    private ImageView proImage;

    @FXML
    private Button productBut;

    @FXML
    private Spinner<Integer> productSpin;
    private ProductData productData;
    private Image image;
    private int currentValue;
    private HomeAdminController homeController;

    // Thiết lập dữ liệu sản phẩm cho thẻ sản phẩm
    public void setData(ProductData productData) {
        this.productData = productData;
        namePro.setText(productData.getName() + "-" + productData.getID());
        pricePro.setText(productData.getPrice() + " VNĐ");

        // Kiểm tra xem đường dẫn ảnh có chứa "file:" hay không
        String path = productData.getImage().startsWith("file:") ? productData.getImage() : "file:" + productData.getImage();

        // Tạo hình ảnh từ đường dẫn được xử lý
        image = new Image(path, 175, 111, false, true);
        proImage.setImage(image);
    }

    // Thiết lập Spinner cho số lượng sản phẩm
    public void setSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        valueFactory.setValue(0);
        productSpin.setValueFactory(valueFactory);
        productSpin.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = productSpin.getValue();
            }
        });
    }

    // Thiết lập HomeController để cập nhật giao diện
    public void setHomeController(HomeAdminController homeController) {
        this.homeController = homeController;
    }

    // Phương thức thêm sản phẩm vào cơ sở dữ liệu
    public void setAddProduct() throws IOException {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/demofx1/VIEW/HomeAdmin.fxml"));
        Parent root = loader.load();
        HomeAdminController homeAdminController = loader.getController();

        String checkProduct = "SELECT QUANTITY, PRICE FROM appmuaban.billproduct WHERE NAME = ?";
        String updateProduct = "UPDATE appmuaban.billproduct SET QUANTITY = ?, PRICE = ? WHERE NAME = ?";
        String addProduct = "INSERT INTO appmuaban.billproduct VALUE(?,?,?,?,?)";

        if (currentValue > 0) {
            try {
                PreparedStatement checkStmt = connectDatabase.prepareStatement(checkProduct);
                checkStmt.setString(1, productData.getName());
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    // Sản phẩm đã tồn tại, cập nhật số lượng và giá
                    int existingQuantity = resultSet.getInt("QUANTITY");
                    int newQuantity = existingQuantity + currentValue;
                    int pricePerUnit = resultSet.getInt("PRICE") / existingQuantity;
                    int newPrice = pricePerUnit * newQuantity;

                    PreparedStatement updateStmt = connectDatabase.prepareStatement(updateProduct);
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setInt(2, newPrice);
                    updateStmt.setString(3, productData.getName());
                    updateStmt.executeUpdate();
                } else {
                    // Sản phẩm chưa tồn tại, chèn bản ghi mới
                    PreparedStatement addStmt = connectDatabase.prepareStatement(addProduct);
                    addStmt.setString(1, Integer.toString(productData.getID()));
                    addStmt.setString(2, productData.getName());
                    addStmt.setString(3, productData.getManufacturer());
                    addStmt.setString(4, Integer.toString(currentValue));
                    addStmt.setString(5, Integer.toString(productData.getPrice() * currentValue));
                    addStmt.executeUpdate();
                }

                // Cập nhật lại giao diện
                homeAdminController.showViewProductList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Đặt lại giá trị của Spinner
        productSpin.getValueFactory().setValue(0);
    }

    // Phương thức khởi tạo khi lớp này được tạo
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSpinner();
    }
}
