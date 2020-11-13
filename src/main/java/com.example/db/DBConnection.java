package com.example.db;

import java.sql.*;

public class DBConnection {

    // NOTE: It'd be better to get them from a properties or XML configuration file
             // make sure to change it to your DB credentials

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Note : class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'.
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME = "message_board?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    static final String DB_NAME = "concordia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    // Database credentials
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "12345";


    static Connection conn = null;

    public static Connection getConnection() {

        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL+DB_NAME,DB_USER,DB_PASSWORD);
            return conn;
        } catch (SQLException e){
            throw new RuntimeException("Error connecting to database",e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Error Class Not Found",e);
        }
    }

    public static void closeConnection() throws SQLException {
        if (conn!=null)
            conn.close();
    }
}