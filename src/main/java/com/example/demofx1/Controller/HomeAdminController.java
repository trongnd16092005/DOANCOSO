package com.example.demofx1.Controller;

import com.example.demofx1.Data_Connection.AdminData;
import com.example.demofx1.Data_Connection.DataBaseConnection;
import com.example.demofx1.Data_Connection.PaymentData;
import com.example.demofx1.Data_Connection.ProductData;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class HomeAdminController implements Initializable {
    @FXML
    TableView<PaymentData>totalIncomeTable;
    @FXML
    TableColumn<PaymentData,Integer>idPayment;
    @FXML
    TableColumn<PaymentData,String>IDUserPayment;
    @FXML
    TableColumn<PaymentData,Integer>amountPayment;
    @FXML
    TableColumn<PaymentData,LocalDateTime>datePayment;
    @FXML
    TableView<PaymentData>todayIncomeTable;
    @FXML
    TableColumn<PaymentData,Integer>todayIDPayment;
    @FXML
    TableColumn<PaymentData,String>todayIDUserPayment;
    @FXML
    TableColumn<PaymentData,Integer>todayAmountPayment;
    @FXML
    TableColumn<PaymentData,LocalDateTime>todayDatePayment;
    @FXML
    TableView<ProductData>productTable;
    @FXML
    TableColumn<PaymentData,Integer>idProductCol;
    @FXML
    TableColumn<PaymentData,String>nameProductCol;
    @FXML
    TableColumn<PaymentData,String>manufacturerCol;
    @FXML
    TableColumn<PaymentData,Integer>priceCol;
    @FXML
    TableColumn<PaymentData,String>statusCol;
    @FXML
    Button income;
    @FXML
    Button products;
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
    ComboBox statusCBox;
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
    ComboBox changeStatusCBox;
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
    LineChart<String,Number> lineIncomeChart;
    private final String[] statusString={"Available","Not Available"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIncomeChart();
        setLabelOfIncome();
        setStatus();
        nameOrID();
        lineIncomeChart.setVisible(true);
        totalIncomeTable.setVisible(false);
        todayIncomeTable.setVisible(false);

        incomePane.setVisible(true);
        productsPane.setVisible(false);

        addProPane.setVisible(true);
        deleteProPane.setVisible(false);
    }
    public void setStatus(){
        List<String> listS=new ArrayList<>();
        Collections.addAll(listS,statusString);
        ObservableList listData =FXCollections.observableArrayList(listS);
        statusCBox.setItems(listData);
        changeStatusCBox.setItems(listData);
    }
    public void signOutAcc(ActionEvent event) throws IOException {
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/demofx1/FXML/LoginScreen.fxml"));
        Parent signInView=loader.load();
        Scene scene=new Scene(signInView);
        stage.setY(170);
        stage.setX(350);
        stage.setScene(scene);
    }
    public void setNameAdminHome(AdminData data){
        nameAdminHome.setText(data.getFName());
    }
    public void setIncomeChart(){
        LocalDateTime currentDateTime=LocalDateTime.now();
        int month=currentDateTime.getMonthValue();
        XYChart.Series<String,Number> series=new XYChart.Series<>();
        XYChart.Data<String,Number> fourMAgo=new XYChart.Data<>(changeMonthToString(month-4),getIncomePerM(month-4));
        XYChart.Data<String,Number> threeMAgo=new XYChart.Data<>(changeMonthToString(month-3),getIncomePerM(month-3));
        XYChart.Data<String,Number> twoMAgo=new XYChart.Data<>(changeMonthToString(month-2),getIncomePerM(month-2));
        XYChart.Data<String,Number> oneMAgo=new XYChart.Data<>(changeMonthToString(month-1),getIncomePerM(month-1));
        XYChart.Data<String,Number> nowM=new XYChart.Data<>(changeMonthToString(month),getIncomePerM(month));
        series.getData().addAll(fourMAgo,threeMAgo,twoMAgo,oneMAgo,nowM);
        series.setName("Income per month");
        lineIncomeChart.getData().add(series);
    }
    public String changeMonthToString(int month){
        if(month==-3)
            return "Sep";
        if(month==-2)
            return "Oct";
        if(month==-1)
            return "Nov";
        if(month==0)
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
    public int getIncomePerM(int month){
        LocalDateTime currentDateTime=LocalDateTime.now();
        int year=currentDateTime.getYear();
        if(month<1)
            year-=1;
        if(month==-3)
            month=9;
        if(month==-2)
            month=10;
        if(month==-1)
            month=11;
        if(month==0)
            month=12;
        int totalPerM=0;
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        Connection connection= dataBaseConnection.getConnection();
        String search="SELECT * FROM appmuaban.payments WHERE MONTH(date)="+month;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(search);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                totalPerM+=resultSet.getInt("amount");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return totalPerM;
    }
    public ObservableList<PaymentData> totalPaymentListData(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        ObservableList<PaymentData> paymentList= FXCollections.observableArrayList();
        String sql="SELECT * FROM appmuaban.payments";
        try {
            PaymentData paymentData;

            PreparedStatement preparedStatement=connectDatabase.prepareStatement(sql);
            ResultSet result=preparedStatement.executeQuery();
            while (result.next()){
                java.sql.Timestamp timestamp = result.getTimestamp("date");
                LocalDateTime localDateTime=timestamp.toLocalDateTime();
                paymentData=new PaymentData(result.getInt("payment_id"),result.getString("user_id"),result.getInt("amount"),localDateTime);
                paymentList.add(paymentData);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return paymentList;
    }
    public void showTotalPaymentList(){
        ObservableList<PaymentData>showTotal=totalPaymentListData();
        idPayment.setCellValueFactory(new PropertyValueFactory<>("id"));
        IDUserPayment.setCellValueFactory(new PropertyValueFactory<>("userID"));
        amountPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        datePayment.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalIncomeTable.setItems(showTotal);
        totalIncomeTable.setVisible(true);
        lineIncomeChart.setVisible(false);
        todayIncomeTable.setVisible(false);
    }
    public ObservableList<PaymentData> todayPaymentListData(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        LocalDateTime checkTime=LocalDateTime.now();
        int day=checkTime.getDayOfMonth();
        int month=checkTime.getMonthValue();
        int year= checkTime.getYear();
        ObservableList<PaymentData> paymentList= FXCollections.observableArrayList();
        String sql = "SELECT * FROM appmuaban.payments WHERE YEAR(date)=" + year + " AND MONTH(date)=" + month + " AND DAYOFMONTH(date)=" + day;
        try {
            PaymentData paymentData;

            PreparedStatement preparedStatement=connectDatabase.prepareStatement(sql);
            ResultSet result=preparedStatement.executeQuery();
            while (result.next()){
                java.sql.Timestamp timestamp = result.getTimestamp("date");
                LocalDateTime localDateTime=timestamp.toLocalDateTime();
                paymentData=new PaymentData(result.getInt("payment_id"),result.getString("user_id"),result.getInt("amount"),localDateTime);
                paymentList.add(paymentData);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return paymentList;
    }
    public void showTodayPaymentList(){
        ObservableList<PaymentData>showToday=todayPaymentListData();
        todayIDPayment.setCellValueFactory(new PropertyValueFactory<>("id"));
        todayIDUserPayment.setCellValueFactory(new PropertyValueFactory<>("userID"));
        todayAmountPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        todayDatePayment.setCellValueFactory(new PropertyValueFactory<>("date"));
        todayIncomeTable.setItems(showToday);
        totalIncomeTable.setVisible(false);
        lineIncomeChart.setVisible(false);
        todayIncomeTable.setVisible(true);
    }
    public ObservableList<ProductData> productListData(){
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        Connection connection=dataBaseConnection.getConnection();
        ObservableList<ProductData> productList=FXCollections.observableArrayList();
        String sql= "SELECT * FROM appmuaban.product";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductData productData=new ProductData(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("MANUFACTURER"),resultSet.getInt("PRICE"),resultSet.getString("STATUS") );
                productList.add(productData);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return productList;
    }
    public void setProductTable(){
        ObservableList<ProductData> showProduct=productListData();
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        productTable.setItems(showProduct);
    }
    public void switchPane(ActionEvent event){
        if(event.getSource()==income){
            incomePane.setVisible(true);
            productsPane.setVisible(false);
        }
        if(event.getSource()==products){
            productsPane.setVisible(true);
            incomePane.setVisible(false);
            addProPane.setVisible(true);
            changeProPane.setVisible(false);
            addProBut.setDisable(true);
            deleteProBut.setDisable(false);
            changeProBut.setDisable(false);
            deleteProPane.setVisible(false);
            productTable.getSelectionModel().clearSelection();
            setProductTable();
        }
        if(event.getSource()==addProBut){
            deleteProPane.setVisible(false);
            addProPane.setVisible(true);
            deleteProBut.setDisable(false);
            addProBut.setDisable(true);
            changeProPane.setVisible(false);
            changeProBut.setDisable(false);
            productTable.getSelectionModel().clearSelection();
            clearProductPane();
        }
        if(event.getSource()==deleteProBut){
            deleteProPane.setVisible(true);
            addProPane.setVisible(false);
            deleteProBut.setDisable(true);
            addProBut.setDisable(false);
            changeProPane.setVisible(false);
            changeProBut.setDisable(false);
            productTable.getSelectionModel().clearSelection();
            clearProductPane();
        }
        if(event.getSource()==changeProBut){
            addProPane.setVisible(false);
            deleteProPane.setVisible(false);
            changeProPane.setVisible(true);
            addProBut.setDisable(false);
            deleteProBut.setDisable(false);
            changeProBut.setDisable(true);
            productTable.getSelectionModel().clearSelection();
            clearProductPane();
        }
    }
    public void setVisibleIncomeChart(){
        lineIncomeChart.setVisible(true);
        totalIncomeTable.setVisible(false);
        todayIncomeTable.setVisible(false);
    }
    public void setLabelOfIncome(){
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        Connection connection= dataBaseConnection.getConnection();
        LocalDateTime localDateTime=LocalDateTime.now();
        int day=localDateTime.getDayOfMonth();
        int month=localDateTime.getMonthValue();
        int year= localDateTime.getYear();
        int totalIncome=0;
        int todayIncome=0;
        String total="SELECT * FROM appmuaban.payments";
        String today = "SELECT * FROM appmuaban.payments WHERE YEAR(date)=" + year + " AND MONTH(date)=" + month + " AND DAYOFMONTH(date)=" + day;
        try {
            PreparedStatement todayPreparedStatement= connection.prepareStatement(today);
            PreparedStatement totalPreparedStatement=connection.prepareStatement(total);
            ResultSet todayResultSet=todayPreparedStatement.executeQuery();
            ResultSet totalResultSet=totalPreparedStatement.executeQuery();
            while (todayResultSet.next()){
                todayIncome+=todayResultSet.getInt("amount");
            }
            while (totalResultSet.next()){
                totalIncome+=totalResultSet.getInt("amount");
            }
            todayIncomeLabel.setText(todayIncome+" VND");
            totalIncomeLabel.setText(totalIncome+" VND");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void setAddProduct() {
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        Connection connection=dataBaseConnection.getConnection();
        try {
            if (IDADDText.getText().isEmpty() || nameADDText.getText().isEmpty() || manufacturerText.getText().isEmpty() || priceADDText.getText().isEmpty() || statusCBox.getSelectionModel().isEmpty()) {
                alertADDPro.setText("Please try again!");
            } else {
                String checkQuery = "SELECT COUNT(1) FROM appmuaban.product WHERE ID = ? OR NAME = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, IDADDText.getText());
                    checkStatement.setString(2, nameADDText.getText());
                    ResultSet resultSet = checkStatement.executeQuery();

                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        alertADDPro.setText("Product already exists. Please try again!");
                    } else {
                        String addQuery = "INSERT INTO appmuaban.product(ID, NAME, MANUFACTURER, STATUS, PRICE) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement addStatement = connection.prepareStatement(addQuery)) {
                            addStatement.setString(1, IDADDText.getText());
                            addStatement.setString(2, nameADDText.getText());
                            addStatement.setString(3, manufacturerText.getText());
                            addStatement.setString(4, (String) statusCBox.getValue());
                            addStatement.setString(5, priceADDText.getText());

                            int affectedRows = addStatement.executeUpdate();

                            if (affectedRows > 0) {
                                setProductTable();
                                alertADDPro.setText("ADD product successfully!");
                            } else {
                                alertADDPro.setText("Failed to add product. Please try again!");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
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
                alertDELETEPro.setText("Deleted successfully!");
            } else {
                alertDELETEPro.setText("Please try again!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void nameOrID(){
        nameDELETEText.textProperty().addListener((observableValue, s, t1) -> {
            IDDELETEText.setDisable(!t1.trim().isEmpty());
            confirmDELETEBut.setDisable(t1.trim().isEmpty());
            if(t1.trim().isEmpty())
                alertDELETEPro.setText("");
        });
        IDDELETEText.textProperty().addListener((observableValue, s, t1) -> {
            nameDELETEText.setDisable(!t1.trim().isEmpty());
            confirmDELETEBut.setDisable(t1.trim().isEmpty());
            if(t1.trim().isEmpty())
                alertDELETEPro.setText("");
        });
            confirmDELETEBut.setDisable(true);
    }
    public void clearProductPane(){
        nameADDText.setText("");
        IDADDText.setText("");
        manufacturerText.setText("");
        priceADDText.setText("");
        statusCBox.getSelectionModel().clearSelection();
        statusCBox.setPromptText("Status");
        alertDELETEPro.setText("");

        IDDELETEText.setText("");
        nameDELETEText.setText("");
        alertADDPro.setText("");

        nameChangeText.setText("");
        IDChangeText.setText("");
        changeManufacturerText.setText("");
        priceChangeText.setText("");
        changeStatusCBox.getSelectionModel().clearSelection();
        alertChangePro.setText("");
    }
    public void getValueProduct(){
        ProductData selected=productTable.getSelectionModel().getSelectedItem();
        if(selected!=null){
        IDChangeText.setText(String.valueOf(selected.getID()));
        nameChangeText.setText(selected.getName());
        changeManufacturerText.setText(selected.getManufacturer());
        priceChangeText.setText(String.valueOf(selected.getPrice()));
        changeStatusCBox.setValue(selected.getStatus());
        }else {
            alertChangePro.setText("Please choose again!");
        }
    }
    public void setChangePro(){
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        Connection connection=dataBaseConnection.getConnection();
        String change="UPDATE appmuaban.product SET NAME=?,MANUFACTURER=?,STATUS=?,PRICE=? WHERE ID=?";
        try {
            if(nameChangeText.getText().isEmpty()||IDChangeText.getText().isEmpty()||changeManufacturerText.getText().isEmpty()||changeStatusCBox.getSelectionModel().isEmpty()){
                alertChangePro.setText("Please try again!");
            }else{
                PreparedStatement preparedStatement= connection.prepareStatement(change);
                preparedStatement.setString(1,nameChangeText.getText());
                preparedStatement.setString(2,changeManufacturerText.getText());
                preparedStatement.setString(3,(String) changeStatusCBox.getValue());
                preparedStatement.setString(4,(String) priceChangeText.getText());
                preparedStatement.setString(5,IDChangeText.getText());
                preparedStatement.executeUpdate();
                setProductTable();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
