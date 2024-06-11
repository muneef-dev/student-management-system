package com.developerstack.edumanage.db;

import java.sql.Connection;

class DbConnectionTest {
    // iza class a podra test panna so ella project eam run seyyaama project in oru part a maattum run seyyalaam
    // test case i ivvaru eluzi vendiya function ikku iza seyyalaam
    public static void main(String[] args) {
        new DbConnectionTest().getInstance();
    }
    void getInstance() {
        // connection nearaya keattu ellathudem reference onda print aahurandu test seithal
        try {
            Connection con1 = DbConnection.getInstance().getConnection();
            Connection con2 = DbConnection.getInstance().getConnection();
            Connection con3 = DbConnection.getInstance().getConnection();
            Connection con4 = DbConnection.getInstance().getConnection();
            Connection con5 = DbConnection.getInstance().getConnection();
            System.out.println(con1);
            System.out.println(con2);
            System.out.println(con3);
            System.out.println(con4);
            System.out.println(con5);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}