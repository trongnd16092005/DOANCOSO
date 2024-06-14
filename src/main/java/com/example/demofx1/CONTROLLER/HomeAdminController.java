package com.example.demofx1.CONTROLLER;

import com.example.demofx1.MODEL.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import java.time.LocalDateTime;
import java.util.*;

public class HomeAdminController implements Initializable {
    @FXML
    TableView<PaymentData> totalIncomeTable;
    @FXML
    TableColumn<PaymentData, Integer> idPayment;
    @FXML
    TableColumn<PaymentData, String> IDUserPayment;
    @FXML
    TableColumn<PaymentData, Integer> amountPayment;
    @FXML
    TableColumn<PaymentData, LocalDateTime> datePayment;
    @FXML
    TableView<PaymentData> todayIncomeTable;
    @FXML
    TableColumn<PaymentData, Integer> todayIDPayment;
    @FXML
    TableColumn<PaymentData, String> todayIDUserPayment;
    @FXML
    TableColumn<PaymentData, Integer> todayAmountPayment;
    @FXML
    TableColumn<PaymentData, LocalDateTime> todayDatePayment;
    @FXML
    TableView<ProductData> productTable;
    @FXML
    TableColumn<ProductData, Integer> idProductCol;
    @FXML
    TableColumn<ProductData, String> nameProductCol;
    @FXML
    TableColumn<ProductData, String> manufacturerCol;
    @FXML
    TableColumn<ProductData, Integer> priceCol;
    @FXML
    TableColumn<ProductData, String> imageCol;//status
    @FXML
    Button income;
    @FXML
    Button products;
    @FXML
    Button POS;
    @FXML
    ImageView imageView;
    @FXML
    AnchorPane POSPane;
    @FXML
    Button signOutButton;
    @FXML
    Label nameAdminHome;
    @FXML
    AnchorPane incomeChartPane;
    @FXML
    AnchorPane incomePane;
    @FXML
    Label totalIncomeLabel;
    @FXML
    Label todayIncomeLabel;
    @FXML
    AnchorPane productsPane;
    @FXML
    AnchorPane addProPane;
    @FXML
    Button addProBut;
    @FXML
    Button deleteProBut;
    @FXML
    Button confirmADDBut;
    @FXML
    TextField priceADDText;
    @FXML
    TextField manufacturerText;
    @FXML
    TextField nameADDText;
    @FXML
    TextField IDADDText;

    @FXML
    Label alertADDPro;
    @FXML
    TextField IDDELETEText;
    @FXML
    TextField nameDELETEText;
    @FXML
    Button confirmDELETEBut;
    @FXML
    AnchorPane deleteProPane;
    @FXML
    Label alertDELETEPro;
    @FXML
    AnchorPane changeProPane;
    @FXML
    TextField IDChangeText;
    @FXML
    TextField nameChangeText;
    @FXML
    TextField changeManufacturerText;
    @FXML
    TextField priceChangeText;
    @FXML
    Button changeProBut;
    @FXML
    Label alertChangePro;
    @FXML
    Button confirmChangeBut;
    @FXML
    LineChart<String, Number> lineIncomeChart;

    @FXML
    GridPane menuGridPane;
    @FXML
    TextField searchField;
    @FXML
    TableView<ProductData> cartTable;
    @FXML
    TableColumn<ProductData, Integer> qCol;
    @FXML
    TableColumn<ProductData, String> nameCartCol;
    @FXML
    TableColumn<ProductData, Integer> pCol;
    @FXML
    Label totalLabel;
    @FXML
    ImageView addImageView;
    @FXML
    ImageView changeImageView;
    @FXML
    Button chooseImg;
    @FXML
    Button chooseImg1;
    @FXML
    TableView<CustomerData> customerTable;
    @FXML
    TableColumn<CustomerData, String> nameCol;
    @FXML
    TableColumn<CustomerData, String> phoneCol;
    @FXML
    TableColumn<CustomerData, Integer> totalSpentCol;
    @FXML
    Button deleteCusBut;
    @FXML
    Button addCusBut;
    @FXML
    AnchorPane deleteCusPane;
    @FXML
    AnchorPane ADDCusPane;
    @FXML
    TextField CusNameDELETE;
    @FXML
    TextField CusPNumDELETE;
    @FXML
    Button confirmDELETECus;
    @FXML
    TextField ADDCusName;
    @FXML
    TextField ADDCusPNum;
    @FXML
    Button confirmADDCus;
    @FXML
    Button customerBut;
    @FXML
    AnchorPane CustomerPane;

    private ObservableList<CustomerData> customerList;

    @FXML
    TextField customerField;
    private final String destDirectoryPath = "C:\\Users\\Admin\\IdeaProjects\\BAMBOO\\src\\main\\resources\\com\\example\\demofx1\\Images";
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Timeline timeline;

    public String urlImage = "";
    ObservableList<ProductData> cardListData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIncomeChart();
        setLabelOfIncome();

        menuDisplayCard();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterProductList(newValue);
        });

        showViewProductList();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            showViewProductList();
            totalLabel.setText(getTotalPrice() + "VND");
            setLabelOfIncome();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        nameOrID();
        nameOrIDCus();
        lineIncomeChart.setVisible(true);
        totalIncomeTable.setVisible(false);
        todayIncomeTable.setVisible(false);

        POSPane.setVisible(true);
        incomePane.setVisible(false);
        productsPane.setVisible(false);
        CustomerPane.setVisible(false);

        deleteCusBut.setDisable(true);
        addCusBut.setDisable(false);
        deleteCusPane.setVisible(false);
        ADDCusPane.setVisible(true);

        addProPane.setVisible(true);
        deleteProPane.setVisible(false);
        InputStream inputStream = getClass().getResourceAsStream("/com/example/demofx1/IMAGES/bamboo.png");
    }

    // Thiết lập biểu đồ thu nhập theo tháng
    public void setIncomeChart() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int month = currentDateTime.getMonthValue();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Data<String, Number> fourMAgo = new XYChart.Data<>(changeMonthToString(month - 4), getIncomePerM(month - 4));
        XYChart.Data<String, Number> threeMAgo = new XYChart.Data<>(changeMonthToString(month - 3), getIncomePerM(month - 3));
        XYChart.Data<String, Number> twoMAgo = new XYChart.Data<>(changeMonthToString(month - 2), getIncomePerM(month - 2));
        XYChart.Data<String, Number> oneMAgo = new XYChart.Data<>(changeMonthToString(month - 1), getIncomePerM(month - 1));
        XYChart.Data<String, Number> nowM = new XYChart.Data<>(changeMonthToString(month), getIncomePerM(month));
        series.getData().addAll(fourMAgo, threeMAgo, twoMAgo, oneMAgo, nowM);
        series.setName("Income per month");
        lineIncomeChart.getData().add(series);
    }

    // Lấy tổng thu nhập theo tháng
    public String changeMonthToString(int month) {
        if (month == -3)
            return "Sep";
        if (month == -2)
            return "Oct";
        if (month == -1)
            return "Nov";
        if (month == 0)
            return "Dev";
        return switch (month) {
            case 1 -> "Jan";
            case 2 -> "Feb";
            case 3 -> "Mar";
            case 4 -> "Apr";
            case 5 -> "May";
            case 6 -> "Jun";
            case 7 -> "Jul";
            case 8 -> "Aug";
            case 9 -> "Sep";
            case 10 -> "Oct";
            case 11 -> "Nov";
            default -> "Dec";
        };
    }

    public int getIncomePerM(int month) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int year = currentDateTime.getYear();
        if (month < 1)
            year -= 1;
        if (month == -3)
            month = 9;
        if (month == -2)
            month = 10;
        if (month == -1)
            month = 11;
        if (month == 0)
            month = 12;
        int totalPerM = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String search = "SELECT * FROM appmuaban.payments WHERE MONTH(date)=" + month;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(search);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalPerM += resultSet.getInt("amount");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return totalPerM;
    }

    // Lấy danh sách thanh toán từ cơ sở dữ liệu
    public ObservableList<PaymentData> totalPaymentListData() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        ObservableList<PaymentData> paymentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appmuaban.payments";
        try {
            PaymentData paymentData;

            PreparedStatement preparedStatement = connectDatabase.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                java.sql.Timestamp timestamp = result.getTimestamp("date");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                paymentData = new PaymentData(result.getInt("payment_id"), result.getString("user_id"), result.getInt("amount"), localDateTime);
                paymentList.add(paymentData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    // Hiển thị danh sách thanh toán tổng quát
    public void showTotalPaymentList() {
        ObservableList<PaymentData> showTotal = totalPaymentListData();
        idPayment.setCellValueFactory(new PropertyValueFactory<>("id"));
        IDUserPayment.setCellValueFactory(new PropertyValueFactory<>("userID"));
        amountPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        datePayment.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalIncomeTable.setItems(showTotal);
        totalIncomeTable.setVisible(true);
        lineIncomeChart.setVisible(false);
        todayIncomeTable.setVisible(false);
    }

    // Lấy danh sách thanh toán trong ngày
    public ObservableList<PaymentData> todayPaymentListData() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        LocalDateTime checkTime = LocalDateTime.now();
        int day = checkTime.getDayOfMonth();
        int month = checkTime.getMonthValue();
        int year = checkTime.getYear();
        ObservableList<PaymentData> paymentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appmuaban.payments WHERE YEAR(date)=" + year + " AND MONTH(date)=" + month + " AND DAYOFMONTH(date)=" + day;
        try {
            PaymentData paymentData;

            PreparedStatement preparedStatement = connectDatabase.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                java.sql.Timestamp timestamp = result.getTimestamp("date");
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                paymentData = new PaymentData(result.getInt("payment_id"), result.getString("user_id"), result.getInt("amount"), localDateTime);
                paymentList.add(paymentData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    // Hiển thị danh sách thanh toán trong ngày
    public void showTodayPaymentList() {
        ObservableList<PaymentData> showToday = todayPaymentListData();
        todayIDPayment.setCellValueFactory(new PropertyValueFactory<>("id"));
        todayIDUserPayment.setCellValueFactory(new PropertyValueFactory<>("userID"));
        todayAmountPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        todayDatePayment.setCellValueFactory(new PropertyValueFactory<>("date"));
        todayIncomeTable.setItems(showToday);
        totalIncomeTable.setVisible(false);
        lineIncomeChart.setVisible(false);
        todayIncomeTable.setVisible(true);
    }

    // Lấy danh sách sản phẩm từ cơ sở dữ liệu
    public void setProductTable() {
        ObservableList<ProductData> showProduct = productListData();
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Sử dụng ButtonCell cho cột image
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageCol.setCellFactory(new Callback<TableColumn<ProductData, String>, TableCell<ProductData, String>>() {
            @Override
            public TableCell<ProductData, String> call(TableColumn<ProductData, String> param) {
                return new ButtonCell();
            }
        });

        productTable.setItems(showProduct);
    }

    // Lấy danh sách sản phẩm từ cơ sở dữ liệu
    public ObservableList<ProductData> productListData() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ObservableList<ProductData> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appmuaban.product";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductData productData = new ProductData(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("MANUFACTURER"),
                        resultSet.getInt("PRICE"),
                        resultSet.getString("IMAGE")
                );
                productList.add(productData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    // Xử lý sự kiện chuyển đổi giữa các tab (Thu nhập, Sản phẩm, POS, Khách hàng)
    public void switchPane(ActionEvent event) {
        if (event.getSource() == income) {//Thu nhập
            incomePane.setVisible(true);
            productsPane.setVisible(false);
            POSPane.setVisible(false);
            CustomerPane.setVisible(false);
        }
        if (event.getSource() == products) {//Sản phẩm
            productsPane.setVisible(true);
            incomePane.setVisible(false);
            POSPane.setVisible(false);
            CustomerPane.setVisible(false);
            addProPane.setVisible(true);
            changeProPane.setVisible(false);
            addProBut.setDisable(true);
            deleteProBut.setDisable(false);
            changeProBut.setDisable(false);
            deleteProPane.setVisible(false);
            productTable.getSelectionModel().clearSelection();
            urlImage=null;
            changeImageView.setImage(null);
            addImageView.setImage(null);
            setProductTable();

        }
        if (event.getSource() == addProBut) {//Nút thêm sản phẩm
            deleteProPane.setVisible(false);
            addProPane.setVisible(true);
            deleteProBut.setDisable(false);
            addProBut.setDisable(true);
            changeProPane.setVisible(false);
            changeProBut.setDisable(false);
            productTable.getSelectionModel().clearSelection();
            urlImage=null;
            changeImageView.setImage(null);
            addImageView.setImage(null);
            clearProductPane();
        }
        if (event.getSource() == deleteProBut) {//Nút xóa sản phẩm
            deleteProPane.setVisible(true);
            addProPane.setVisible(false);
            deleteProBut.setDisable(true);
            addProBut.setDisable(false);
            changeProPane.setVisible(false);
            changeProBut.setDisable(false);
            productTable.getSelectionModel().clearSelection();
            urlImage=null;
            changeImageView.setImage(null);
            addImageView.setImage(null);
            clearProductPane();
        }
        if (event.getSource() == changeProBut) {//Nút thay đổi thông số sản phẩm
            addProPane.setVisible(false);
            deleteProPane.setVisible(false);
            changeProPane.setVisible(true);
            addProBut.setDisable(false);
            deleteProBut.setDisable(false);
            changeProBut.setDisable(true);
            productTable.getSelectionModel().clearSelection();
            urlImage=null;
            changeImageView.setImage(null);
            addImageView.setImage(null);
            clearProductPane();
        }
        if (event.getSource() == POS) {//Điểm bán hàng
            incomePane.setVisible(false);
            productsPane.setVisible(false);
            POSPane.setVisible(true);
            CustomerPane.setVisible(false);
            menuDisplayCard();
        }
        if(event.getSource()==customerBut){//Khách hàng
            clearProductPane();
            incomePane.setVisible(false);
            productsPane.setVisible(false);
            POSPane.setVisible(false);
            CustomerPane.setVisible(true);
            addCusBut.setDisable(true);
            deleteCusBut.setDisable(false);

            setCustomerTable();
        }
        if(event.getSource()==deleteCusBut){//Xóa khách hàng
            clearProductPane();
            deleteCusPane.setVisible(true);
            ADDCusPane.setVisible(false);
            addCusBut.setDisable(false);
            deleteCusBut.setDisable(true);

        }
        if(event.getSource()==addCusBut){//Thêm khách hàng
            clearProductPane();
            deleteCusPane.setVisible(false);
            ADDCusPane.setVisible(true);
            addCusBut.setDisable(true);
            deleteCusBut.setDisable(false);
        }
    }


    // Hiển thị biểu đồ thu nhập và ẩn các bảng thu nhập
    public void setVisibleIncomeChart() {
        lineIncomeChart.setVisible(true);
        totalIncomeTable.setVisible(false);
        todayIncomeTable.setVisible(false);
    }


    // Thiết lập nhãn hiển thị tổng thu nhập và thu nhập hôm nay
    public void setLabelOfIncome() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        LocalDateTime localDateTime = LocalDateTime.now();
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        int totalIncome = 0;
        int todayIncome = 0;
        String total = "SELECT * FROM appmuaban.payments";
        String today = "SELECT * FROM appmuaban.payments WHERE YEAR(date)=" + year + " AND MONTH(date)=" + month + " AND DAYOFMONTH(date)=" + day;
        try {
            PreparedStatement todayPreparedStatement = connection.prepareStatement(today);
            PreparedStatement totalPreparedStatement = connection.prepareStatement(total);
            ResultSet todayResultSet = todayPreparedStatement.executeQuery();
            ResultSet totalResultSet = totalPreparedStatement.executeQuery();
            while (todayResultSet.next()) {
                todayIncome += todayResultSet.getInt("amount");
            }
            while (totalResultSet.next()) {
                totalIncome += totalResultSet.getInt("amount");
            }
            todayIncomeLabel.setText(todayIncome + " VND");
            totalIncomeLabel.setText(totalIncome + " VND");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Thêm sản phẩm mới vào cơ sở dữ liệu
    public void setAddProduct() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        System.out.println(urlImage+"dau add");
        String x=chooseImageAndSave();
        try {
            if (IDADDText.getText().isEmpty() || nameADDText.getText().isEmpty() || manufacturerText.getText().isEmpty() || priceADDText.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please fill in all fields and try again!");
            } else {
                String checkQuery = "SELECT COUNT(1) FROM appmuaban.product WHERE ID = ? OR NAME = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, IDADDText.getText());
                    checkStatement.setString(2, nameADDText.getText());
                    ResultSet resultSet = checkStatement.executeQuery();
                    System.out.println(urlImage+"truoc khi nhap");


                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Product Exists", "Product already exists. Please try again!");
                    } else {
                        String addQuery = "INSERT INTO appmuaban.product(ID, NAME, MANUFACTURER, IMAGE, PRICE) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement addStatement = connection.prepareStatement(addQuery)) {
                            addStatement.setString(1, IDADDText.getText());
                            addStatement.setString(2, nameADDText.getText());
                            addStatement.setString(3, manufacturerText.getText());
                            addStatement.setString(4, x);
                            addStatement.setString(5, priceADDText.getText());
                            System.out.println(urlImage+"sau khi nhap");
                            urlImage = null;
                            System.out.println(urlImage+"sau khi xoa");
                            addImageView.setImage(null);
                            int affectedRows = addStatement.executeUpdate();

                            if (affectedRows > 0) {
                                setProductTable();
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Product Added", "Product added successfully!");
                                urlImage = null;
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Error", "Failed to Add Product", "Failed to add product. Please try again!");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Hiển thị hộp thoại cảnh báo
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    // Xóa sản phẩm khỏi cơ sở dữ liệu
    public void setDeletePro() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        String search = "SELECT * FROM appmuaban.product WHERE ID=? OR NAME=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(search);
            preparedStatement.setString(1, IDDELETEText.getText());
            preparedStatement.setString(2, nameDELETEText.getText());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String delete = "DELETE FROM appmuaban.product WHERE ID=? OR NAME=?";
                preparedStatement = connection.prepareStatement(delete);
                preparedStatement.setString(1, IDDELETEText.getText());
                preparedStatement.setString(2, nameDELETEText.getText());
                preparedStatement.executeUpdate();
                setProductTable();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product Deleted", "Deleted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Delete Failed", "Product not found. Please try again!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Kiểm tra xem trường tên hoặc ID có được điền không và vô hiệu hóa trường còn lại
    public void nameOrID() {
        nameDELETEText.textProperty().addListener((observableValue, s, t1) -> {
            IDDELETEText.setDisable(!t1.trim().isEmpty());
            confirmDELETEBut.setDisable(t1.trim().isEmpty());
            if (t1.trim().isEmpty())
                alertDELETEPro.setText("");
        });
        IDDELETEText.textProperty().addListener((observableValue, s, t1) -> {
            nameDELETEText.setDisable(!t1.trim().isEmpty());
            confirmDELETEBut.setDisable(t1.trim().isEmpty());
            if (t1.trim().isEmpty())
                alertDELETEPro.setText("");
        });
        confirmDELETEBut.setDisable(true);
    }


    // Xóa nội dung các trường nhập liệu của sản phẩm
    public void clearProductPane() {
        //Sản phẩm
        nameADDText.setText("");
        IDADDText.setText("");
        manufacturerText.setText("");
        priceADDText.setText("");;
        alertDELETEPro.setText("");

        //Sản phẩm
        IDDELETEText.setText("");
        nameDELETEText.setText("");
        alertADDPro.setText("");

        //Sản phẩm
        nameChangeText.setText("");
        IDChangeText.setText("");
        changeManufacturerText.setText("");
        priceChangeText.setText("");
        alertChangePro.setText("");

        //Khách hàng
        ADDCusName.setText("");
        ADDCusPNum.setText("");
        CusPNumDELETE.setText("");
        CusNameDELETE.setText("");
    }


    // Lấy giá trị của sản phẩm được chọn để thay đổi thông tin
    public void getValueProduct() {
        ProductData selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            IDChangeText.setText(String.valueOf(selected.getID()));
            nameChangeText.setText(selected.getName());
            changeManufacturerText.setText(selected.getManufacturer());
            priceChangeText.setText(String.valueOf(selected.getPrice()));

            String imageUrl = selected.getImage();
            if (!imageUrl.startsWith("file:")) {
                imageUrl = "file:" + imageUrl;
            }

            changeImageView.setImage(new Image(imageUrl));
        } else {
            alertChangePro.setText("Please choose again!");
        }
    }

    // Cập nhật thông tin sản phẩm
    public void setChangePro() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        String x=chooseImageAndSave();

        Connection connection = dataBaseConnection.getConnection();
        String change = "UPDATE appmuaban.product SET NAME=?, MANUFACTURER=?, IMAGE=?, PRICE=? WHERE ID=?";
        try {

            if (nameChangeText.getText().isEmpty() || IDChangeText.getText().isEmpty() || changeManufacturerText.getText().isEmpty() || priceChangeText.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Change Failed", "Please fill in all fields!");
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(change);
                preparedStatement.setString(1, nameChangeText.getText());
                preparedStatement.setString(2, changeManufacturerText.getText());
                preparedStatement.setString(3, x);
                preparedStatement.setString(4, priceChangeText.getText());
                preparedStatement.setString(5, IDChangeText.getText());
                preparedStatement.executeUpdate();
                setProductTable();
                urlImage = null;
                changeImageView.setImage(null);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Change Success", "Product information updated successfully!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Change Failed", "An error occurred while updating product information. Please try again!");
            throw new RuntimeException(e);
        }
    }


    // Lấy danh sách sản phẩm từ cơ sở dữ liệu
    public ObservableList<ProductData> menuGetData() {
        String sql = "SELECT * FROM appmuaban.product";
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ProductData productData;
            while (resultSet.next()) {
                productData = new ProductData(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("MANUFACTURER"),
                        resultSet.getInt("PRICE"),
                        resultSet.getString("IMAGE"));
                listData.add(productData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;
    }


    // Hiển thị danh sách sản phẩm trong một GridPane
    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(menuGetData());

        int col = 0;
        int row = 0;

        menuGridPane.getColumnConstraints().clear();
        menuGridPane.getRowConstraints().clear();
        for (int i = 0; i < cardListData.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/demofx1/VIEW/CardProduct.fxml"));
                AnchorPane pane = loader.load();
                CardProductController controller = loader.getController();
                controller.setData(cardListData.get(i));
                if (col == 3) {
                    col = 0;
                    row += 1;
                }
                menuGridPane.add(pane, col++, row);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // Lọc danh sách sản phẩm theo từ khóa và hiển thị kết quả lọc trong GridPane
    public void filterProductList(String keyword) {
        ObservableList<ProductData> filteredList = FXCollections.observableArrayList();

        for (ProductData product : cardListData) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    String.valueOf(product.getID()).contains(keyword)) {
                filteredList.add(product);
            }
        }

        menuGridPane.getChildren().clear();
        int col = 0;
        int row = 0;
        for (ProductData product : filteredList) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/demofx1/VIEW/CardProduct.fxml"));
                AnchorPane pane = loader.load();
                CardProductController controller = loader.getController();
                controller.setData(product);

                menuGridPane.add(pane, col++, row);
                if (col == 3) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Lấy danh sách sản phẩm từ cơ sở dữ liệu và trả về dưới dạng ObservableList
    public ObservableList<ProductData> productViewListData() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        ObservableList<ProductData> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appmuaban.billproduct";
        try {
            ProductData productData;

            PreparedStatement preparedStatement = connectDatabase.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                productData = new ProductData(result.getInt("ID"), result.getString("NAME"), result.getString("MANUFACTURER"), result.getInt("QUANTITY"), result.getInt("PRICE"));
                productList.add(productData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productList;
    }


    // Hiển thị danh sách sản phẩm trong TableView
    public void showViewProductList() {
        ObservableList<ProductData> show = productViewListData();
        if (nameCartCol == null || qCol == null || pCol == null || cartTable == null) {
            System.err.println("One of the TableColumn or TableView instances is null.");
            return;
        }
        nameCartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        qCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        cartTable.setItems(show);
        cartTable.refresh();
    }


    // Tính tổng giá trị của tất cả sản phẩm trong cơ sở dữ liệu
    public int getTotalPrice() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        int totalPrice = 0;

        String query = "SELECT SUM(PRICE) AS TotalPrice FROM appmuaban.billproduct";

        try {
            Statement statement = connectDatabase.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                totalPrice = resultSet.getInt("TotalPrice");
            }

            resultSet.close();
            statement.close();
            connectDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalPrice;
    }


    // Xóa dữ liệu sản phẩm trong bảng billproduct
    public void clearBillProductData() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();

        String deleteQuery = "DELETE FROM appmuaban.billproduct";

        try {
            Statement statement = connectDatabase.createStatement();
            statement.executeUpdate(deleteQuery);

            statement.close();
            connectDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Tạo một số ngẫu nhiên không trùng lặp cho việc thanh toán
    public int getRandom() {
        Random random = new Random();
        int number;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String check = "SELECT COUNT(1) FROM appmuaban.payments WHERE payment_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(check);
            ResultSet resultSet;
            do {
                number = random.nextInt(900) + 100;
                preparedStatement.setInt(1, number);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
            } while (resultSet.getInt(1) > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return number;
    }


    // Kiểm tra và thực hiện thanh toán, bao gồm cả xác nhận in hóa đơn
    public void setCheckPay() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        int random=getRandom();

        String checkBillProduct = "SELECT COUNT(*) AS total FROM appmuaban.billproduct";
        try {
            PreparedStatement checkStmt = connection.prepareStatement(checkBillProduct);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int total = resultSet.getInt("total");
            resultSet.close();
            checkStmt.close();

            if (total == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Products");
                alert.setContentText("Please add products to the bill before making a payment.");
                alert.show();
                return;
            }
            if(total>0){
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Do you want to print the invoice?");
                confirmationAlert.setContentText("Choose your option.");

                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No and continue");
                ButtonType buttonTypeExit = new ButtonType("Exit");

                confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeExit);

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeYes) {
                        handlePrintAction(random);
                        setPay(random);
                    } else if (response == buttonTypeNo) {
                        setPay(random);
                    } else if (response == buttonTypeExit) {
                        confirmationAlert.close();
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        clearBillProductData();
    }


    // Thêm thông tin thanh toán vào cơ sở dữ liệu
    public void setPay(int random){
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        LocalDateTime date = LocalDateTime.now();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Payment success!");
        alert.setContentText("Please OK to continue!");
        alert.show();
        String pay = "INSERT INTO appmuaban.payments VALUE(?,?,?,?)";
        String user = "Anonymous";
        if (!customerField.getText().isEmpty()) {
            user = customerField.getText();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(pay);
            preparedStatement.setString(1, Integer.toString(getRandom()));
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, Integer.toString(getTotalPrice()));
            preparedStatement.setString(4, String.valueOf(date));
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Xử lý hành động in hóa đơn và lưu dưới dạng tệp PDF
    public void handlePrintAction(int random ) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            GeneratePDF generatePDF = new GeneratePDF();
            try {
                generatePDF.createPDF(file.getAbsolutePath(),random);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Chọn và thay đổi hình ảnh sản phẩm
    public void chooseChangeImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
                changeImageView.setImage(image);
                 urlImage=selectedFile.toURI().toString();
            }

        }


    // Chọn và thêm hình ảnh sản phẩm
    public void chooseAddImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imageUrl = selectedFile.toURI().toString();
            if (!imageUrl.startsWith("file:")) {
                imageUrl = "file:" + imageUrl;
            }
            Image image = new Image(imageUrl);
            addImageView.setImage(image);
            urlImage=imageUrl;
            System.out.println(imageUrl);
        }
    }


    // Chọn và lưu hình ảnh sản phẩm
    public String chooseImageAndSave() {
        if (urlImage != null && !urlImage.isEmpty()) {
            try {
                if (!urlImage.startsWith("file:")) {
                    throw new IllegalArgumentException("Invalid file path format");
                }

                String fileName = new File(URI.create(urlImage)).getName();
                Path sourcePath = Paths.get(new URI(urlImage));
                Path destPath = Paths.get(destDirectoryPath, fileName);

                if (Files.exists(destPath)) {
                    System.out.println("Image already exists at: " + destPath.toUri().toString());
                    return destPath.toUri().toString();
                }

                Files.copy(sourcePath, destPath);
                String copiedImageUrl = destPath.toUri().toString();
                System.out.println("Image saved successfully at: " + copiedImageUrl);
                return copiedImageUrl;
            } catch (Exception e) {
                System.out.println("Error saving image: " + e.getMessage());
            }
        } else {
            System.out.println("No image selected.");
        }
        return null;
    }


    // Lấy danh sách khách hàng từ cơ sở dữ liệu và trả về dưới dạng ObservableList
    public ObservableList<CustomerData> customerListData() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        ObservableList<CustomerData> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appmuaban.customer";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String phoneNumber = resultSet.getString("PNUMBER");
                int totalSpent = getTotalSpentByCustomer(username);
                CustomerData customerData = new CustomerData(username, phoneNumber, totalSpent);
                customerList.add(customerData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }


    // Hiển thị danh sách khách hàng trong TableView
    public void setCustomerTable() {
        ObservableList<CustomerData> showCustomer = customerListData();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        totalSpentCol.setCellValueFactory(new PropertyValueFactory<>("totalSpent"));
        customerTable.setItems(showCustomer);
    }


    // Tính tổng số tiền mà khách hàng đã chi tiêu
    public int getTotalSpentByCustomer(String username) {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        int totalPrice = 0;

        String query = "SELECT SUM(AMOUNT) AS totalSpent FROM appmuaban.payments WHERE  user_id=?";

        try {
            PreparedStatement preparedStatement = connectDatabase.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("totalSpent");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    // Thêm khách hàng mới vào cơ sở dữ liệu
    public void addCustomer() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String name = ADDCusName.getText();
        String PNum = ADDCusPNum.getText();

        if (name.isEmpty() || PNum.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please fill in both name and phone number fields!");
            return;
        }

        String sql = "INSERT INTO appmuaban.customer (USERNAME, PNUMBER) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, PNum);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer Added", "Customer has been added successfully!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCustomerTable();
    }


    // Xóa khách hàng khỏi cơ sở dữ liệu
    public void deleteCustomer() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String name = CusNameDELETE.getText();
        String Pnumber = CusPNumDELETE.getText();

        if (name.isEmpty() && Pnumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please fill in either name or phone number field!");
            return; // Kết thúc phương thức nếu cả hai trường đều trống
        }

        String sql = "";
        if (!name.isEmpty()) {
            sql = "DELETE FROM appmuaban.customer WHERE USERNAME = ?";
        } else {
            sql = "DELETE FROM appmuaban.customer WHERE PNUMBER = ?";
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name.isEmpty() ? Pnumber : name);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer Deleted", "Customer has been deleted successfully!");
                setCustomerTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Thiết lập trạng thái của các trường nhập liệu khi xóa khách hàng
    public void nameOrIDCus() {
        CusNameDELETE.textProperty().addListener((observableValue, s, t1) -> {
            CusPNumDELETE.setDisable(!t1.trim().isEmpty());
            confirmDELETECus.setDisable(t1.trim().isEmpty());
            if (t1.trim().isEmpty()) {
                alertDELETEPro.setText("");
            }
        });

        CusPNumDELETE.textProperty().addListener((observableValue, s, t1) -> {
            CusNameDELETE.setDisable(!t1.trim().isEmpty());
            confirmDELETECus.setDisable(t1.trim().isEmpty());
            if (t1.trim().isEmpty()) {
                alertDELETEPro.setText("");
            }
        });
        confirmDELETECus.setDisable(true);
    }
}






