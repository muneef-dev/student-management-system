package com.developerstack.edumanage.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane context;
    public Label lblDate;
    public Label lblTime;

    // ui varum poze date and time varanum so initilize() la thaan podanum
    public void initialize(){
        setData();
    }

    private void setData() {
        // long code
        /*Date date = new Date(); // Date class util la eeraza edu not the sql pakage
        // System.out.println(date); iza data nalla format alla
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // mm -> minutes MM -> month a kurichchum
        String textDate = dateFormat.format(date);
        lblDate.setText(textDate);*/

        // best practise
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        // lblTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date())); izu sari but static time aahum so
        // ==== dont think code it is in the internet
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e -> {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                            lblTime.setText(LocalTime.now().format(dateTimeFormatter));
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ location +".fxml"))));
        stage.centerOnScreen();
    }

    public void openStudentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StudentForm");
    }

    public void openTeacherFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("TeacherForm");
    }

    public void openProgramFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProgramForm");
    }

    public void openIntakeFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IntakeForm");
    }

    public void openRegistrationFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RegistrationForm");
    }
}
