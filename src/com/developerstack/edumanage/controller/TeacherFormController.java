package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Teacher;
import com.developerstack.edumanage.view.tm.TeacherTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Observable;
import java.util.Optional;

public class TeacherFormController {
    public AnchorPane teacherContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtContact;

    public Button btn;
    public TableView<TeacherTm> tblTeachers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colOption;

    String searchText = "";

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setTeacherId();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
            searchText = newValue;
            setTableData(searchText);
        });

        tblTeachers.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            if (null!=newValue){
                setData(newValue);
            }
        });
    }

    private void setData(TeacherTm tm) {
        txtId.setText(tm.getCode());
        txtName.setText(tm.getName());
        txtContact.setText(tm.getContact());
        txtAddress.setText(tm.getAddress());
        btn.setText("Update Teacher");
    }

    private void setTableData(String searchText) {
        ObservableList observableList = FXCollections.observableArrayList();
        for (Teacher tr:Database.teacherTable
             ) {
            if (tr.getName().contains(searchText)){
                Button btn = new Button("Delete");
                TeacherTm tm = new TeacherTm(
                        tr.getCode(), tr.getName(), tr.getContact(), tr.getAddress(), btn
                );

                btn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"are you sure?",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)){
                        Database.teacherTable.remove(tr);
                        new Alert(Alert.AlertType.INFORMATION,"deleted!").show();
                        setTableData(searchText);
                        setTeacherId();
                    }
                });
                observableList.add(tm);
            }
        }
        tblTeachers.setItems(observableList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) teacherContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
        btn.setText("Save Teacher");
        setTeacherId();
        clear();
        tblTeachers.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            if (null!=newValue){
                setData(newValue);
            }
        });
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (btn.getText().equalsIgnoreCase("Save Teacher")){
            Teacher teacher = new Teacher(
                    txtId.getText(),txtName.getText(),txtContact.getText(),txtAddress.getText()
            );
            Database.teacherTable.add(teacher);
            setTeacherId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION,"Teacher saved!").show();
        } else {
            for (Teacher tr:Database.teacherTable){
                if (tr.getCode().equals(txtId.getText())){
                    tr.setName(txtName.getText());
                    tr.setContact(txtContact.getText());
                    tr.setAddress(txtAddress.getText());
                    clear();
                    setTeacherId();
                    setTableData(searchText);
                    btn.setText("Save Teacher");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"not found").show();
        }

    }

    private void setTeacherId() {
        if (!Database.teacherTable.isEmpty()){
           Teacher lastTeacher = Database.teacherTable.get(Database.teacherTable.size()-1);
           String lastId = lastTeacher.getCode();
           String splitData[] = lastId.split("-");
           String lastIdIntegerNumberAsAString = splitData[1];
           int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
           lastIntegerIdAsInt++;
           String generatedTeacherId = "T-"+lastIntegerIdAsInt;
           txtId.setText(generatedTeacherId);
        }else {
            txtId.setText("T-1");
        }
    }

    private void clear(){
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
    }
}
