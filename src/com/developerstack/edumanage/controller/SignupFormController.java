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

import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class SignupFormController {
    public AnchorPane context;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;

    public PasswordField txtPassword;



    public void areadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText().trim().toLowerCase();

        // String password = txtPassword.getText().trim(); // use this to check before crypting
        String password = new PasswordManager().encrypt(txtPassword.getText().trim());

        // start - ram la eera database
        // Database.userTable.add(new User(firstName,lastName,email,password));
        // new Alert(Alert.AlertType.INFORMATION,"Welcome").show();
        // setUi("LoginForm");
        // end - ram la eera database


        // creating new user
        User createUser = new User(firstName, lastName, email, password);
        // izu la ippa output varum boolean aza boolean variable ondukku poattu kolra

        // signup() call seyyappadum idaththil thaan error handle seyyappadanum so ingu try catch a podanum
        try {
            boolean isSaved = signup(createUser);
            // signup() izu kku anonymous object a pass seyyalaam azu fast aahum
            // boolean isSaved = signup(new User(firstName, lastName, email, password)); // use this in future
            if (isSaved) { // true inda iza popup sei
                new Alert(Alert.AlertType.INFORMATION, "Welcome").show();
                setUi("LoginForm");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }

    //============================
    // iza mehtod a public panna theawalla because iza veara edaththula eendhu access seyrulla so private sei
    // indha mehtod signUpOnAction eanum method il thaan call seyyappadum so andha button i press seythonne
    // signUpOnAction trigger aahunonna azil indha signup() invoke seyyappadum andha text fields in values
    // i eduththu andha values i signup() method ikku send seyyappadum so izula eendha save aahina illaya
    // ena (true or false) boolean aaha vealiya output varum so aza boolean a detect senji save aahina illayaandu check seyyalaam
    // so ye parameters ivalo eduththeera ? azukku pazil direct a object pass seyya ealume? aze lease
    // object a pass seyrathaala multiple return seyya ealum (oru veala user save aahina poro
    // save aahina user da details aza method aala thara selli keatta multiple data return seyya mudiyazu so kattayama
    // object use panni aahanum)
    // text fields 20 eendha parameter periya line ondukke pohum so object a anuppurazu leasi manage panna leasi data loss aahurazu koravu

    /*private boolean signup(String email, String firstName, String lastName, String password){
        // save
    }*/

    // so user da constructor a call senji azukku details a kuduththu uruvaakku sinna object ondu andha
    // object i indha signup method ikku anuppu

    // izukku throws ClassNotFoundException, SQLException directa iza kudukka ealum
    // throws Exception ena kudukkalaam but u have to maintain a standard because
    // end point ill indha method i call seiravar vealaggi kola oonum enna exceptions ean vaarandu
    private boolean signup(User user) throws ClassNotFoundException, SQLException { // models senjazu database il save seyyavaahum
        // saving user into database

        // inga try catch use pannalaam but use pannina fail so dont do
        // because indha mehtod call seyyappaduvazu veara oru edaththilirundhaahum so avadaththukku propagate seyyanum

        // without simplification
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc.mysql://localhost:3306/lms3","root","1234");
        String sql = "INSERT INTO user VALUES ('"+user.getEmail()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getPassword()+"')";
        Statement statement = connection.createStatement();
        int rowCount = statement.executeUpdate(sql);
        if (rowCount>0){
            return true;
        }
        return false;*/


        // 1 load the driver
        // Class.forName("com.mysql.cj.jdbc.Driver"); // old version -> com.mysql.jdbc.Driver (deprecate)
        // iza driver a ram ikku load seyyakola exception ondu varum so aza ingu try seivathillai throw thaann seyyanum

        // 2 create connection  (like pipe or bridge)
        // Connection connection = DriverManager.getConnection("jdbc.mysql://localhost:3306/lms3","root","1234");
        // localhost = 127.0.0.1 izem pass seyyalaam
        // DriverManager enbazu mysql udan deel seyya theawayyana tools i veiththirukkum java class/app/software aahum
        // connection object i return seyyum so kayyaam sql connection a thaan select pannanum

        // 3 write sql query
        // connection i seithavudan query i ealuthanum because sql veala seira query aal aahum
        // String sql = "INSERT INTO user VALUES ('"+user.getEmail()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getPassword()+"')"; // '" + write here + "'
        // kattayam database la eera fields (colums) order ikku thaan inga
        // eendhu values a anuppanum model ill ulla order alla reminder!!!!!!!!!!!

        // 4 create statement
        // statement enbazu object aahum izu java vil irundhu mysql ikku anuppa vendiya command/function aahum
        // azul sql query um include aahum statement mysql ill trigger aahum
        // Statement statement = connection.createStatement();

        // 5 set sql into the statement and execute
        // int rowCount = statement.executeUpdate(sql);
        // executeUpdate() indha method tharuwazu int value ondaahum azu raw count aahum
        // executeUpdate() izu execute aahinonna azu database ikku peiththu eathna raws ikku effect aanazu ena sollum
        // 1 raw effect aanal 1 ena varum 2 inda 2 ena varum and data save aanaal kattayamaaha 1 sari count varum

        // String sql = "INSERT INTO user VALUES (?,?,?,?)";
        // prepared statement ikku sql thaan pass seira but mela ullla user.getEmail() iza maai static data pass seyyappada maattazu
        // prepared statement ill static data allamal dynamic data thaan kodukkappadum (?,?,?,?) ena
        // PreparedStatement statement = connection.prepareStatement(sql); // inga type Statement ena kuduththalum pila varazu because
        // azula eedhu thaan prepared statement inherit aahi eera
        // so ippa satement value 4 set seyyanum
        /*statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());*/
        // return statement.executeUpdate()>0; // inga sql pass seyyappada maattazu

        /*if (rowCount>0){
            return true; // kattayamaaha ivvidaththil 0 vida koodava eendha true varum so save aahindu arththam
        }
        return false;*/


        // with simplification 1
        // mysql jar connector i project file ulle podanum illatti class not found error varum becasue tools illama connect seuua eala

        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3","root","1234");
        String sql = "INSERT INTO user VALUES ('"+user.getEmail()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getPassword()+"')";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql)>0;*/ // 3  // insert , update , delete seythaal executeUpdate method thaan use aahum

        // it means execute update thaan execute aahum
        /*int rowCount = statement.executeUpdate(sql);
        // return rowCount>0?true:false; // 1
        return rowCount>0; // indha expression ill direct aa boolean thaan return aahum more simplyfied // 2 */



        // with prepared statement
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3","root","1234");
        System.out.println(connection); // checking connetion is different location or something*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something


        String sql = "INSERT INTO user VALUES (?,?,?,?)";
        // prepared statement ikku sql thaan pass seira but mela ullla user.getEmail() iza maai static data pass seyyappada maattazu
        // prepared statement ill static data allamal dynamic data thaan kodukkappadum (?,?,?,?) ena
        PreparedStatement statement = connection.prepareStatement(sql); // inga type Statement ena kuduththalum pila varazu because
        // azula eedhu thaan prepared statement inherit aahi eera
        // so ippa satement value 4 set seyyanum
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        return statement.executeUpdate()>0; // inga sql pass seyyappada maattazu
    }
}
