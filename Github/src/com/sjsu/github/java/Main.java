//SJSU CMPE 138 Spring 2019 Team-2
package com.sjsu.github.java;
import java.sql.*;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sjsu.github.util.DBConnection;
 
public class Main {
	
	static Logger logger = Logger.getRootLogger();

	public Main()				//default constructor
	{
		PropertyConfigurator.configure("resources/log4j.properties");	//configure log4j
	}
	static Connection myConn;
	static Statement mystmt;
	 
	public static void main(String[] args) {
		DBConnection dbconnectobj = new DBConnection();
		Main mainobj = new Main();
		Signup signupobj = new Signup();
		Login loginobj = new Login();
		AdminLogin adminloginobj = new AdminLogin();
		System.out.println("----------------------------------------------"); 
		System.out.println("           Welcome to Github Project!         ");
		System.out.println("----------------------------------------------"); 
		try{
			
			myConn = dbconnectobj.getConnection();
			myConn.setAutoCommit(false); 
			
			logger.info("Entered into Main Page");
	
			mystmt = myConn.createStatement();
			
		} catch(Exception exc){
			exc.printStackTrace();
		}
		
		System.out.println("1.Signup\n2.Login\n3.Admin Login");
		System.out.println("Enter your choice:");
		Scanner myObj = new Scanner(System.in);
		int choice = myObj.nextInt();
		 
		switch(choice){
			case 1:	try {
				signupobj.signuppage();
			} catch (SQLException e) {
				e.printStackTrace();
			}
					break; 	
			
			case 2: loginobj.loginpage();
					break;
					
			case 3: adminloginobj.adminloginpage();
			break;
			default: {	System.out.println("Please enter valid choice.\n");
						mainobj.main(null);
			   		 }
		}
		logger.info("Ended Main Page");
	}
}
