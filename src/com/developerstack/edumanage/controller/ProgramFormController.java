package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Program;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.model.Teacher;
import com.developerstack.edumanage.view.tm.ProgramTm;
import com.developerstack.edumanage.view.tm.StudentTm;
import com.developerstack.edumanage.view.tm.TechnologyAddTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ProgramFormController {

    public AnchorPane context;

    public TextField txtTechnology;

    public TextField txtName;

    public TextField txtCost;

    public TextField txtSearch;

    public Button btn;

    public TableView<ProgramTm> tblPrograms;

    public TableColumn<?, ?> colCode;

    public TableColumn<?, ?> colProgram;

    public TableColumn<?, ?> colTeacher;

    public TableColumn<?, ?> colTech;

    public TableColumn<?, ?> colCost;

    public TableColumn<?, ?> colOption;

    public TextField txtId;

    public ComboBox<String> cmdTeacher;

    public TableView<TechnologyAddTm> tblTechnologies;

    public TableColumn<?, ?> colTCode;

    public TableColumn<?, ?> colTName;

    public TableColumn<?, ?> colTRemove;

    public void initialize(){

        setProgramCode();
        setTeachers();
        loadProgram();

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colTech.setCellValueFactory(new PropertyValueFactory<>("btnTech"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));


        colTCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    ObservableList<TechnologyAddTm> tmList = FXCollections.observableArrayList();


    ArrayList<String> teachersArray = new ArrayList<>();
    private void setTeachers() {
        // iza veara teacherTm ikku eduththum poda ealum string ikku eduththum poda ealum
        for (Teacher t:Database.teacherTable
             ) {
            teachersArray.add(t.getCode()+". "+t.getName());
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(teachersArray);
        cmdTeacher.setItems(observableList);
    }


    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashboardForm");
    }


    public void newProgramOnAction(ActionEvent event) {

    }


    public void saveOnAction(ActionEvent event) {

        // technologies a poattu save panna oru arrayList or strign array oonum table data va string a maaththi thaan pass pannanum
        String[] selectedTechnologies = new String[tmList.size()]; // izuda length tmList da size thaan varonum  777777
        int pointer = 0;
        for (TechnologyAddTm t: tmList
             ) {
            selectedTechnologies[pointer] = t.getName();
            pointer++; // index maai edukka thaan izu theava
        }

        if(btn.getText().equalsIgnoreCase("Save Program")){
            Program program = new Program(
                    txtId.getText(),
                    txtName.getText(),
                    selectedTechnologies,
                    cmdTeacher.getValue().split("\\.")[0], // teachersArray.add(t.getCode()+". "+t.getName()); izu dot aala  separate aavathaal \\. split sei
                    Double.parseDouble(txtCost.getText()) // constructor keakkuta double value so double value ikku txt a convert panni anuppu
                    // reminder ihina user friendly illa txtCost.getText() izukku user string pass pannina error varum
                    // so ahinekku oru popup varanum enter number only indu

            );
            // System.out.println(student);
            Database.programTable.add(program);
            /*setProgramCode();
            clear();
            setTableData(searchText);*/
            new Alert(Alert.AlertType.INFORMATION,"Program saved!").show();
            loadProgram();
            setProgramCode();
            clear();
        }/*else {
            // long code
            for (Student st:Database.studentTable
            ) {
                if (st.getStudentId().equals(txtId.getText())){
                    st.setFullName(txtName.getText());
                    st.setAddress(txtAddress.getText());
                    st.setDateOfBirth(Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    //  before code the search section
                    // setTableData(); // izukku empty string a pass senja sari
                    // database update aahina izu moonum invoke aahanum
                    // setTableData("");
                    setTableData(searchText); // after 99999
                    clear();
                    setStudentId();
                    btn.setText("Save Student");
                    new Alert(Alert.AlertType.INFORMATION,"Student updated!").show();
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"Not found").show();*/
    }
    private void loadProgram(){
        ObservableList<ProgramTm> programTmObservableList = FXCollections.observableArrayList(); // izula program table ikku poada veadiyazuhal thaan eekkanum
        for (Program p: Database.programTable
             ) {
            Button techButton = new Button("Show tech");
            Button removeButton = new Button("Delete");
            ProgramTm tm = new ProgramTm(
                    p.getCode(),
                    p.getName(),
                    p.getTeacherId(),
                    techButton,
                    p.getCost(),
                    removeButton
            );
            programTmObservableList.add(tm);
        }
        tblPrograms.setItems(programTmObservableList);
    }

    private void setProgramCode() {
        if(!Database.programTable.isEmpty()){ // empty ya ena check pannum
            // not(!) seythaal table ikku value ondu poattirundhaa mattum thaan varu (empty illatti ullukku varum)
            Program lastProgram = Database.programTable.get(Database.programTable.size()-1); // size() -> length
            String lastId = lastProgram.getCode(); // getProgramCode() aal id edukkappattullazu
            String splitData[] = lastId.split("-"); // split senja azu array a thaan tharum so (splitData[] indha) array ikku poattu kolunum
            // split senja ippidi varum ex: [S,3]
            String lastIdIntegerNumberAsAString = splitData[1]; // index 0 , 1
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            // Integer class la static method ullazu parseInt() ena azukku string kuduththal aza int aa maaththi tharum
            lastIntegerIdAsInt++; // 1 koottappattullazu
            String genaratorProgramCode = "S-"+lastIntegerIdAsInt; // aza value ikku S- add seyyappattullazu
            txtId.setText(genaratorProgramCode);
        }else { // empty ya eendha else ikku varum
            txtId.setText("P-1");
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ location +".fxml"))));
        stage.centerOnScreen();
    }

    public void addTechOnAction(ActionEvent actionEvent) {
        if (!isExists(txtTechnology.getText().trim())) {
            Button btn = new Button("Remove");
            TechnologyAddTm tm = new TechnologyAddTm(
                tmList.size()+1,txtTechnology.getText().trim(),btn
            );
            tmList.add(tm);
            tblTechnologies.setItems(tmList);
            txtTechnology.clear();
        } else  {
            txtTechnology.selectAll();
            new Alert(Alert.AlertType.WARNING,"already exists").show();
        }
    }

    private boolean isExists(String tech){
        for (TechnologyAddTm tm:tmList){
            if(tm.getName().equals(tech)){
                return true;
            }
        }
        return false;
    }

    private void clear(){
        txtName.clear();
        txtCost.clear();
        txtTechnology.clear();
    }
}
