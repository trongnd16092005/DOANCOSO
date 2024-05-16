    package com.example.demofx1.Controller;


    import com.example.demofx1.Data_Connection.AdminData;
    import com.example.demofx1.Data_Connection.DataBaseConnection;
    import com.example.demofx1.Data_Connection.UserData;
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
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;
    import java.io.IOException;
    import java.net.URL;
    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    import java.util.ResourceBundle;

    public class LoginScreenController implements Initializable {
        @FXML
        RadioButton UserRaButton;
        @FXML
        RadioButton AdminRaButton;
        @FXML
        ToggleGroup toggleGroupUsRa;
        @FXML
        private PasswordField passwordField;
        @FXML
        private TextField usernameField;
        @FXML
        Button loginButton;
        @FXML
        Label loginMassageLabel;
        @FXML
        Label createACC;
        @FXML
        Label forgotPass;
        @FXML
        VBox VBoxLeft;
        @FXML
        AnchorPane loginPane;
        @FXML
        AnchorPane forgotPassPane;
        @FXML
        AnchorPane createPane;
        @FXML
        AnchorPane changePass;
        @FXML
        ComboBox questionForgot;
        @FXML
        TextField userNameForgot;
        @FXML
        TextField Answer;
        @FXML
        Button backForgot;
        @FXML
        Button proceed;
        @FXML
        Label forgotLabel;
        @FXML
        TextField userCreate;
        @FXML
        TextField numberCreate;
        @FXML
        PasswordField passCreate;
        @FXML
        ComboBox questionCreate;
        @FXML
        TextField answerCreate;
        @FXML
        Button backCreate;
        @FXML
        Button createAcc;
        @FXML
        PasswordField changePassword;
        @FXML
        PasswordField changePass2;
        @FXML
        Button bacKChange;
        @FXML
        Label labelCreate;
        @FXML
        Label labelForgot;
        @FXML
        Button proceedChange;
        @FXML
        Label alertChange;
        @FXML
        TextField fNameCreate;
        @FXML
        TextField lNameCreate;

        private final String[] questionList={"What is your favourite food?","What is your favourite color?"};
        public void question(){
            List<String> listQ=new ArrayList<>();
            Collections.addAll(listQ, questionList);
            ObservableList listData= FXCollections.observableArrayList(listQ);
            questionForgot.setItems(listData);
            questionCreate.setItems(listData);
        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle){
            question();
            forgotPassPane.setVisible(false);
            createPane.setVisible(false);
            changePass.setVisible(false);
            loginPane.setVisible(true);
            loginButton.setDisable(true);
            usernameField.textProperty().addListener((observableValue, OldValue, NewValue) -> {
                loginButton.setDisable(NewValue.trim().isEmpty());
            } );
        }
        @FXML
        public void loginButtonOnAction(ActionEvent e){
            if(toggleGroupUsRa.getSelectedToggle()!=null){
                if(!usernameField.getText().isBlank() && !passwordField.getText().isBlank()){
                   if(UserRaButton.isSelected())
                       validateLoginUser(e);
                   else if (AdminRaButton.isSelected())
                       validateLoginAdmin(e);
                }
                else
                    loginMassageLabel.setText("Please enter user name and password.");
                }else
                    loginMassageLabel.setText("Please choose User or Admin.");
            }
        public void validateLoginAdmin(ActionEvent event){
            DataBaseConnection connectNow=new DataBaseConnection();
            Connection connectDatabase=connectNow.getConnection();
            String hexPass =md5(passwordField.getText());
            String verifyLogin="SELECT count(1) FROM loginadmin WHERE ADMINNAME='"+usernameField.getText()+"'AND PASSWORD='"+hexPass+"'";
            try {
                Statement statement=connectDatabase.createStatement();
                ResultSet queryResult=statement.executeQuery(verifyLogin);
                while (queryResult.next()){
                    if (queryResult.getInt(1)==1){
                    changeSceneAdmin(event);
                    }else {
                        loginMassageLabel.setText("Invalid Login. Please try again!");
                    }
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        public void validateLoginUser(ActionEvent event){
            DataBaseConnection connectNow=new DataBaseConnection();
            Connection connectDatabase=connectNow.getConnection();
            String hexPass =md5(passwordField.getText());
            String verifyLogin="SELECT count(1) FROM loginuser WHERE USERNAME='"+usernameField.getText()+"'AND PASSWORD='"+hexPass+"'";
            try {
                Statement statement=connectDatabase.createStatement();
                ResultSet queryResult=statement.executeQuery(verifyLogin);
                while (queryResult.next()){
                    if (queryResult.getInt(1)==1){
                        changeSceneUser(event);
                    }else {
                        loginMassageLabel.setText("Invalid Login. Please try again!");
                    }
                }
            }catch (Exception e){
                throw new RuntimeException(e);

            }
        }
        public void changeSceneAdmin(ActionEvent event) throws IOException {
            Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/demofx1/FXML/HomeAdmin.fxml"));
            Parent homeAdminView=loader.load();
            Scene scene=new Scene(homeAdminView);
            stage.setX(170);
            stage.setY(70);
            stage.setTitle("B.A.M.B.O.O");
            stage.setResizable(false);
            HomeAdminController controller=loader.getController();
            AdminData data=new AdminData(usernameField.getText(),getFName());
            controller.setNameAdminHome(data);
            stage.setScene(scene);
        }
        public void changeSceneUser(ActionEvent event) throws IOException {
            Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/demofx1/FXML/HomeUser.fxml"));
            Parent homeAdminView=loader.load();
            Scene scene=new Scene(homeAdminView);
            stage.setX(170);
            stage.setY(70);
            stage.setTitle("B.A.M.B.O.O");
            stage.setResizable(false);
            HomeUserController controller=loader.getController();
            UserData userData=new UserData(usernameField.getText(),getFName());
            controller.setNameUserHome(userData);
            controller.setResetList();
            stage.setScene(scene);
        }
        public String getFName(){
            DataBaseConnection connectionNow= new DataBaseConnection();
            Connection connection=connectionNow.getConnection();
            String name="Name";
            String search="empty";
            if(AdminRaButton.isSelected()){
                search="SELECT * FROM appmuaban.loginadmin WHERE ADMINNAME='"+usernameField.getText()+"'";
            }
            if(UserRaButton.isSelected()){
                search="SELECT * FROM appmuaban.loginuser WHERE USERNAME='"+usernameField.getText()+"'";
            }
            try {
                PreparedStatement preparedStatement= connection.prepareStatement(search);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                name=resultSet.getString("FIRSTNAME");
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            return name;
        }
        public void switchForm(ActionEvent event){
            if(event.getSource()==backCreate){
                loginPane.setVisible(true);
                createPane.setVisible(false);
                createAcc.setDisable(false);
                userCreate.setText("");
                passCreate.setText("");
                numberCreate.setText("");
                questionCreate.getSelectionModel().clearSelection();
                questionCreate.setPromptText("Choose question");
                answerCreate.setText("");
            }
            if(event.getSource()==backForgot){
                loginPane.setVisible(true);
                forgotPassPane.setVisible(false);
                userNameForgot.setText("");
                Answer.setText("");
                questionForgot.getSelectionModel().clearSelection();
                questionForgot.setPromptText("Choose question");
            }
            if(event.getSource()==proceed){
                    DataBaseConnection connectNow=new DataBaseConnection();
                    Connection connectDatabase=connectNow.getConnection();
                    String verifyPass="SELECT count(1) FROM appmuaban.loginuser WHERE USERNAME=? AND QUESTION=? AND ANSWER=?";
                    try {
                        PreparedStatement preparedStatement=connectDatabase.prepareStatement(verifyPass);
                        preparedStatement.setString(1,userNameForgot.getText());
                        preparedStatement.setString(2,(String) questionForgot.getValue());
                        preparedStatement.setString(3,Answer.getText());
                        ResultSet queryResult=preparedStatement.executeQuery();
                        while (queryResult.next()){
                            if (queryResult.getInt(1)==1){
                                forgotPassPane.setVisible(false);
                                changePass.setVisible(true);
                            }else{

                                labelForgot.setText("Please try again!");
                            }
                        }
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            if(event.getSource()==bacKChange){
                if(proceedChange.isDisable()){
                    changePass.setVisible(false);
                    loginPane.setVisible(true);
                }
                else {
                    changePass.setVisible(false);
                    forgotPassPane.setVisible(true);
                }
            }
            if(event.getSource()==proceedChange){
                DataBaseConnection connectNow=new DataBaseConnection();
                Connection connectDatabase=connectNow.getConnection();
                if("".equals(changePassword.getText()) || changePass2.getText().isEmpty()){
                    alertChange.setText("Please enter all the required information");
                }
                else if(!changePassword.getText().equals(changePass2.getText())){
                    alertChange.setText("Please ensure both passwords are the same.");
                }
                else if(changePassword.getText().equals(changePass2.getText())){
                    String changeP="UPDATE appmuaban.loginuser SET PASSWORD=? WHERE USERNAME=?";
                    try {
                        String hexpass =md5(changePassword.getText());
                        PreparedStatement preparedStatement=connectDatabase.prepareStatement(changeP);
                        preparedStatement.setString(1,hexpass);
                        preparedStatement.setString(2,userNameForgot.getText());
                        int rowAffected=preparedStatement.executeUpdate();
                        if(rowAffected>0){
                            alertChange.setText("Password successfully updated.");
                            proceedChange.setDisable(true);
                        } else {
                            alertChange.setText("Failed to change password.");
                        }
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        public void switchFormCreate(){
                loginPane.setVisible(false);
                createPane.setVisible(true);
        }
        public void switchFormForgot(){
            loginPane.setVisible(false);
            forgotPassPane.setVisible(true);
        }

        public void createAccount() {

            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connectDatabase = connectNow.getConnection();
            String verifyLimit = "SELECT count(1) FROM loginuser WHERE USERNAME='" + userCreate.getText() + "'";
            try {
                Statement statement = connectDatabase.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLimit);
                while (queryResult.next()){
                    if (userCreate.getText()==null||numberCreate.getText()==null||passCreate.getText()==null||questionCreate.getValue()==null||answerCreate.getText()==null){
                        labelCreate.setText("Please enter all the required information");
                    }
                    else  if(queryResult.getInt(1)==1){
                        labelCreate.setText("Please change Username");
                    } else{
                        String hexpass =md5(passCreate.getText());
                        String stringCreateAcc="INSERT INTO appmuaban.loginuser(USERNAME,PASSWORD,PNUMBER,QUESTION,ANSWER,FIRSTNAME,LASTNAME) VALUES(?,?,?,?,?,?,?)";
                        PreparedStatement preparedStatement=connectDatabase.prepareStatement(stringCreateAcc);
                        preparedStatement.setString(1,userCreate.getText());
                        preparedStatement.setString(2,hexpass);
                        preparedStatement.setString(3,numberCreate.getText());
                        preparedStatement.setString(4,(String) questionCreate.getValue());
                        preparedStatement.setString(5,answerCreate.getText());
                        preparedStatement.setString(6,fNameCreate.getText());
                        preparedStatement.setString(7,lNameCreate.getText());
                        int rowAffected=preparedStatement.executeUpdate();
                        if(rowAffected>0){
                            labelCreate.setText("Account created successfully!");
                            createAcc.setDisable(true);
                        } else {
                            labelCreate.setText("Failed to create account.");
                        }
                    }
                }
            }catch (Exception e){
                throw new RuntimeException(e);

            }
        }public static String md5(String input) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = md.digest(input.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
