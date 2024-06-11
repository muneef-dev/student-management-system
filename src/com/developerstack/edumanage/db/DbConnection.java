package com.developerstack.edumanage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // singleton concept
    // singleton => Creational design pattern -> must needed to industry

    // rule 1 must be private and static
    private static DbConnection dbConnection; // private and static DbConnection type variable ondu seyyanum (rule 1)
    // izu default null aahum

    private Connection connection; // sql connection // connection ikku thaan singleton apply pandra

    // rule 2
    // indha class in, instance i veara edaththula eendhu seyya kudukeala so constructor a private syyanum
    private DbConnection() throws ClassNotFoundException, SQLException {
        // connection a make paani assign senji kollum 55555
        // so izula thaan database connection a seyyanum

        // and exception a throw sei
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234"); // iza copy paste
        // senjonna izu local varialbe onda senjikolum appa azu keela getConnection() la null a return seyyum
        // meala eera indha private Connection connection; thaan naam ezirpaakkum connection so copy past panninonna azu
        // local variable ikku assing senji kollum so aza illamaakki ippidi podanum
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms3", "root", "1234");
    }

    // rule 3
    // dbconncetion in constructor i private seythaal eppidi indha connectio i access seira? so
    // constructor private seythaal option ondu kudukkanum vearaa edaththula eendhu access seyya
    // but instance aavatagiyata seyya kudukkuralla so oru instance i mattum seyya option kudukkanum
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {// public kudukkura veara edaththula eendhu access seyya and kattayama static kudukkanum because
    // kudukkallatti connection in instance uruvaakku mudiyaathathaal indha method i access seyya mudiyaamal pohum
    // and indha method return seyyanum DbConnection (indha class inaze reference i aahum) iyye aahum
    // (DbConnection type reference aahum) and kattayama getInstance thaan azu da method name aa  eekkanum izu rule aahum

        // indha method a call seyyum 1st time le if true aahum becasuse null aala so DbConnection ikku new instance ondu senjikollum
        // so constructor la connection onda senji connection variable ikku assign senji kollum 55555
        if (dbConnection==null){ // DbConnection default nulle ea
            dbConnection = new DbConnection();
            // so indha getInstance() class la eendhu oru instance thaan pohum eathna mura call senjaalum instance irundha
            // oru instance thaan pohum instance nearaya instance i azu seyyazu
        }
        return dbConnection; // if ulla pora dbconnection null inda mattum thaan illatti azu uruvaakki irundha connection i anda time vealiya anupum
        // so conncetion uruvaavazzu (hedenne) orukka vaahum
    }

    // andha Connection i edukka public method ondu theawa
    public Connection getConnection(){
        return connection;
    }
}
