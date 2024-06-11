package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.db.DbConnection;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.view.tm.StudentTm;
import com.sun.deploy.util.FXLoader;
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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public DatePicker txtDob;
    public TextField txtAddress;
    public TableView<StudentTm> tblStudents; // type safe seyyanum
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colOption;

    public Button btn;
    public TextField txtSearch;
    String searchText = ""; // 999999
    // so empty string a assign panni aza pass senja sari

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setStudentId();
        //  before code the search section
        // setTableData();
        // setTableData(""); // izukku empty string a pass senja sari
        setTableData(searchText); // after 99999
        // nearaya eadaththula empty string pass panneera bur iza mura nallamilla
        // because nimal,samantha,nimali ena 3 data eera time nimal a search panni alichcha
        // search box la nimal indu eekum azoda samantha um load aahum aza empty string a pass seyrathaala thats a problem
        // so solution is go 999999

        // update scene
        // use listener

        // search section
       txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
           searchText = newValue; // inga aza reassign panni eera to identify listener
           setTableData(searchText); // data load seyya veara table theavalla already eeraze use seyyalaam
       });

       tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           // setData vinul eazaavazu modification aahinaal newValue eanum record setData eanum method ikku pass
           // seyyum azukku pass aakkum poazu azu assign senji kondeekkum value null aahum naam eazaavazu change ondu
           // senjonne so setData(newValue); izu mattum iza method la eendha NullPointerException paayum
           // so if condition ullukku izu varanum illatti NullPointerException paayum

           // setData(newValue); // indha method call aahum new value oda (iza line mattum eendha error)

           if(null != newValue){// day-28 or javaFx 05 -> 2h.23m.0
               setData(newValue); // indha method call aahum new value oda
           } // arththam null aa eendha skip sei

       });

    }

    private void setData(StudentTm tm) { // newValue -> tm
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtDob.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txtAddress.setText(tm.getAddress());
        btn.setText("Update Student");
    }

    // with ram database
    /*private void setTableData(String searchText) {
        ObservableList<StudentTm> observableList = FXCollections.observableArrayList();
        for (Student st:Database.studentTable
             ) {
            if (st.getFullName().contains(searchText)){
                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(
                        st.getStudentId(),st.getFullName(),new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),st.getAddress(),btn
                        // iza st.getDateOfBirth() ivadathula direct aa kudukka eala izu date type but studenttm keakkura
                        // String type so string type data thaan inga varanum
                );
                btn.setOnAction(e ->{
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.get().equals(ButtonType.YES)){
                        Database.studentTable.remove(st);
                        new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                        // setTableData(""); // izukku empty string a pass senja sari
                        setTableData(searchText); // after 99999
                        setStudentId();
                        clear(); // izu naan poattazu naan kandu pudichcha error
                    }
                });
                observableList.add(tm);
            }

        }
        tblStudents.setItems(observableList);
    }*/

    // real database
    private void setTableData(String searchText) {
        ObservableList<StudentTm> observableList = FXCollections.observableArrayList();
        try {
            for (Student st:searchStudent(searchText)
            ) {
                /*if (st.getFullName().contains(searchText)){*/ // database le filter panni anuppurazaala izu theawalla
                    Button btn = new Button("Delete");
                    StudentTm tm = new StudentTm(
                            st.getStudentId(),st.getFullName(),new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),st.getAddress(),btn
                            // iza st.getDateOfBirth() ivadathula direct aa kudukka eala izu date type but studenttm keakkura
                            // String type so string type data thaan inga varanum
                    );
                    btn.setOnAction(e ->{
                        Alert alert = new Alert(
                                Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO
                        );
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if(buttonType.get().equals(ButtonType.YES)){
                            // ram database
                            /*Database.studentTable.remove(st);
                            new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                            // setTableData(""); // izukku empty string a pass senja sari
                            setTableData(searchText); // after 99999
                            setStudentId();
                            clear(); // izu naan poattazu naan kandu pudichcha error*/

                            // real database
                            try {
                                if (deleteStudent(st.getStudentId())){
                                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                                    setTableData(searchText);
                                    setStudentId();
                                    clear();
                                }else {
                                    new Alert(Alert.AlertType.WARNING,"Try again").show();
                                }
                            } catch (SQLException | ClassNotFoundException ex) {
                                new Alert(Alert.AlertType.ERROR,e.toString()).show();
                            }

                        }
                    });
                    observableList.add(tm);
                }
            tblStudents.setItems(observableList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    //  before code the search section
    /*private void setTableData() {
        ObservableList<StudentTm> observableList = FXCollections.observableArrayList();
        for (Student st:Database.studentTable
             ) {
            Button btn = new Button("Delete");
            StudentTm tm = new StudentTm(
                    st.getStudentId(),st.getFullName(),new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),st.getAddress(),btn
                    // iza st.getDateOfBirth() ivadathula direct aa kudukka eala izu date type but studenttm keakkura
                    // String type so string type data thaan inga varanum
            );
            btn.setOnAction(e ->{
                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO
                );
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get().equals(ButtonType.YES)){
                    Database.studentTable.remove(st);
                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                    setTableData(); // izukku empty string a pass senja sari
                    setStudentId();
                }
            });
            observableList.add(tm);
        }
        tblStudents.setItems(observableList);
    }*/

    private void setStudentId() {
        // ram database start
        /*if(!Database.studentTable.isEmpty()){ // empty ya ena check pannum
            // not(!) seythaal table ikku value ondu poattirundhaa mattum thaan varu (empty illatti ullukku varum)
            Student lastStudent = Database.studentTable.get(Database.studentTable.size()-1); // size() -> length
            String lastId = lastStudent.getStudentId(); // getStudentId() aal id edukkappattullazu
            String splitData[] = lastId.split("-"); // split senja azu array a thaan tharum so (splitData[] indha) array ikku poattu kolunum
            // split senja ippidi varum ex: [S,3]
            String lastIdIntegerNumberAsAString = splitData[1]; // index 0 , 1
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            // Integer class la static method ullazu parseInt() ena azukku string kuduththal aza int aa maaththi tharum
            lastIntegerIdAsInt++; // 1 koottappattullazu
            String genaratorStudentId = "S-"+lastIntegerIdAsInt; // aza value ikku S- add seyyappattullazu
            txtId.setText(genaratorStudentId);
        }else { // empty ya eendha else ikku varum
            txtId.setText("S-1");
        }*/
        // ram database end

        // in the direct database
        try {
            String lastId = getLastId();
            if (null!=lastId){
                String splitData[] = lastId.split("-"); // split senja azu array a thaan tharum so (splitData[] indha) array ikku poattu kolunum
                // split senja ippidi varum ex: [S,3]
                String lastIdIntegerNumberAsAString = splitData[1]; // index 0 , 1
                int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
                // Integer class la static method ullazu parseInt() ena azukku string kuduththal aza int aa maaththi tharum
                lastIntegerIdAsInt++; // 1 koottappattullazu
                String genaratorStudentId = "S-"+lastIntegerIdAsInt; // aza value ikku S- add seyyappattullazu
                txtId.setText(genaratorStudentId);
            }else { // empty ya eendha else ikku varum
                txtId.setText("S-1");
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // start - ram la eera database
    /*public void saveOnAction(ActionEvent actionEvent) {
        if(btn.getText().equals("Save Student")){
            Student student = new Student( // dob vandhu date type sutti getValue thaan varum and azu local date keakkum
                    // so anonymous object la pass  panni (date a eduththu) toEpochDay aala long ikku convert seyyappattullazu
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );
            // System.out.println(student);
            Database.studentTable.add(student);
            setStudentId(); // iza call pannatti puziya id genarate aahazu iza comment pannitti check pannu vealaggum
            clear(); // ellam clear seyya
            //  before code the search section
            // setTableData(); // new student a save panninonna table a refresh seida
            // setTableData("");// izukku empty string a pass senja sari)
            setTableData(searchText); // after 99999
            new Alert(Alert.AlertType.INFORMATION,"Student saved!").show();
        }else {
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
            new Alert(Alert.AlertType.WARNING,"Not found").show();

            *//*Optional<Student> selectedStudent = Database.studentTable.stream().filter(e->e.getStudentId().equals(txtId.getText())).findFirst();
            if(!selectedStudent.isPresent()){
                new Alert(Alert.AlertType.WARNING,"Not found").show();
                return;
            }
            selectedStudent.get().setAddress();*//*
        }

    }*/
    // end - ram la eera database

    // real database
    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student( // dob vandhu date type sutti getValue thaan varum and azu local date keakkum
                // so anonymous object la pass  panni (date a eduththu) toEpochDay aala long ikku convert seyyappattullazu
                txtId.getText(),
                txtName.getText(),
                Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                txtAddress.getText()
        ); // iza if ikku vealiya poatta method ullukku ella edaththulem access seyya
        if(btn.getText().equals("Save Student")){
            /*Student student = new Student( // dob vandhu date type sutti getValue thaan varum and azu local date keakkum
                    // so anonymous object la pass  panni (date a eduththu) toEpochDay aala long ikku convert seyyappattullazu
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );*/

            try {
                if (saveStudent(student)){
                    // Database.studentTable.add(student); theawalla ihina theawalla
                    setStudentId(); // iza call pannatti puziya id genarate aahazu iza comment pannitti check pannu vealaggum
                    clear();
                    setTableData("");// izukku empty string a pass senja sari)
                    // setTableData(searchText); // after 99999 ihina theawalla
                    new Alert(Alert.AlertType.INFORMATION,"Student saved!").show();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try again!!").show();
                }
            } catch (ClassNotFoundException | SQLException e){
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }

            // ram database start
            // System.out.println(student);
           /* Database.studentTable.add(student);
            setStudentId(); // iza call pannatti puziya id genarate aahazu iza comment pannitti check pannu vealaggum
            clear();*/ // ellam clear seyya
            //  before code the search section
            // setTableData(); // new student a save panninonna table a refresh seida
            // setTableData("");// izukku empty string a pass senja sari)
            // setTableData(searchText); // after 99999
            // new Alert(Alert.AlertType.INFORMATION,"Student saved!").show();
            // ram database end

        }else {
            // long code
            // ram database
            /*for (Student st:Database.studentTable
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

            // real database
            try {
                if (updateStudent(student)){
                    setStudentId(); // iza call pannatti puziya id genarate aahazu iza comment pannitti check pannu vealaggum
                    clear();
                    //setTableData(searchText);// izukku empty string a pass senja sari)
                    setTableData("");// izukku empty string a pass senja sari)
                    // setTableData(searchText); // after 99999 ihina theawalla
                    new Alert(Alert.AlertType.INFORMATION,"Student updated!").show();
                    btn.setText("Save Student");
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try again up!!").show();
                }
            } catch (ClassNotFoundException | SQLException e){
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }


            /*Optional<Student> selectedStudent = Database.studentTable.stream().filter(e->e.getStudentId().equals(txtId.getText())).findFirst();
            if(!selectedStudent.isPresent()){
                new Alert(Alert.AlertType.WARNING,"Not found").show();
                return;
            }
            selectedStudent.get().setAddress();*/
        }

    }
    private void clear(){
        //txtId.clear(); // id clear pannina azu pila noob
        txtName.clear();
        txtAddress.clear();
        txtDob.setValue(null); // izu date type enbazaal value a null sei
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clear();
        setStudentId();
        btn.setText("Save Student");

        // izu naan poattazu sir da mistake aahichchi
        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // setData vinul eazaavazu modification aahinaal newValue eanum record setData eanum method ikku pass
            // seyyum azukku pass aakkum poazu azu assign senji kondeekkum value null aahum naam eazaavazu change ondu
            // senjonne so setData(newValue); izu mattum iza method la eendha NullPointerException paayum
            // so if condition ullukku izu varanum illatti NullPointerException paayum

            // setData(newValue); // indha method call aahum new value oda (iza line mattum eendha error)

            if(null != newValue){// day-28 or javaFx 05 -> 2h.23m.0
                setData(newValue); // indha method call aahum new value oda
            } // arththam null aa eendha skip sei

        });
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    private boolean saveStudent(Student student) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something

        // String sql = "INSERT INTO student VALUES(?,?,?,?)";
        // PreparedStatement statement = connection.prepareStatement(sql);
        // sql i direct aavum kudukkalaam
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(?,?,?,?)");
        // vendiya data type i set seyya mudiyum
        preparedStatement.setString(1, student.getStudentId());
        preparedStatement.setString(2, student.getFullName());
        preparedStatement.setObject(3, student.getDateOfBirth());
        // object ikku veandiya value poda ealum indrazaala setObject part ikku indha date i poattukkollalaam student.getDateOfBirth()
        preparedStatement.setString(4, student.getAddress());
        return preparedStatement.executeUpdate()<0;
    }

    private String getLastId() throws SQLException, ClassNotFoundException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT student_id FROM student ORDER BY CAST(SUBSTRING(student_id,3) AS UNSIGNED) DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    private List<Student> searchStudent(String text) throws ClassNotFoundException, SQLException {
        text = "%" + text + "%"; // %text%
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE full_name LIKE ? OR address LIKE ?");
        // meala query la 2 ?,? eeera azu 2 ikkum value kudukkanum
        preparedStatement.setString(1,text);
        preparedStatement.setString(2,text);
        ResultSet resultSet = preparedStatement.executeQuery();
        // ihina if use panna eala because ihina vaara set ondu (neraya varum)
        List<Student> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(
                    new Student(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4)
                    )
            );
        }
        return list;
    }

    private boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE student_id=?");
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    private boolean updateStudent(Student student) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");*/

        // applied singleton design pattern
        Connection connection = DbConnection.getInstance().getConnection();
        //System.out.println(connection); // checking connetion is different location or something

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET full_name=?,dob=?, address=? WHERE student_id=?");
        // query order ikku thaan keela eeraza set seyyanum
        preparedStatement.setString(4, student.getStudentId());
        preparedStatement.setString(1, student.getFullName());
        preparedStatement.setObject(2, student.getDateOfBirth());
        // object ikku veandiya value poda ealum indrazaala setObject part ikku indha date i poattukkollalaam student.getDateOfBirth()
        preparedStatement.setString(3, student.getAddress());
        return preparedStatement.executeUpdate()<0;
    }
}
