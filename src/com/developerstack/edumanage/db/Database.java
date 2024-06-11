package com.developerstack.edumanage.db;

import com.developerstack.edumanage.model.*;
import com.developerstack.edumanage.util.security.PasswordManager;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    // static poda reson izu full project ukkum arraylist ondaahum so access seyya leasi class name a veachchi
    public static ArrayList<User> userTable = new ArrayList();
    public static ArrayList<Student> studentTable = new ArrayList();
    public static ArrayList<Teacher> teacherTable = new ArrayList();
    public static ArrayList<Program> programTable = new ArrayList();
    public static ArrayList<Intake> intakeTable = new ArrayList();

    static { // indha block app open panninna run aahum
        userTable.add(
                new User("Role","Admin","",new PasswordManager().encrypt(""))
        );
        /*studentTable.add(
                new Student("S-1","nimal", ,"colombo"),
                new Student("S-2","kamal",,"kandy"),
                new Student("S-3","vimal",,"galle"),
                new Student("S-4","bimal",,"jaffna")
        );*/
    }

}
