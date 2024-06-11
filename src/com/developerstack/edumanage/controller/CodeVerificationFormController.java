package com.developerstack.edumanage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CodeVerificationFormController {
    public AnchorPane context;
    public TextField txtCode;
    public Label txtSelectedEmail;

    int code = 0;
    String selectedEmail = "";
    public void setUserData(int verificationCode, String email){
        // System.out.println(verificationCode);
        // System.out.println(email);
        code = verificationCode;
        selectedEmail = email;
        System.out.println(code);
        txtSelectedEmail.setText(email);
    }
    public void veryfiCodeOnAction(ActionEvent actionEvent) throws IOException {
        // izukku ForgotPasswordFormController page ilirundhu geneate aahum varification code indha page ikku vara code pannanum
        // important
        if (String.valueOf(code).equals(txtCode.getText())){ // code ikku vaarazu int aahum so aza string ikku valueOf() aala maaththeekku to more safe
            // navigate reset password
            // System.out.println("reset password form");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ResetPasswordForm.fxml"));
            Parent parent = fxmlLoader.load();
            ResetPasswordFormController controller = fxmlLoader.getController();
            controller.setUserData(selectedEmail);
            Stage  stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(parent));
        } else {
            new Alert(Alert.AlertType.ERROR,"Wrong verifycation code").show();
        }
    }

    public void changeEmailOnAction(ActionEvent actionEvent) throws IOException {
            setUi("ForgotPasswordForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ location +".fxml"))));
        stage.centerOnScreen();
    }
}
