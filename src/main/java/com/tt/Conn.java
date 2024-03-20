package com.tt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private Connection conn;
    final String DB_URL = "jdbc:mysql://localhost:3306/GKJava?useSSL=false&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true";
    final String USERNAME = "root";
    final String PASSWORD = "";


    public Conn(){
    }

    public Connection getConn(){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return conn;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
