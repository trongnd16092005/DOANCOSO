package com.example.demofx1.Controller;

import com.example.demofx1.Data_Connection.DataBaseConnection;
import com.example.demofx1.Data_Connection.PaymentData;
import com.example.demofx1.Data_Connection.ProductData;
import com.example.demofx1.Data_Connection.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

public class HomeUserController implements Initializable {
    @FXML
    Button signOut;
    @FXML
    Label nameUserHome;
    @FXML
    TableView<ProductData> viewProductTable;
    @FXML
    TableColumn<ProductData,Integer> IDProduct;
    @FXML
    TableColumn<ProductData,String> nameProduct;
    @FXML
    TableColumn<ProductData,String> manufacturer;
    @FXML
    TableColumn<ProductData,Integer> quantityProduct;
    @FXML
    TableColumn<ProductData,Integer> priceProduct;
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
    ImageView imageView;
    @FXML
    TextField nameText;
    @FXML
    TextField IDText;
    @FXML
    Spinner<Integer> insertQuantity;
    @FXML
    Label alertLabel;
    @FXML
    Button addProduct;
    @FXML
    TextField pricePer1;
    @FXML
    Label totalLabel;
    @FXML
    Button resetList;
    @FXML
    Button paymentButton;
    @FXML
    Button viewCartBut;
    @FXML
    Button showAllBut;


    private String nameForList;
    private  String IDForList;
    private String Manufacturer;
    private int qtySpinner;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpinner();
        nameOrID();
        showViewProductList();
        showProductList();
        setResetList();
        user_id="";
        paymentButton.setDisable(true);
        productTable.setVisible(true);
        viewProductTable.setVisible(false);
        viewCartBut.setVisible(true);
        showAllBut.setVisible(false);
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
    private String user_id="";
    public void setNameUserHome(UserData data){
        nameUserHome.setText(data.getFName());
        user_id= data.getUserName();
    }
    public ObservableList<ProductData> productViewListData(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
       ObservableList<ProductData> productList=FXCollections.observableArrayList();
       String sql="SELECT * FROM appmuaban.billproduct";
       try {
            ProductData productData;

           PreparedStatement preparedStatement=connectDatabase.prepareStatement(sql);
           ResultSet result=preparedStatement.executeQuery();
           while (result.next()){
               productData=new ProductData(result.getInt("ID"),result.getString("NAME"),result.getString("MANUFACTURER"),result.getInt("QUANTITY"), result.getInt("PRICE"));
               productList.add(productData);
           }
       }catch (Exception e){
           throw new RuntimeException(e);
       }
       return productList;
    }
    public void showViewProductList(){
        ObservableList<ProductData>show=productViewListData();
        IDProduct.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        quantityProduct.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        viewProductTable.setItems(show);
    }
    public ObservableList<ProductData> productListData(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        ObservableList<ProductData> productList=FXCollections.observableArrayList();
        String sql="SELECT * FROM appmuaban.product";
        try {
            ProductData productData;

            PreparedStatement preparedStatement=connectDatabase.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                productData=new ProductData(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("MANUFACTURER"),resultSet.getInt("PRICE"),resultSet.getString("STATUS") );
                productList.add(productData);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return productList;
    }
    public void showProductList(){
        ObservableList<ProductData>show=productListData();
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        productTable.setItems(show);
    }
    public ObservableList<ProductData> SearchProductListData(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        ObservableList<ProductData> productList=FXCollections.observableArrayList();
        String sql="SELECT * FROM appmuaban.product WHERE ID=? OR NAME=?";
        try {
            ProductData productData;
            PreparedStatement preparedStatement=connectDatabase.prepareStatement(sql);
            preparedStatement.setString(1,IDText.getText());
            preparedStatement.setString(2,nameText.getText());
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                productData=new ProductData(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("MANUFACTURER"),resultSet.getInt("PRICE"),resultSet.getString("STATUS") );
                productList.add(productData);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return productList;
    }
    public void showSearchProductListData(){
        ObservableList<ProductData>showSearch=SearchProductListData();
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        productTable.setItems(showSearch);
    }
    public void setSpinner(){
        SpinnerValueFactory<Integer> valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10);
        valueFactory.setValue(0);
        insertQuantity.setValueFactory(valueFactory);
        insertQuantity.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
//                int currentValue=insertQuantity.getValue();
            }
        });
    }
        public void nameOrID(){
        nameText.textProperty().addListener((observableValue, s, t1) -> {
            IDText.setDisable(!t1.trim().isEmpty());
        });
        IDText.textProperty().addListener((observableValue, s, t1) -> {
            nameText.setDisable(!t1.trim().isEmpty());
        });
        if(nameText.getText().trim().isEmpty()&&IDText.getText().trim().isEmpty()){
            alertLabel.setText("");
        }
    }
    public void setPriceByName(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        String search="SELECT * FROM appmuaban.product WHERE NAME='"+nameText.getText()+"'";
        try {
            PreparedStatement preparedStatement=connectDatabase.prepareStatement(search);
            ResultSet result=preparedStatement.executeQuery();

            if(result.next()){
                String a=Integer.toString(result.getInt("PRICE"));
                pricePer1.setText(a);
                alertLabel.setText("");
                String b=Integer.toString(result.getInt("ID"));
                IDText.setPromptText(b);
                showSearchProductListData();
            }else{
                pricePer1.setText("0");
                alertLabel.setText("Please try again!");
                IDText.setPromptText("");
                if(nameText.getText().trim().isEmpty()&&IDText.getText().trim().isEmpty()){
                    alertLabel.setText("");
                    pricePer1.setText("0");
                    IDText.setPromptText("");
                    showProductList();
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void setPriceByID(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        String search="SELECT * FROM appmuaban.product WHERE ID="+IDText.getText();
        if (!IDText.getText().trim().isEmpty()){
        try {
            PreparedStatement preparedStatement=connectDatabase.prepareStatement(search);
            ResultSet result=preparedStatement.executeQuery();
            if(result.next()){
                String a=Integer.toString(result.getInt("PRICE"));
                pricePer1.setText(a);
                alertLabel.setText("");
                String b=result.getString("NAME");
                nameText.setPromptText(b);
                showSearchProductListData();
            }else{
                nameText.setPromptText("");
                pricePer1.setText("0");
                alertLabel.setText("Please try again!");
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
            }
        }else{
            if(nameText.getText().trim().isEmpty()&&IDText.getText().trim().isEmpty()){
                alertLabel.setText("");
                pricePer1.setText("0");
                nameText.setPromptText("");
                showProductList();

            }
        }
    }
    public void spinnerValue(){
         qtySpinner=insertQuantity.getValue();
    }
    public String getNameProduct(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        if(nameText.isDisable()) {
            String searchName = "SELECT *FROM appmuaban.product WHERE ID="+IDText.getText();
            if (!IDText.getText().trim().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connectDatabase.prepareStatement(searchName);
                    ResultSet result = preparedStatement.executeQuery();
                    if (result.next()) {
                        nameForList= result.getString("NAME");
                    }
                    else
                        return "null";
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    return  nameForList;
    }
    public String getIDProduct(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        if(IDText.isDisable()) {
            String searchID = "SELECT *FROM appmuaban.product WHERE NAME='" +nameText.getText() +"'";
            if (!nameText.getText().trim().isEmpty()) {
                try {
                    PreparedStatement preparedStatement = connectDatabase.prepareStatement(searchID);
                    ResultSet result = preparedStatement.executeQuery();
                    if(result.next()) {
                        IDForList= Integer.toString(result.getInt("ID"));
                    }
                    else
                        return "null";
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return  IDForList;
    }
    public void getManufacturer(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        String search="";
        if(nameText.isDisable()){
            search="SELECT * FROM appmuaban.product WHERE ID="+IDText.getText();
            try {
                PreparedStatement preparedStatement=connectDatabase.prepareStatement(search);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    Manufacturer= resultSet.getString("MANUFACTURER");
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        if(IDText.isDisable()){
             search="SELECT * FROM appmuaban.product WHERE NAME='"+nameText.getText()+"'";
            try {
                PreparedStatement preparedStatement=connectDatabase.prepareStatement(search);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    Manufacturer= resultSet.getString("MANUFACTURER");
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }
    private  int pricePerProduct;
    private  int total=0;
    public void getPrice(){
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        String search;
        if(nameText.isDisable()){
            search="SELECT * FROM appmuaban.product WHERE ID="+IDText.getText();
            try {
                PreparedStatement preparedStatement=connectDatabase.prepareStatement(search);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    pricePerProduct= resultSet.getInt("PRICE");
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        if(IDText.isDisable()){
            search="SELECT * FROM appmuaban.product WHERE NAME='"+nameText.getText()+"'";
            try {
                PreparedStatement preparedStatement=connectDatabase.prepareStatement(search);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    pricePerProduct= resultSet.getInt("PRICE");
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }
    public void setLabelTotal(){
        int x=pricePerProduct*qtySpinner;
        total+=x;
        totalLabel.setText(total+" VND");
    }
    public void setAddProduct(){
        String checkName=getNameProduct();
        String checkID=getIDProduct();
        setPriceByName();
        setPriceByID();
        spinnerValue();
        getManufacturer();
        getPrice();
        setLabelTotal();
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDatabase= connectNow.getConnection();
        String addList="INSERT INTO appmuaban.billproduct VALUE(?,?,?,?,?)";
        if(qtySpinner==0){
            alertLabel.setText("Please try again!");
        }else{
            try {
                paymentButton.setDisable(false);
                PreparedStatement preparedStatement=connectDatabase.prepareStatement(addList);
                if(nameText.isDisable()){
                    if("null".equals(checkName))
                        alertLabel.setText("Please try again!");
                    else {
                        paymentButton.setDisable(false);
                        preparedStatement.setString(1,IDText.getText());
                        preparedStatement.setString(2,getNameProduct());
                        preparedStatement.setString(3,Manufacturer);
                        preparedStatement.setString(4,Integer.toString(qtySpinner));
                        preparedStatement.setString(5,Integer.toString(pricePerProduct));
                        preparedStatement.executeUpdate();
                        showViewProductList();
                    }
                }
                if(IDText.isDisable()){
                    if("null".equals(checkID))
                        alertLabel.setText("Please try again!");
                    else {
                        paymentButton.setDisable(false);
                        preparedStatement.setString(1,getIDProduct());
                        preparedStatement.setString(2,nameText.getText());
                        preparedStatement.setString(3,Manufacturer);
                        preparedStatement.setString(4,Integer.toString(qtySpinner));
                        preparedStatement.setString(5,Integer.toString(pricePerProduct));
                        preparedStatement.executeUpdate();
                        showViewProductList();}
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    public void setResetList(){
        addProduct.setDisable(false);
       DataBaseConnection connection=new DataBaseConnection();
       Connection connectionNow=connection.getConnection();
       String reset= "DELETE FROM appmuaban.billproduct";
       try {
           PreparedStatement preparedStatement= connectionNow.prepareStatement(reset);
           preparedStatement.executeUpdate();
           showProductList();
           showViewProductList();
           IDText.setText("");
           nameText.setText("");
           IDText.setPromptText("");
           nameText.setPromptText("");
           alertLabel.setText("");
           pricePer1.setText("0");
           total=0;
           pricePerProduct=0;
           totalLabel.setText(total+" VND");
           setSpinner();
       }catch (Exception e){
           throw new RuntimeException(e);
       }

    }
    public int getRandom(){
        Random random=new Random();
        int number=random.nextInt(900)+100;
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        Connection connection=dataBaseConnection.getConnection();
        String check="SELECT COUNT(1) FROM appmuaban.payments WHERE payment_id="+number;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(check);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                if(resultSet.getInt(1)>0){
                    getRandom();
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return number;
    }
    public void setPay(){
        DataBaseConnection dataBaseConnection= new DataBaseConnection();
        Connection connection= dataBaseConnection.getConnection();
        LocalDateTime date=LocalDateTime.now();
        addProduct.setDisable(true);
        paymentButton.setDisable(true);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Payment success!");
        alert.setContentText("Please RESET to continue!");
        alert.show();
        String pay="INSERT INTO appmuaban.payments VALUE(?,?,?,?)";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(pay);
            preparedStatement.setString(1,Integer.toString(getRandom()));
            preparedStatement.setString(2,user_id);
            preparedStatement.setString(3,Integer.toString(total));
            preparedStatement.setString(4,String.valueOf(date));
            preparedStatement.executeUpdate();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void changeTable(ActionEvent event){
        if(event.getSource()==showAllBut){
            viewProductTable.setVisible(false);
            productTable.setVisible(true);
            showAllBut.setVisible(false);
            viewCartBut.setVisible(true);
        }
        if (event.getSource()==viewCartBut){
            viewProductTable.setVisible(true);
            productTable.setVisible(false);
            viewCartBut.setVisible(false);
            showAllBut.setVisible(true);
        }
    }
}
