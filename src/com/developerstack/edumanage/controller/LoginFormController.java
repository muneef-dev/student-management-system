package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.db.DbConnection;
import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void forgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPasswordForm");
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().trim().toLowerCase();
        String password = txtPassword.getText().trim();

        // izu mokka code
        /*for(User user: Database.userTable){
            if(user.getEmail().equals(email)){
                if(user.getPassword().equals(password)){
                    System.out.println(user.toString()); // tostring method i override seyyatti ihina veala seyyazu
                    // to string vaara developer purpose ikkahum azu user class la thaan podanum
                    // user mattum sout la pass pannina object print aahum tostring kuduththa ella data print aahum
                    return; // kattayam return pannanum illlatti loop ikku vealiya pohum jvm
                }else {
                    new Alert(Alert.AlertType.ERROR,"wrong password").show();
                    return; // kattayam return pannanum illlatti loop ikku vealiya pohum jvm
                }
            }
        }
        new Alert(Alert.AlertType.WARNING,String.format("user not foung (%s)",email)).show();*/

        // so think like programmer
        // stream() vandu bata maai and ovvoru record (object) varum pozum filter seizu azula 1st ikku vaara aala
        // pudichchi e variable ikku poattu (appidiye azula vaara ovvoru object eam poattu) equal aandu paaru
        // azu porahu match aahum 1 element i edu, aza null error a illamaakka 1.8 ikku poro vandha
        // Optional class type variable ikku assign senji


        // start - ram la eera database
        /*Optional<User> selectedData = Database.userTable.stream().filter(e->e.getEmail().equals(email)).findFirst();
        if(selectedData.isPresent()){ // andha data eendha user eendha check password
            // if(selectedData.get().getPassword().equals(password)){ // before crypting
            if(new PasswordManager().checkPassword(password,selectedData.get().getPassword())) { // get() vandhu user a thearum
                // selectedData ikku azula eera lamda vaala user select aahi store aahi irundha azu inga
                // get() aala konduvarappattullazu
                // System.out.println(selectedData.get().toString()); // use this to check
                setUi("DashboardForm");
            }else {
                new Alert(Alert.AlertType.ERROR,"wrong password").show();
                // return; inga return theawa illai
            }
        }else { // print this alert
            new Alert(Alert.AlertType.WARNING,String.format("user not foung (%s)",email)).show();
        }*/
        // end - ram la eera database

        // real database
        try{
            User selectedUser = login(email);
            if (null!=selectedUser) {
                if(new PasswordManager().checkPassword(password,selectedUser.getPassword())) { // get() vandhu user a thearum
                    // selectedData ikku azula eera lamda vaala user select aahi store aahi irundha azu inga
                    // get() aala konduvarappattullazu
                    // System.out.println(selectedData.get().toString()); // use this to check
                    setUi("DashboardForm");
                }else {
                    new Alert(Alert.AlertType.ERROR,"wrong password").show();
                    // return; inga return theawa illai
                }
            }else {
                new Alert(Alert.AlertType.WARNING,String.format("user not foung (%s)",email)).show();
            }
        }catch(ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ location +".fxml"))));
        stage.centerOnScreen();
    }

    private User login(String email) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something

        // with statement

        // String sql = "SELECT * FROM user WHERE email='"+email+"'"; // indha query i oththazaha izu hi there'OR '1=1 irukkanum
        // System.out.println(sql);
        // statement i hack seyyalaam that mean bypass hi there'OR '1=1, ena email il kuduththa password wrong aa eendhaaum
        // user a kaattum so use prepared statement azu secure aahum
        // Statement statement = connection.createStatement();
        // statement.executeQuery(sql); // select endraal kaataayamaaha executeQuery aahum
        // izilirundhu data set ondu thaan eduththuttu varum azai list ondula poattukondu varu azu result set aahum
        // ResultSet resultSet = statement.executeQuery(sql); // izil records neraya varum but izula ondu thaan varum

        // with prepared statement
        String sql = "SELECT * FROM user WHERE email=?"; // 121212
        //System.out.println(connection); // checking connetion is different location or something
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,email); // ingu statement ikku data set seyyappattu keelea execute seyyappattullazu
        ResultSet resultSet = statement.executeQuery(); // check the bypass (100'OR '1=1,)
        // prepared statement ill email=? ippidi kodukkappadum prirahu izai change seyya mudiyaazu
        // dynamicaly iza maai injection apply seyya mudiyazu 100'OR '1=1, ivvaru
        // because sql i ealuzi mudinchiii -- go 121212
        // so maruwa sql i edit seyya mudiyaazu vendum endraaal data vei add seyyamudiyum
        // php la vearamaai iza seyyalaam so kavanam

        if (resultSet.next()){ // next eanum method boolean a return seyyum izil iruppazu pointer ondaahum before start
            // eanum option aahum
            // next() method boolean i return seyyum pointer before start ilirundu next method a call senjonna record result set
            // la eendha true return aahum or false return aahum

            /*return new User( // so appidi record eendha inga new user a return seyyum
                return seyya mundhi user object i seyythirundhaal easy so see down
            );*/

            // User user = new User(); // oru user a seyya 4 vidayam kudukkanum so result set la oru node irukkum azil
            // database la ulla colum order la thaan data irukkum (email,fname,lname,password). indha orders ikku number um
            // irukkum (1,2,3,4) so email vendum endraal column name aalum access seyyalaam numbers (index) moolamum access seyyalamm
            // database order alla User class keakkura maaikki kudukkanum

            User user = new User(
                    // column name la access seithal
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
                    // column numbers (index) la access seithal
                    /*resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)*/
            );
            // best option column name type seyya kastam inda use numbers
            System.out.println(user);
            return user; // 2.43.29

        }
        return null;
    }
}
