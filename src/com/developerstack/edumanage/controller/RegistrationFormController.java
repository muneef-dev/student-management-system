package com.developerstack.edumanage.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class RegistrationFormController {

    public AnchorPane context;

    public TextField txtId;

    public TextField txtStudent;

    public Button btn;

    public ComboBox<?> cmbProgram;

    public ToggleGroup payment;
    public RadioButton rBtnPaid;
    public RadioButton rBtnPending;

    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent event) {

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}