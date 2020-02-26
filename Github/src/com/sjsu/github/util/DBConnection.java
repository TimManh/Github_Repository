//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static String url = "";    
    private static String driverName = "com.mysql.cj.jdbc.Driver";   
    private static String username = "";   
    private static String password = "";
    private static Connection myConn;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
            	myConn = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found."); 
        }
        return myConn;
    }

}
